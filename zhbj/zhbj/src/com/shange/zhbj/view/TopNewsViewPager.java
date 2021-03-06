package com.shange.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TopNewsViewPager extends ViewPager {

	
	private float startY;
	private float startX;


	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	public TopNewsViewPager(Context context) {
		super(context);
	}
	
	
	/* 1.上下滑动不拦截
	 * 2.向右滑动并且当前是第一个页面,需要拦截
	 * 3.向左滑动并且当前是最后一个页面,需要拦截
	 * 
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		switch (ev.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			//按下的时候获取初始位置
			startX = ev.getX();
			startY = ev.getY();
			
			
			break;
		case MotionEvent.ACTION_MOVE:
			float endX = ev.getX();
			float endY = ev.getY();
			float offsetX = endX - startX;//获取x轴偏移量,
			float offsetY = endY - startY;//获取y轴偏移量
			if(Math.abs(offsetY)<Math.abs(offsetX)){//左右滑动
				//如果是第一页或最后一页,可以拦截
				int currentItem = getCurrentItem();//等于0时,只能往右划,所以要分左右
				if(offsetX>0){//往右划,
					if(currentItem==0){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
					
				}else{
					int count = getAdapter().getCount();//getChildCount()
					if(currentItem==count-1){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
				
				
				
			}else{//上下滑动
				//可以拦截
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			
			
			break;

		default:
			break;
		}
		
		
		return super.dispatchTouchEvent(ev);//默认是false,传给子控件,但是当前控件没有子控件,只会传给当前
		//控件的onTouchEvent事件
	}

}
