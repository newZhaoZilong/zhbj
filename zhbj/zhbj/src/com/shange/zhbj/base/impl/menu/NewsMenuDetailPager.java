package com.shange.zhbj.base.impl.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.shange.zhbj.MainActivity;
import com.shange.zhbj.R;
import com.shange.zhbj.base.BaseMenuDetailPager;
import com.shange.zhbj.domain.NewsMenu.NewsTabData;
import com.viewpagerindicator.TabPageIndicator;

/**
 * @author 山哥
 *菜单详情页-新闻
 *ViewPagerIndicator使用流程:
 *1.引入库
 *2.解决support-v4冲突(让两个版本一致)
 *3.从例子程序中拷贝布局文件
 *4.从例子程序中拷贝相关代码(指示器和viewpager绑定:重写getPageTitle方法返回标题
 *5.在清单文件中增减样式
 *6.背景修改为白色
 *7.修改样式-背景样式&文字样式
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager mViewPager;
	@ViewInject(R.id.indicator)//viewpager指示器
	private TabPageIndicator mIndicator;//页签页面集合
	private ArrayList<NewsTabData> mTabData;//页签网络数据
	private ArrayList<TabDetailPager> mPagers;//页签页面集合

	public NewsMenuDetailPager(Activity activity,ArrayList<NewsTabData> TabData) {
		super(activity);
		mTabData = TabData;

	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_news_menu_detail, null);
		ViewUtils.inject(this, view);//注入,
		return view;
	}
	@Override
	public void initData() {
		mPagers = new ArrayList<TabDetailPager>();
		//初始化页签
		for(int i = 0; i< mTabData.size();i++){
			TabDetailPager pager = new TabDetailPager(mActivity,mTabData.get(i));
			mPagers.add(pager);
		}
		mViewPager.setAdapter(new NewsMenuDetailAdapter());
		mIndicator.setViewPager(mViewPager);//将viewpager和指示器绑定在一起
		//注意:必须在viewpager设置完成数据之后
		//设置页面滑动监听
		//绑定了指示器后,只能设置指示器的监听事件
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				//当位于第2页之后不能滑动
				if(position == 0){
					//开启侧边栏
					setSlidingMenuEnable(true);//默认为true,第一页不调用正好

				}else{
					//禁用侧边栏
					setSlidingMenuEnable(false);
				}
				System.out.println(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				
			}

			@Override
			public void onPageScrollStateChanged(int state) {


			}
		});
	}

	protected void setSlidingMenuEnable(boolean enable) {
		//获取侧边栏对象
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}


	}

	class NewsMenuDetailAdapter extends PagerAdapter{

		//指定指示器的标题
		@Override
		public CharSequence getPageTitle(int position) {
			NewsTabData data = mTabData.get(position);

			return data.title;
		}
		@Override
		public int getCount() {

			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			//判读那
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagers.get(position);
			View view = pager.mRootView;
			container.addView(pager.mRootView);
			pager.initData();

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
		}
	}
	@OnClick(R.id.btn_next)
	public void nextPage(View view){
		//跳到下个页面
		int currentItem = mViewPager.getCurrentItem();//viewpager有这个
		currentItem++;
		mViewPager.setCurrentItem(currentItem,false);//设置当前页面,viewpager封装好的
		
	}

}
