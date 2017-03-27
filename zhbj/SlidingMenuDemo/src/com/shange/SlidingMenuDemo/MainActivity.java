package com.shange.SlidingMenuDemo;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * @author 山哥
 *使用slidingmenu1.1.引入slidingmenu库2.继承SlidingFragmentActivity
 *3.onCreate改成public
 *4.调用相关api
 *
 */
public class MainActivity extends SlidingFragmentActivity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //这个必须设置,跟setContentView一样,不设置会出异常
        setBehindContentView(R.layout.left_menu);
        
        SlidingMenu slidingMenu = getSlidingMenu();
        //设置右侧侧边栏
        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置模式,左右都有侧边栏
        
        //设置全屏触摸
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        
        //设置侧边栏宽度
        slidingMenu.setBehindOffset(500);//设置屏幕预留宽度,这里需要适配
        
    }
}
