package com.shange.zhbj.utils;

import android.content.Context;

/**
 * @author ɽ��
 *���绺�湤����
 */
public class CacheUtils {

	/**
	 * ��urlΪkey,��jsonΪvalue,�����ڱ���
	 * @param url �����ַ
	 * @param json 
	 * @param ctx
	 */
	public static void putCache(String url,String json,Context ctx){
		//��json����sheredPreference��ȥ
		SpUtils.putString(ctx, url, json);
	}
	/**
	 * ��ȡ����
	 * @param url
	 * @param ctx
	 */
	public static String getCache(String url,Context ctx){
		//��json����sheredPreference��ȥ
		return SpUtils.getString(ctx, url, null);
	}
}
