package com.shange.zhbj;

import com.shange.zhbj.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;


public class SplashActivity extends Activity {

	
    private RelativeLayout rl_root;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        //旋转动画
        RotateAnimation animRotate = new RotateAnimation(0, 360,
        		Animation.RELATIVE_TO_SELF, 0.5f,
        		Animation.RELATIVE_TO_SELF, 0.5f);
        animRotate.setDuration(1000);//动画时间
        animRotate.setFillAfter(true);//保持动画结束状态
        //缩放动画
        ScaleAnimation animScale = new ScaleAnimation(0, 1,0, 1,
        		Animation.RELATIVE_TO_SELF, 0.5f,
        		Animation.RELATIVE_TO_SELF, 0.5f);
        animScale.setDuration(1000);
        animScale.setFillAfter(true);//保持动画结束状态
        //渐变动画
        AlphaAnimation animAlpha = new AlphaAnimation(0, 1);
        animScale.setDuration(1000);
        animScale.setFillAfter(true);//保持动画结束状态
        //动画集合
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(animRotate);
        set.addAnimation(animScale);
        set.addAnimation(animAlpha);
        //启动动画
        rl_root.startAnimation(set);
        
        set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				//动画结束,跳转页面
				//如果是第一次进入,跳转到新手引导
				//否则跳主页面
				boolean isFirstEnter = SpUtils.getBoolean
						(getApplicationContext(), "is_first_enter", true);
				Intent intent;
				if(isFirstEnter){
					//新手引导
					intent = new Intent(getApplicationContext(), GuideActivity.class);
				}else{
					//主页面
					intent = new Intent(getApplicationContext(), MainActivity.class);
				}
				startActivity(intent);//这种方式更优化
				finish();//关闭当前界面
			}
		});
    }
	

}
