package com.shange.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.shange.zhbj.base.BasePager;

/**
 * ��ҳ
 * @author ɽ��
 *
 */
public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity activity) {
		super(activity);//ΪʲôҪʵ�ֹ��췽��,��Ϊ���������ʱ��,��Ҫ���츸��
		//�������Ϊ��,Ĭ�ϴ���,super(),������ʡ�Բ�д,�в������޷�ʡ�Բ�д
		//�ܽ�һ�仰,�޲����Ŀ���ʡ�Բ�д,�в����Ĳ���,why,�в�����Ϊɶ����ʡ�Բ�д,Ĭ���в���������?
	}
	public void initData(){
		System.out.println("�����ʼ����----");
		//Ҫ��֡������䲼�ֶ���
		TextView view = new TextView(mActivity);
		view.setText("����");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);//�ӿؼ��൱�ڵ�ǰ�ؼ�������,�ӿؼ�������ҳ��������
		
		fl_content.addView(view);
		tv_title.setText("����");
		
		
	}

}
