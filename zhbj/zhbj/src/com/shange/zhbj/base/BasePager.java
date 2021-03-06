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
 * 五个标签页的基类
 * @author 山哥
 *这个基类就是方便些五个标签页,就跟viewHolder有点像,省得find
 */
public class BasePager {
	public Activity mActivity;
	public TextView tv_title;//每一页的标题
	public ImageButton btn_menu;//标题左边开启菜单的按钮
	public FrameLayout fl_content;//这个framelayout需要动态填充的内容
	//因为不知道写什么,所以framelayout占个位置,想到后直接动态填充
	public ImageButton btn_photo;
	
	public View mRootView;//当前页面的布局对象,这什么玩儿意,看不懂啊
	
	public BasePager(Activity activity){
		mActivity = activity;
		mRootView = initView();
	}
	
	
	//初始化布局
	public View initView(){
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		btn_menu = (ImageButton) view.findViewById(R.id.btn_menu);
		fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
		btn_photo = (ImageButton) view.findViewById(R.id.btn_photo);
		//设置按钮的点击事件
		btn_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggle();
				
			}
		});
		return view;		
	}
	//初始化布局
	public void initData(){
		
	}
	/**
	 * 打开或者关闭侧边栏
	 */
	protected void toggle() {
		MainActivity mainUI = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();//如果当前状态是开,调用后就关,反之亦然
		
	}


}
