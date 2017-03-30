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
		//AsyncTack �첽��װ�Ĺ���,����ʵ���첽�������������(���̳߳�+handler�ķ�װ)
		new BitmapTack().execute(imageView,url);//����asyncTask
	}
	/**
	 * @author ɽ��
	 *�������͵�����:��һ������:doInBackground��Ĳ�������
	 *�ڶ�������:onProgressUpdate��Ĳ�������
	 *����������:onPostExecute��Ĳ������ͼ�doInBackground�ķ�������
	 *
	 *
	 */
	class BitmapTack extends AsyncTask<Object, Integer, Bitmap>{
		private ImageView mImageView;
		private String mUrl;
		
		//1.Ԥ����,���������߳�
		@Override
		protected void onPreExecute() {
			System.out.println("onPreExecute");
			super.onPreExecute();
		}
		//2.���ڼ���,���������߳�(���ķ���),����ֱ���첽����
		@Override
		protected Bitmap doInBackground(Object... params) {
			System.out.println("doInBackground");
			//publishProgress(values)���ô˷���ʵ�ֽ��ȸ���(�����onProgressUpdate)
			//�����߳��и��½���Ӧ�û��õ��˷���,�о�û���ð�,�����������Ϳ��������߳��и���
			
			mImageView = (ImageView)params[0];
			mUrl = (String)params[1];//����,����ǰimageview��url������һ��
			
			mImageView.setTag(mUrl);
			
			
			Bitmap bitmap =  download(mUrl);
			return bitmap;
		}
		//3.���½��ȵķ���,���������߳�
		@Override
		protected void onProgressUpdate(Integer... values) {
			System.out.println("onProgressUpdate");
			super.onProgressUpdate(values);
		}
		
		//4.���ؽ���,���������߳�(���ķ���),����ֱ�Ӹ���UI
		@Override
		protected void onPostExecute(Bitmap result) {
			System.out.println("onPostExecute");
			if(result != null){
				//��imageView����ͼƬ
				//����listview�����û��Ƶ���imageview������ܱ����item����,�Ӷ����ܽ������ͼƬ���ø�imageview����
				//������Ҫ�ڴ˴�У��,�ж��Ƿ�����ȷ��ͼƬ
				String url = (String) mImageView.getTag();
				if(url.equals(mUrl)){//�ж�ͼƬ�󶨵�url�Ƿ���ǶԵ�ǰbitmap��url
					mImageView.setImageBitmap(result);
					
					//д���ػ���
					mLocalCaheUtils.setLocalCache(url, result);
					//д�ڴ滺��
					mMemoryCacheUtils.setMemoryCache(url,result);
					
				}
				
			}
			
			super.onPostExecute(result);
		}
		
		
	}
	public Bitmap download(String url) {
		//doInBackground�������߳�,�������ﲻ��Ҫ�������߳�
		HttpURLConnection openConnection = null;
		try {
			//����url����
			URL url2 = new URL(url);
			//������
			openConnection = (HttpURLConnection) url2.openConnection();
			
			openConnection.setRequestMethod("GET");
			openConnection.setConnectTimeout(5000);//�������ӳ�ʱʱ��
			openConnection.setReadTimeout(5000);//���ö�ȡ��ʱʱ��
			openConnection.connect();//��������
			
			int responseCode = openConnection.getResponseCode();//��ȡ��Ӧ��
			if(responseCode == 200){//����200˵�����ӳɹ�
				InputStream inputStream = openConnection.getInputStream();//��ȡ��ȡ��
				
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//����ת��Ϊbitmap
				
				return bitmap;//����bitmap				
			}					
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			if(openConnection!=null){
				
				openConnection.disconnect();//�ǵ�ȡ������
			}
		}
		return null;
	}

}
