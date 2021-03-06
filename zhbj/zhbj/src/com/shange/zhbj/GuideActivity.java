package com.shange.zhbj;

import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import com.shange.zhbj.utils.DensityUtils;
import com.shange.zhbj.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {

	private ViewPager mViewPager;

	private int[] mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

	private ArrayList<ImageView> mImageList;

	private LinearLayout ll_container;

	private int mPointDis;

	private ImageView iv_red_point;

	private Button btn_start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题,必须在setContentView之前调用
		setContentView(R.layout.activity_guide);
		btn_start = (Button) findViewById(R.id.btn_start);
		iv_red_point = (ImageView) findViewById(R.id.iv_red_point);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		mViewPager = (ViewPager) findViewById(R.id.vp_guide);//viewpager自带滑动功能
		initData();
		mViewPager.setAdapter(new GuideAdapter());


		//监听layout方法结束的事件,位置确定好之后,再获取圆点间距
		//视图树
		iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				//layout方法执行结束的回调
				mPointDis = ll_container.getChildAt(1).getLeft()
						-ll_container.getChildAt(0).getLeft();//间距
				iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);//直接用this,方便
			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			@Override
			public void onPageSelected(int position) {
				//某个页面被选中
				if(position == mImageIds.length - 1){//老子直接从最原始的资料取数据
					btn_start.setVisibility(View.VISIBLE);//设置为可见的
					
				}else{
					btn_start.setVisibility(View.INVISIBLE);//设置为不可见的
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				params.leftMargin = (int) (mPointDis*positionOffset)+mPointDis*position;//高中生看到这个,还以为是敲代码的都是弱智						

				// 当页面滑动过程中的回调,只要滑动就会执行
				iv_red_point.setLayoutParams(params);



			}

			@Override
			public void onPageScrollStateChanged(int state) {
				//页面状态发生变化的回调,
				

			}
		});
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//更新sp,存储状态
				SpUtils.putBoolean(getApplicationContext(),"is_first_enter",false);
				
				//跳到主页面
				startActivity(new Intent(getApplicationContext(),MainActivity.class));
				
				finish();
			}
		});
	}

	//初始化数据
	private void initData(){
		mImageList = new ArrayList<ImageView>();
		ImageView imageview;
		ImageView pointImage;//这样能成,说明linearlayout可以添加重复的

		for(int i = 0;i<3;i++){
			imageview = new ImageView(this);
			//设置背景图片
			imageview.setBackgroundResource(mImageIds[i]);
			mImageList.add(imageview);
			//初始化小圆点
			pointImage = new ImageView(this);
			pointImage.setImageResource(R.drawable.shape_point_gray);//设置图片
			//初始化布局参数,宽高包括内容,父控件是谁,就是谁声明的布局参数,这个父控件,指的是ImageView的父控件
			LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			if(i>0){//保证最左边的点是从0开始
				//设置左边距
				layoutParams.leftMargin = DensityUtils.dip2px(10, this);
			}
			//布局参数是随动的,所以每个view都需要一个,如果尺寸相同可以共用
			//就记住一个view对应一个布局参数
			pointImage.setLayoutParams(layoutParams);//设置布局参数,我想问,这是给图片设置的
			//跟相对布局,线性布局有什么关系,有关系,如果父控件对不上号,就会出错

			ll_container.addView(pointImage);//给容器添加圆点,
		}

	}
	class GuideAdapter extends PagerAdapter{//ViewPager的适配器

		//item的个数
		@Override
		public int getCount() {

			return mImageList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			//固定写法,判断arg1是否是view
			return arg0 == arg1;
		}
		//初始化item布局
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = mImageList.get(position);
			container.addView(imageView);//添加控件,不添加,一张都看不见
			return imageView;
		}
		//销毁item,一般适配器里的ViewGroup指的都是setAdapter的View
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
		}


	}

}
