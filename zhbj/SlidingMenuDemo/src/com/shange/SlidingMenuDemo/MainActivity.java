package com.shange.SlidingMenuDemo;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * @author ɽ��
 *ʹ��slidingmenu1.1.����slidingmenu��2.�̳�SlidingFragmentActivity
 *3.onCreate�ĳ�public
 *4.�������api
 *
 */
public class MainActivity extends SlidingFragmentActivity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //�����������,��setContentViewһ��,�����û���쳣
        setBehindContentView(R.layout.left_menu);
        
        SlidingMenu slidingMenu = getSlidingMenu();
        //�����Ҳ�����
        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//����ģʽ,���Ҷ��в����
        
        //����ȫ������
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        
        //���ò�������
        slidingMenu.setBehindOffset(500);//������ĻԤ�����,������Ҫ����
        
    }
}
