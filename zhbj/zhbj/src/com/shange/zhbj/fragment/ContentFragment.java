package com.shange.zhbj.fragment;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shange.zhbj.MainActivity;
import com.shange.zhbj.R;
import com.shange.zhbj.base.BasePager;
import com.shange.zhbj.base.impl.GovAffairsPager;
import com.shange.zhbj.base.impl.HomePager;
import com.shange.zhbj.base.impl.NewsCenterPager;
import com.shange.zhbj.base.impl.SettingPager;
import com.shange.zhbj.base.impl.SmartServicePager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {
	private ViewPager mViewPager;
	private ArrayList<BasePager> mPagers;
	private RadioGroup rg_group;

	//也就是说,这个类只需要实现这两个方法,就行了,想想也是,初始化数据几乎就是全部逻辑
	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_content);
		//单选按钮组
		rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
		//底栏标签切换监听
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			//当选中改变时调用
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.rb_home:
					//首页,false就不能滑动了
					mViewPager.setCurrentItem(0,false);//设置viewpager对应的当前页面
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1,false);
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2,false);
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3,false);
					break;
				case R.id.rb_setting:
					mViewPager.setCurrentItem(4,false);
					break;

				default:
					break;
				}

			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				//当页面被选中时调用,点到哪个,初始化哪儿个
				mPagers.get(position).initData();//选中页面初始化,记住是在这里初始化数据的
				if(position == 0 || position == mPagers.size() - 1){
					//首页和设置要禁用侧边栏
					setSlidingMenuEnable(false);
				}else{
					//其他页面开启侧边栏
					setSlidingMenuEnable(true);
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				//当页面滑动时调用,现在应该调用不了把

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				//当页面状态改变时调用,废了

			}
		});
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();
		//添加五个标签页
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new NewsCenterPager(mActivity));
		mPagers.add(new SmartServicePager(mActivity));
		mPagers.add(new GovAffairsPager(mActivity));
		mPagers.add(new SettingPager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());//设置适配器


		mPagers.get(0).initData();//初始化第一页
		//首页禁用侧边栏
		setSlidingMenuEnable(false);

	}
	//其实这个方法是多于的,不够优化,首先设成TOUCHMODE_NONE,然后2到4设成TOUCHMODE_FULLSCREEN,不就行了吗,不过封装成方法也可以
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

	class ContentAdapter extends PagerAdapter{

		@Override
		public int getCount() {

			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagers.get(position);
			View view = pager.mRootView;	//获取当前页面对象的布局		
			//pager.initData();//在这里初始化下数据
			//viewpater会默认加载下一个页面,为了节省流量和性能,不要再此处调用初始化数据的方法
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
		}
	}
	//获取新闻中心页面
	public NewsCenterPager getNewsCenterPager(){
		//现在只需要修改新闻中心页就行了,其它的不需要
		NewsCenterPager pager = (NewsCenterPager) mPagers.get(1);

		return pager;
	}


}
