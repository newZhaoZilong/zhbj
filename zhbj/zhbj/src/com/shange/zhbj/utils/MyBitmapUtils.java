package com.shange.zhbj.utils;

import android.widget.ImageView;

public class MyBitmapUtils {

	private NetCacheUtils mNetCacheUtils;
	public MyBitmapUtils(){
		mNetCacheUtils = new NetCacheUtils();
	}
	public void display(ImageView imageView,String url){
		//���ȴ��ڴ��м���ͼƬ,�ٶ����,���˷�����
		//��δӱ���(sdcard)����ͼƬ,�ٶȿ�,���˷�����
		//�����������ͼƬ,�ٶ���,�˷�����
		mNetCacheUtils.getBitmapFromNet(imageView,url);
	}

}