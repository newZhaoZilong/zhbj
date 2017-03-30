package com.shange.zhbj.utils;

import com.shange.zhbj.R;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitmapUtils {
	
	private MemoryCacheUtils mMemoryCacheUtils;
	private LocalCaheUtils mLocalCaheUtils;
	private NetCacheUtils mNetCacheUtils;

	public MyBitmapUtils(){
		
		mMemoryCacheUtils = new MemoryCacheUtils();//内存缓存
		mLocalCaheUtils = new LocalCaheUtils();	//本地缓存
		mNetCacheUtils = new NetCacheUtils(mLocalCaheUtils,mMemoryCacheUtils);//网络缓存
	}
	public void display(ImageView imageView,String url){
		//优先从内存中加载图片,速度最快,不浪费流量
		//其次从本地(sdcard)加载图片,速度快,不浪费流量
		//最后从网络加载图片,速度慢,浪费流量
		//设置默认图片
		imageView.setImageResource(R.drawable.pic_item_list_default);
		
		Bitmap bitmap = mMemoryCacheUtils.getMemoryCache(url);
		
		if(mMemoryCacheUtils.getMemoryCache(url)!=null){
			imageView.setImageBitmap(bitmap);
			System.out.println("从内存加载图片啦");
			return;
			
		}
		//其次从本地(sdcard)加载图片,速度快,不浪费流量
		bitmap = mLocalCaheUtils.getLocalCache(url);
		if(bitmap != null){
			imageView.setImageBitmap(bitmap);
			System.out.println("从本地加载图片");
			//写内存缓存
			mMemoryCacheUtils.setMemoryCache(url, bitmap);
			return;
			
		}
		//最后从网络下载图片,速度慢,浪费流量
		mNetCacheUtils.getBitmapFromNet(imageView, url);
		
	}

}
