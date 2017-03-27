package com.shange.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollView extends ViewPager {

	public NoScrollView(Context context) {
		super(context);
		
	}

	public NoScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	//事件拦截
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//return super.onInterceptTouchEvent(ev);
		//interceptTouch返回true,传递给自己的ontouchevent事件,返回false,不拦截,传递给子控件,的dispatchtouchevent
		
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//重写此方法,触摸的什么都不做,从而实现对滑动事件的禁用
		return true;//true,消费了此事件
	}
	
	

}
