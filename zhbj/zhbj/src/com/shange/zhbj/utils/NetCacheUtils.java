package com.shange.zhbj.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCacheUtils {
	
	private LocalCaheUtils mLocalCaheUtils;
	
	private MemoryCacheUtils mMemoryCacheUtils;
	
	public NetCacheUtils(LocalCaheUtils localCaheUtils,MemoryCacheUtils memoryCacheUtils){
		mLocalCaheUtils = localCaheUtils;
		mMemoryCacheUtils = memoryCacheUtils;
	}

	public void getBitmapFromNet(ImageView imageView, String url) {
		
		//
		//AsyncTack 异步封装的工具,可以实现异步请求及主界面更新(对线程池+handler的封装)
		new BitmapTack().execute(imageView,url);//启动asyncTask
	}
	/**
	 * @author 山哥
	 *三个泛型的意义:第一个泛型:doInBackground里的参数类型
	 *第二个泛型:onProgressUpdate里的参数类型
	 *第三个泛型:onPostExecute里的参数类型及doInBackground的返回类型
	 *
	 *
	 */
	class BitmapTack extends AsyncTask<Object, Integer, Bitmap>{
		private ImageView mImageView;
		private String mUrl;
		
		//1.预加载,运行在主线程
		@Override
		protected void onPreExecute() {
			System.out.println("onPreExecute");
			super.onPreExecute();
		}
		//2.正在加载,运行在子线程(核心方法),可以直接异步请求
		@Override
		protected Bitmap doInBackground(Object... params) {
			System.out.println("doInBackground");
			//publishProgress(values)调用此方法实现进度更新(会调用onProgressUpdate)
			//在子线程中更新进度应该会用到此方法,感觉没屌用啊,进度条本来就可以再子线程中更新
			
			mImageView = (ImageView)params[0];
			mUrl = (String)params[1];//打标记,将当前imageview和url绑定在了一起
			
			mImageView.setTag(mUrl);
			
			
			Bitmap bitmap =  download(mUrl);
			return bitmap;
		}
		//3.跟新进度的方法,运行在主线程
		@Override
		protected void onProgressUpdate(Integer... values) {
			System.out.println("onProgressUpdate");
			super.onProgressUpdate(values);
		}
		
		//4.加载结束,运行在主线程(核心方法),可以直接更新UI
		@Override
		protected void onPostExecute(Bitmap result) {
			System.out.println("onPostExecute");
			if(result != null){
				//给imageView设置图片
				//由于listview的重用机制导致imageview对象可能被多个item共用,从而可能将错误的图片设置给imageview对象
				//所以需要在此处校验,判断是否是正确的图片
				String url = (String) mImageView.getTag();
				if(url.equals(mUrl)){//判断图片绑定的url是否就是对当前bitmap的url
					mImageView.setImageBitmap(result);
					
					//写本地缓存
					mLocalCaheUtils.setLocalCache(url, result);
					//写内存缓存
					mMemoryCacheUtils.setMemoryCache(url,result);
					
				}
				
			}
			
			super.onPostExecute(result);
		}
		
		
	}
	public Bitmap download(String url) {
		//doInBackground是在子线程,所以这里不需要开启子线程
		HttpURLConnection openConnection = null;
		try {
			//创建url对象
			URL url2 = new URL(url);
			//打开连接
			openConnection = (HttpURLConnection) url2.openConnection();
			
			openConnection.setRequestMethod("GET");
			openConnection.setConnectTimeout(5000);//设置连接超时时间
			openConnection.setReadTimeout(5000);//设置读取超时时间
			openConnection.connect();//连接网络
			
			int responseCode = openConnection.getResponseCode();//获取响应码
			if(responseCode == 200){//等于200说明连接成功
				InputStream inputStream = openConnection.getInputStream();//获取读取流
				
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//将流转化为bitmap
				
				return bitmap;//返回bitmap				
			}					
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(openConnection!=null){
				
				openConnection.disconnect();//记得取消连接
			}
		}
		return null;
	}

}
