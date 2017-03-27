package com.shange.zhbj.base;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shange.zhbj.MainActivity;
import com.shange.zhbj.R;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * �����ǩҳ�Ļ���
 * @author ɽ��
 *���������Ƿ���Щ�����ǩҳ,�͸�viewHolder�е���,ʡ��find
 */
public class BasePager {
	public Activity mActivity;
	public TextView tv_title;//ÿһҳ�ı���
	public ImageButton btn_menu;//������߿����˵��İ�ť
	public FrameLayout fl_content;//���framelayout��Ҫ��̬��������
	//��Ϊ��֪��дʲô,����framelayoutռ��λ��,�뵽��ֱ�Ӷ�̬���
	public ImageButton btn_photo;
	
	public View mRootView;//��ǰҳ��Ĳ��ֶ���,��ʲô�����,��������
	
	public BasePager(Activity activity){
		mActivity = activity;
		mRootView = initView();
	}
	
	
	//��ʼ������
	public View initView(){
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		btn_menu = (ImageButton) view.findViewById(R.id.btn_menu);
		fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
		btn_photo = (ImageButton) view.findViewById(R.id.btn_photo);
		//���ð�ť�ĵ���¼�
		btn_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggle();
				
			}
		});
		return view;		
	}
	//��ʼ������
	public void initData(){
		
	}
	/**
	 * �򿪻��߹رղ����
	 */
	protected void toggle() {
		MainActivity mainUI = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();//�����ǰ״̬�ǿ�,���ú�͹�,��֮��Ȼ
		
	}


}