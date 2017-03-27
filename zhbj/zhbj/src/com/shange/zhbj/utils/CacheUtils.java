package com.shange.zhbj.utils;

import android.content.Context;

/**
 * @author 山哥
 *网络缓存工具类
 */
public class CacheUtils {

	/**
	 * 以url为key,以json为value,保存在本地
	 * @param url 网络地址
	 * @param json 
	 * @param ctx
	 */
	public static void putCache(String url,String json,Context ctx){
		//将json存入sheredPreference中去
		SpUtils.putString(ctx, url, json);
	}
	/**
	 * 获取缓存
	 * @param url
	 * @param ctx
	 */
	public static String getCache(String url,Context ctx){
		//将json存入sheredPreference中去
		return SpUtils.getString(ctx, url, null);
	}
}
