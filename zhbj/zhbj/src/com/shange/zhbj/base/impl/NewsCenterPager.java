package com.shange.zhbj.base.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shange.zhbj.MainActivity;
import com.shange.zhbj.base.BaseMenuDetailPager;
import com.shange.zhbj.base.BasePager;
import com.shange.zhbj.base.impl.menu.InteractMenuDetailPager;
import com.shange.zhbj.base.impl.menu.NewsMenuDetailPager;
import com.shange.zhbj.base.impl.menu.PhotosMenuDetailPager;
import com.shange.zhbj.base.impl.menu.TopicDetailPager;
import com.shange.zhbj.domain.NewsMenu;
import com.shange.zhbj.fragment.LeftMenuFragment;
import com.shange.zhbj.global.GlobalConstant;
import com.shange.zhbj.utils.CacheUtils;

/**
 * 首页
 * @author 山哥
 *
 */
public class NewsCenterPager extends BasePager {

	private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;//菜单详情页


	private NewsMenu mNewsData;//分类信息网络数据
	public NewsCenterPager(Activity activity) {
		super(activity);//为什么要实现构造方法,因为构造子类的时候,先要构造父类
		//如果参数为空,默认存在,super(),即可以省略不写,有参数的无法省略不写
		//总结一句话,无参数的可以省略不写,有参数的不能,why,有参数的为啥不能省略不写,默认有参数不行吗?
	}
	public void initData(){
		System.out.println("新闻中心初始化啦----");
		//要给帧布局填充布局对象
		/*	TextView view = new TextView(mActivity);
		view.setText("新闻中心");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);//子控件相当于当前控件的中心,子控件就是首页这两个字

		fl_content.addView(view);
		tv_title.setText("新闻中心");*/
		//先判断有没有缓存,如果有的话,就加载缓存
		String cache = CacheUtils.getCache(GlobalConstant.CATEGORY_URL, mActivity);
		if(!TextUtils.isEmpty(cache)){//等于空是true

			processData(cache);
			System.out.println("缓存:"+cache);
		}

		//请求服务器,获取数据
		//开元框架:XUtils
		getDataFromServer();

	}
	/**
	 * 从服务器获取数据
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		//请求方式为get,请求地址"",因为json是字符串,所以请求的是字符串,泛型为string
		utils.send(HttpMethod.GET,GlobalConstant.CATEGORY_URL,new RequestCallBack<String>() {


			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				//请求成功
				String json = responseInfo.result;//获取相应结果json字符串
				System.out.println("服务器返回结果:"+json);
				processData(json);
				CacheUtils.putCache(GlobalConstant.CATEGORY_URL, json, mActivity);//放在这里好,
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				//请求失败
				error.printStackTrace();
				Toast.makeText(mActivity, msg, 1).show();

			}
		} );

	}
	/**
	 * 解析数据
	 * @param result
	 */
	protected void processData(String json) {

		Gson gson = new Gson();
		mNewsData = gson.fromJson(json,NewsMenu.class);
		System.out.println(mNewsData);

		//获取侧边栏对象,给侧边栏对象添加数据
		MainActivity mainUI = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUI.getLeftMenuFragment();
		//拿到数据,给做菜单碎片赋值,
		leftMenuFragment.setMenuData(mNewsData.data);//赋值的时候初始化一下
		//初始化4个菜单详情页
		mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
		mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));//那要newscenter干嘛?
		mMenuDetailPagers.add(new TopicDetailPager(mActivity));//那要newscenter干嘛?
		mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity,btn_photo));//那要newscenter干嘛?
		mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));//那要newscenter干嘛?
		//将新闻菜单详情页设置为默认页面
		setCurrentDetailPager(0);

	}
	public void setCurrentDetailPager(int position){

		//重新给frameLayout添加内容
		BaseMenuDetailPager pager = mMenuDetailPagers.get(position);//获取当前应该显示的页面
		View view = pager.mRootView;//当前页面的布局
		//清除之前的旧布局
		fl_content.removeAllViews();
		fl_content.addView(view);//给帧布局添加布局


		//初始化页面数据
		pager.initData();//在这里更新的数据,玛德一段好找

		//更新标题
		tv_title.setText(mNewsData.data.get(position).title);
		//如果是组图页面,需要显示切换按钮
		if(pager instanceof PhotosMenuDetailPager){
			btn_photo.setVisibility(View.VISIBLE);
		}else{
			//隐藏切换按钮
			btn_photo.setVisibility(View.GONE);
		}


	}


}
