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
 * @author ɽ��
 *�˵�����ҳ-����
 *ViewPagerIndicatorʹ������:
 *1.�����
 *2.���support-v4��ͻ(�������汾һ��)
 *3.�����ӳ����п��������ļ�
 *4.�����ӳ����п�����ش���(ָʾ����viewpager��:��дgetPageTitle�������ر���
 *5.���嵥�ļ���������ʽ
 *6.�����޸�Ϊ��ɫ
 *7.�޸���ʽ-������ʽ&������ʽ
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager mViewPager;
	@ViewInject(R.id.indicator)//viewpagerָʾ��
	private TabPageIndicator mIndicator;//ҳǩҳ�漯��
	private ArrayList<NewsTabData> mTabData;//ҳǩ��������
	private ArrayList<TabDetailPager> mPagers;//ҳǩҳ�漯��

	public NewsMenuDetailPager(Activity activity,ArrayList<NewsTabData> TabData) {
		super(activity);
		mTabData = TabData;

	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_news_menu_detail, null);
		ViewUtils.inject(this, view);//ע��,
		return view;
	}
	@Override
	public void initData() {
		mPagers = new ArrayList<TabDetailPager>();
		//��ʼ��ҳǩ
		for(int i = 0; i< mTabData.size();i++){
			TabDetailPager pager = new TabDetailPager(mActivity,mTabData.get(i));
			mPagers.add(pager);
		}
		mViewPager.setAdapter(new NewsMenuDetailAdapter());
		mIndicator.setViewPager(mViewPager);//��viewpager��ָʾ������һ��
		//ע��:������viewpager�����������֮��
		//����ҳ�滬������
		//����ָʾ����,ֻ������ָʾ���ļ����¼�
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				//��λ�ڵ�2ҳ֮���ܻ���
				if(position == 0){
					//���������
					setSlidingMenuEnable(true);//Ĭ��Ϊtrue,��һҳ����������

				}else{
					//���ò����
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
		//��ȡ���������
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}


	}

	class NewsMenuDetailAdapter extends PagerAdapter{

		//ָ��ָʾ���ı���
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
			//�ж���
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
		//�����¸�ҳ��
		int currentItem = mViewPager.getCurrentItem();//viewpager�����
		currentItem++;
		mViewPager.setCurrentItem(currentItem,false);//���õ�ǰҳ��,viewpager��װ�õ�
		
	}

}