package com.shange.zhbj.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.shange.zhbj.R;

import android.content.Context;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class PullToRefreshListView extends ListView implements OnScrollListener{

	private static final int STATE_PULL_TO_REFRESH = 1;//下拉刷新
	private static final int STATE_RELEASE_TO_REFRESH = 2;//释放刷新
	private static final int STATE_REFRESHING = 3;//刷新中
	private static final String tag = "PullToRefreshListView";
	private int mCurrentState = STATE_PULL_TO_REFRESH;//当前刷新状态
	private View mHeadView;
	private int mHeaderViewHeight;
	private int startY = -1;
	private TextView tvTitle;
	private TextView tvTime;
	private ImageView ivArrow;
	private RotateAnimation rotateUpAnim;
	private RotateAnimation rotateDownAnim;
	private ProgressBar pbProgress;
	public PullToRefreshListView(Context context) {
		super(context);
		initHeadView();
		initFooterView();
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView();
		initFooterView();
	}

	public PullToRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initHeadView();
		initFooterView();
	}
	/**
	 * 初始化头布局
	 * 
	 */
	private void initHeadView(){
		mHeadView = View.inflate(getContext(), R.layout.pull_to_refresh, null);
		//添加头布局
		addHeaderView(mHeadView);
		tvTitle = (TextView) mHeadView.findViewById(R.id.tv_title);
		tvTime = (TextView) mHeadView.findViewById(R.id.tv_time);
		ivArrow = (ImageView) mHeadView.findViewById(R.id.iv_arrow);

		pbProgress = (ProgressBar) mHeadView.findViewById(R.id.pb_loading);
		//pbProgress.set


		//隐藏头布局
		mHeadView.measure(0, 0);
		mHeaderViewHeight = mHeadView.getMeasuredHeight();

		mHeadView.setPadding(0, -mHeaderViewHeight, 0, 0);
		initAnimation();
		setCurrentTime();//更新时间,否则时间就是默认时间了
		
		
	}

	private void initFooterView(){
		mFooterView = View.inflate(getContext(), R.layout.pull_to_refresh_footer, null);
		addFooterView(mFooterView);
		mFooterView.measure(0, 0);
		mFooterViewHeight = mFooterView.getMeasuredHeight();

		mFooterView.setPadding(0, -mFooterViewHeight, 0,0);

		setOnScrollListener(this);//滑动监听
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY  = (int) ev.getY();

			break;
		case MotionEvent.ACTION_MOVE:
			if(startY  == -1){//当用户按住头条新闻的viewpager进行下拉时,actiondown,会被viewpager消费
				startY = (int)ev.getY();//导致startY没有赋值,此处需要重新获取一下

			}
			if(mCurrentState == STATE_REFRESHING){
				//如果是正在刷新,跳出循环
				break;//这时候正在更新数据
			}
			int endY = (int)ev.getY();
			int dy = endY - startY;//y轴偏移量,为正向下拉,为负时,向上拉
			int firstVisiblePosition = getFirstVisiblePosition();//当前显示的第一个item角标
			//必须向下拉,并且当前显示的是第一个item
			if(dy>0 && firstVisiblePosition ==0){
				int padding = dy - mHeaderViewHeight;//计算当前下拉控件的padding值
				mHeadView.setPadding(0, padding, 0, 0);
				//padding>0说明头布局完全拉出来了,
				if(padding >0 && mCurrentState!=STATE_RELEASE_TO_REFRESH){
					//改为松开刷新
					mCurrentState = STATE_RELEASE_TO_REFRESH;//记录当前状态
					refreshState();
				}else if (padding <0 && mCurrentState != STATE_PULL_TO_REFRESH){
					//也就是说她又拉回来了
					//改为下拉刷新
					mCurrentState = STATE_PULL_TO_REFRESH;
					refreshState();
				}
				return true;
			}

			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			if(mCurrentState == STATE_RELEASE_TO_REFRESH){
				mCurrentState = STATE_REFRESHING;
				refreshState();
				//完整展示头布局
				mHeadView.setPadding(0, 0, 0, 0);
				//完整展示头布局
				//4.进行回调
				if(mListener !=null){
					mListener.onRefresh();//刷新数据
					//	onRefreshComplete();//刷新结束,收起控件
				}
				//	onRefreshComplete();//刷新结束,收起控件

			}else if(mCurrentState == STATE_PULL_TO_REFRESH){
				//说明没有拉过界,只是玩玩
				//恢复原来的状态,隐藏头布局

				mHeadView.setPadding(0, -mHeaderViewHeight, 0, 0);

			}

			break;

		}


		return super.onTouchEvent(ev);
	}
	private void setCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date());//new Date()里面调用的就是获取System.currentTimeMillis();
		tvTime.setText("上次刷新时间:"+time);
	}

	/**
	 * 初始化头布局的动画
	 */
	private void initAnimation(){
		rotateUpAnim = new RotateAnimation(0, -180,//从0度,逆时针旋转180度,箭头,从下逆时针旋转180度变为朝上,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateUpAnim.setDuration(300);
		rotateUpAnim.setFillAfter(true);//动画停留在结束位置
		rotateDownAnim = new RotateAnimation(-180f, -360,//箭头,从上逆时针旋转180度变为朝下,跟up形成一个循环
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateDownAnim.setDuration(300);
		rotateDownAnim.setFillAfter(true);//动画停留在结束位置
	}



	/**
	 * 根据当前状态刷新界面
	 */
	private void refreshState() {

		switch (mCurrentState) {
		case STATE_PULL_TO_REFRESH:
			tvTitle.setText("下拉刷新");
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.setVisibility(View.VISIBLE);
			ivArrow.startAnimation(rotateUpAnim);
			break;
		case STATE_RELEASE_TO_REFRESH:
			tvTitle.setText("释放刷新");
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.setVisibility(View.VISIBLE);
			ivArrow.startAnimation(rotateDownAnim);
			break;
		case STATE_REFRESHING:
			tvTitle.setText("正在刷新...");
			ivArrow.clearAnimation();//清除箭头动画,否则无法隐藏
			pbProgress.setVisibility(View.VISIBLE);
			ivArrow.setVisibility(View.INVISIBLE);
			break;
		}

	}
	public void onRefreshComplete(boolean success){
		if(!isLoadMore){
			mHeadView.setPadding(0, -mHeaderViewHeight, 0, 0);
			mCurrentState = STATE_PULL_TO_REFRESH;
			tvTitle.setText("下拉刷新");
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.setVisibility(View.VISIBLE);
			if(success){//只有刷新成功之后才更新时间
				setCurrentTime();//更新时间
			}			
		}else{
			//加载更多
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
			isLoadMore = false;
		}
		
		
	}


	//3.定义成员变量,接收监听对象
	private OnRefreshListener mListener;
	private View mFooterView;
	private int mFooterViewHeight;


	public void setOnRefreshListener(OnRefreshListener refreshListener){
		mListener = refreshListener;
	}

	/**
	 * @author 山哥
	 *1.下拉刷新的回调接口
	 *
	 */
	public interface OnRefreshListener{

		public void onRefresh();
		
		public void onLoadMore();
	}

	private boolean isLoadMore;//标记是否在加载
	//滑动状态发生变化
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		Log.i(tag, "老子的方法1===>onScrollStateChanged");
		if(scrollState == SCROLL_STATE_IDLE){//空闲状态
			
			int lastVisiblePosition = getLastVisiblePosition();
			if(lastVisiblePosition == getCount() - 1&& !isLoadMore){//当前显示的是最后一个,并且没有在加载数据
				isLoadMore = true;
				
				//到底了
				System.out.println("加载更多...");
				mFooterView.setPadding(0, 0, 0, 0);//显示加载更多的布局
				
				setSelection(getCount() - 1);//将listview显示在最后一个item上,
				//从而加载更多会直接展示出来,无需手动滑动
				
				//通知主界面加载下一页数据
				if(mListener!=null){//终于明白了,自己new的没有给OnRefreshListener赋值,所以加载不了
					Log.i(tag, "老子的方法22222222222222222===>onScrollStateChanged");
					Log.i(tag, "老子的方法11111111111111===>onScrollStateChanged");
					mListener.onLoadMore();
				}
				
			}

		}

	}
	//滑动过程回调
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.i(tag, "老子的方法2===>onScroll");

	}
	
}
