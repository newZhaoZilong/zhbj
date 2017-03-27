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
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
		//������setContentView֮ǰ����
		setContentView(R.layout.activity_main);
		//
		setBehindContentView(R.layout.left_menu);
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//ȫ������
		slidingMenu.setBehindOffset(500);//��ĻԤ��500���ؿ��
		//��ʼ����Ƭ
		initFragment();
	}
	private void initFragment(){
		
		FragmentManager fm = getSupportFragmentManager();//����support v4�����,������ǿ
		//��������
		FragmentTransaction transaction = fm.beginTransaction();
		//��fragment�滻����,�õ���id,��������֡�����滻
		transaction.replace(R.id.fl_left_menu,new LeftMenuFragment(), TAG_LEFT_MENU);

		transaction.replace(R.id.fl_main,new ContentFragment(), TAG_CONTENT);
		//�ύ����
		transaction.commit();
		
	}
	public LeftMenuFragment getLeftMenuFragment(){
		FragmentManager fm = getSupportFragmentManager();//����support v4�����,������ǿ
		LeftMenuFragment fragment = (LeftMenuFragment) fm.findFragmentByTag(TAG_LEFT_MENU);
		
		return fragment;
		
	}
	public ContentFragment getContentFragment(){
		FragmentManager fm = getSupportFragmentManager();//����support v4�����,������ǿ
		ContentFragment fragment = (ContentFragment) fm.findFragmentByTag(TAG_CONTENT);
		
		return fragment;
		
	}
}
