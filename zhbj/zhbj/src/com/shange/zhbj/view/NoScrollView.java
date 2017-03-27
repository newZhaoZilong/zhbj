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
	//�¼�����
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//return super.onInterceptTouchEvent(ev);
		//interceptTouch����true,���ݸ��Լ���ontouchevent�¼�,����false,������,���ݸ��ӿؼ�,��dispatchtouchevent
		
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//��д�˷���,������ʲô������,�Ӷ�ʵ�ֶԻ����¼��Ľ���
		return true;//true,�����˴��¼�
	}
	
	

}
