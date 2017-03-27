package com.shange.zhbj.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.shange.zhbj.base.BaseMenuDetailPager;

/**
 * @author ɽ��
 *�˵�����ҳ-ר��
 *
 *
 */
public class TopicDetailPager extends BaseMenuDetailPager {

	public TopicDetailPager(Activity activity) {
		super(activity);
		
	}

	@Override
	public View initView() {
		TextView view = new TextView(mActivity);
		view.setText("�˵�����ҳ-ר��");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		return view;
	}

}
