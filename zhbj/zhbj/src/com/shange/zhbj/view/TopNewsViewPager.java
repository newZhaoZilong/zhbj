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
	
	
	/* 1.���»���������
	 * 2.���һ������ҵ�ǰ�ǵ�һ��ҳ��,��Ҫ����
	 * 3.���󻬶����ҵ�ǰ�����һ��ҳ��,��Ҫ����
	 * 
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		switch (ev.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			//���µ�ʱ���ȡ��ʼλ��
			startX = ev.getX();
			startY = ev.getY();
			
			
			break;
		case MotionEvent.ACTION_MOVE:
			float endX = ev.getX();
			float endY = ev.getY();
			float offsetX = endX - startX;//��ȡx��ƫ����,
			float offsetY = endY - startY;//��ȡy��ƫ����
			if(Math.abs(offsetY)<Math.abs(offsetX)){//���һ���
				//����ǵ�һҳ�����һҳ,��������
				int currentItem = getCurrentItem();//����0ʱ,ֻ�����һ�,����Ҫ������
				if(offsetX>0){//���һ�,
					if(currentItem==0){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
					
				}else{
					int count = getAdapter().getCount();//getChildCount()
					if(currentItem==count-1){
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
				
				
				
			}else{//���»���
				//��������
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			
			
			break;

		default:
			break;
		}
		
		
		return super.dispatchTouchEvent(ev);//Ĭ����false,�����ӿؼ�,���ǵ�ǰ�ؼ�û���ӿؼ�,ֻ�ᴫ����ǰ
		//�ؼ���onTouchEvent�¼�
	}

}