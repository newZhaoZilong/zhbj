package com.shange.zhbj.fragment;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shange.zhbj.MainActivity;
import com.shange.zhbj.R;
import com.shange.zhbj.base.impl.NewsCenterPager;
import com.shange.zhbj.domain.NewsMenu.NewsMenuData;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 侧边栏fragment
 * @author 山哥
 *
 */
public class LeftMenuFragment extends BaseFragment {

	//@ViewInject(R.id.lv_list)//纯属装逼
	private ListView lv_list;
	private ArrayList<NewsMenuData> mNewsMenuData;
	
	private int mCurrentPos;//当前被选中的item的位置
	private LeftMenuAdapter mAdapter;
	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//选中哪个,跟新mCurrentPos
				mCurrentPos = position;
				//更新适配器
				mAdapter.notifyDataSetChanged();
				//收起侧边栏
				toggle();
				//侧边栏点击之后,要修改新闻中心的FrameLayout中的内容
				//要修改新闻中心的framelayout中的内容,就要获取新闻中心对象
				setCurrentDetailPager(position);
				
			}
		});
		//ViewUtils.inject(this, view);
		return view;
	}

	/**
	 * 根据标题修改页面
	 * @param position
	 */
	protected void setCurrentDetailPager(int position) {
		//mianactivity
		MainActivity mainUI = (MainActivity) mActivity;
		ContentFragment fragment = mainUI.getContentFragment();
		//获取NewsCenterPager,怎么获取啊,
		NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();
		//修改新闻中心的FragmentLayout的布局
		newsCenterPager.setCurrentDetailPager(position);
		
	}

	/**
	 * 打开或者关闭侧边栏
	 */
	protected void toggle() {
		MainActivity mainUI = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();//如果当前状态是开,调用后就关,反之亦然
		
	}

	@Override
	public void initData() {//这个方法一开始就调用了,但是必须走到网络新闻才有数据,所以可以说废了

	}

	//给侧边栏设置数据
	public void setMenuData(ArrayList<NewsMenuData> data){

		mCurrentPos = 0;
		//更新页面
		mNewsMenuData = data;
		mAdapter = new LeftMenuAdapter();
		if(mNewsMenuData!=null){
			System.out.println("有数据");
			lv_list.setAdapter(mAdapter);
		}

	}
	class LeftMenuAdapter extends BaseAdapter{

		@Override
		public int getCount() {

			return mNewsMenuData.size();
		}

		@Override
		public NewsMenuData getItem(int position) {

			return mNewsMenuData.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
			//找到控件
			TextView tv_menu = (TextView) view.findViewById(R.id.tv_menu);
			//赋值
			tv_menu.setText(mNewsMenuData.get(position).title);

			if(mCurrentPos == position){
				tv_menu.setEnabled(true);//相等为红色,默认为0,也就是说第一个默认为红色
			}else{
				tv_menu.setEnabled(false);//不相等为白色
			}

			return view;
		}

	}


}
