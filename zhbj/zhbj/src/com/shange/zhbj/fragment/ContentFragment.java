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

	//Ҳ����˵,�����ֻ��Ҫʵ������������,������,����Ҳ��,��ʼ�����ݼ�������ȫ���߼�
	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_content);
		//��ѡ��ť��
		rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
		//������ǩ�л�����
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			//��ѡ�иı�ʱ����
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.rb_home:
					//��ҳ,false�Ͳ��ܻ�����
					mViewPager.setCurrentItem(0,false);//����viewpager��Ӧ�ĵ�ǰҳ��
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
				//��ҳ�汻ѡ��ʱ����,�㵽�ĸ�,��ʼ���Ķ���
				mPagers.get(position).initData();//ѡ��ҳ���ʼ��,��ס���������ʼ�����ݵ�
				if(position == 0 || position == mPagers.size() - 1){
					//��ҳ������Ҫ���ò����
					setSlidingMenuEnable(false);
				}else{
					//����ҳ�濪�������
					setSlidingMenuEnable(true);
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				//��ҳ�滬��ʱ����,����Ӧ�õ��ò��˰�

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				//��ҳ��״̬�ı�ʱ����,����

			}
		});
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();
		//��������ǩҳ
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new NewsCenterPager(mActivity));
		mPagers.add(new SmartServicePager(mActivity));
		mPagers.add(new GovAffairsPager(mActivity));
		mPagers.add(new SettingPager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());//����������


		mPagers.get(0).initData();//��ʼ����һҳ
		//��ҳ���ò����
		setSlidingMenuEnable(false);

	}
	//��ʵ��������Ƕ��ڵ�,�����Ż�,�������TOUCHMODE_NONE,Ȼ��2��4���TOUCHMODE_FULLSCREEN,����������,������װ�ɷ���Ҳ����
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
			View view = pager.mRootView;	//��ȡ��ǰҳ�����Ĳ���		
			//pager.initData();//�������ʼ��������
			//viewpater��Ĭ�ϼ�����һ��ҳ��,Ϊ�˽�ʡ����������,��Ҫ�ٴ˴����ó�ʼ�����ݵķ���
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
		}
	}
	//��ȡ��������ҳ��
	public NewsCenterPager getNewsCenterPager(){
		//����ֻ��Ҫ�޸���������ҳ������,�����Ĳ���Ҫ
		NewsCenterPager pager = (NewsCenterPager) mPagers.get(1);

		return pager;
	}


}
