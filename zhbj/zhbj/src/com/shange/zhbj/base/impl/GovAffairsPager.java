package com.shange.zhbj.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.shange.zhbj.base.BasePager;

/**
 * 首页
 * @author 山哥
 *
 */
public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity activity) {
		super(activity);//为什么要实现构造方法,因为构造子类的时候,先要构造父类
		//如果参数为空,默认存在,super(),即可以省略不写,有参数的无法省略不写
		//总结一句话,无参数的可以省略不写,有参数的不能,why,有参数的为啥不能省略不写,默认有参数不行吗?
	}
	public void initData(){
		System.out.println("政务初始化啦----");
		//要给帧布局填充布局对象
		TextView view = new TextView(mActivity);
		view.setText("政务");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);//子控件相当于当前控件的中心,子控件就是首页这两个字
		
		fl_content.addView(view);
		tv_title.setText("政务");
		
		
	}

}
