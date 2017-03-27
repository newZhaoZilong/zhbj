package com.shange.zhbj.utils;

import android.widget.ImageView;

public class MyBitmapUtils {

	private NetCacheUtils mNetCacheUtils;
	public MyBitmapUtils(){
		mNetCacheUtils = new NetCacheUtils();
	}
	public void display(ImageView imageView,String url){
		//优先从内存中加载图片,速度最快,不浪费流量
		//其次从本地(sdcard)加载图片,速度快,不浪费流量
		//最后从网络加载图片,速度慢,浪费流量
		mNetCacheUtils.getBitmapFromNet(imageView,url);
	}

}
