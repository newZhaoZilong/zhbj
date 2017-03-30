package com.shange.zhbj.utils;

import com.shange.zhbj.R;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitmapUtils {
	
	private MemoryCacheUtils mMemoryCacheUtils;
	private LocalCaheUtils mLocalCaheUtils;
	private NetCacheUtils mNetCacheUtils;

	public MyBitmapUtils(){
		
		mMemoryCacheUtils = new MemoryCacheUtils();//�ڴ滺��
		mLocalCaheUtils = new LocalCaheUtils();	//���ػ���
		mNetCacheUtils = new NetCacheUtils(mLocalCaheUtils,mMemoryCacheUtils);//���绺��
	}
	public void display(ImageView imageView,String url){
		//���ȴ��ڴ��м���ͼƬ,�ٶ����,���˷�����
		//��δӱ���(sdcard)����ͼƬ,�ٶȿ�,���˷�����
		//�����������ͼƬ,�ٶ���,�˷�����
		//����Ĭ��ͼƬ
		imageView.setImageResource(R.drawable.pic_item_list_default);
		
		Bitmap bitmap = mMemoryCacheUtils.getMemoryCache(url);
		
		if(mMemoryCacheUtils.getMemoryCache(url)!=null){
			imageView.setImageBitmap(bitmap);
			System.out.println("���ڴ����ͼƬ��");
			return;
			
		}
		//��δӱ���(sdcard)����ͼƬ,�ٶȿ�,���˷�����
		bitmap = mLocalCaheUtils.getLocalCache(url);
		if(bitmap != null){
			imageView.setImageBitmap(bitmap);
			System.out.println("�ӱ��ؼ���ͼƬ");
			//д�ڴ滺��
			mMemoryCacheUtils.setMemoryCache(url, bitmap);
			return;
			
		}
		//������������ͼƬ,�ٶ���,�˷�����
		mNetCacheUtils.getBitmapFromNet(imageView, url);
		
	}

}
