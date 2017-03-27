package com.shange.zhbj;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.shange.zhbj.fragment.ContentFragment;
import com.shange.zhbj.fragment.LeftMenuFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;


public class MainActivity extends SlidingFragmentActivity {
	private static final String TAG_CONTENT = "tag_content";
	private static final String TAG_LEFT_MENU = "tag_left_menu";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题
		//必须在setContentView之前调用
		setContentView(R.layout.activity_main);
		//
		setBehindContentView(R.layout.left_menu);
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏触摸
		slidingMenu.setBehindOffset(500);//屏幕预留500像素宽度
		//初始化碎片
		initFragment();
	}
	private void initFragment(){
		
		FragmentManager fm = getSupportFragmentManager();//这是support v4包里的,兼容性强
		//开启事务
		FragmentTransaction transaction = fm.beginTransaction();
		//用fragment替换布局,用的是id,更像是用帧布局替换
		transaction.replace(R.id.fl_left_menu,new LeftMenuFragment(), TAG_LEFT_MENU);

		transaction.replace(R.id.fl_main,new ContentFragment(), TAG_CONTENT);
		//提交事务
		transaction.commit();
		
	}
	public LeftMenuFragment getLeftMenuFragment(){
		FragmentManager fm = getSupportFragmentManager();//这是support v4包里的,兼容性强
		LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(TAG_LEFT_MENU);
		
		return fragment;
		
	}
	public ContentFragment getContentFragment(){
		FragmentManager fm = getSupportFragmentManager();//这是support v4包里的,兼容性强
		ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(TAG_CONTENT);
		
		return fragment;
		
	}
}
