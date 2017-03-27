package com.shange.zhbj.utils;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;

public class SpUtils {
	//д

	private static SharedPreferences sharedPreferences;

	public static void createFile(Context ctx){
		//�жϸ��ļ�����
		if(sharedPreferences==null){
			sharedPreferences = ctx.getSharedPreferences("config_zhbj", ctx.MODE_PRIVATE);//���Ǵ���һ��xml�ļ�,
			//������shared_prefs�ļ���

		}
	}
	/**
	 * д��boolean����ֵsp��
	 * @param ctx �����Ļ���
	 * @param key	�洢�ڵ�����
	 * @param value	�洢�ڵ��ֵboolean
	 */
	public static void putBoolean(Context ctx,String key,boolean value){
		//�����жϸ��ļ�����
		createFile(ctx);
		//���������Ҫedit,ȡ�����ݲ�Ҫ,
		sharedPreferences.edit().putBoolean(key, value).commit();//���Ǵ洢�ڵ�				
	}
	//��
	/**
	 * ��ȡboolean��ʶ��sp��
	 * @param ctx �����Ļ���
	 * @param key �洢�ڵ�����
	 * @param value	û�д˽ڵ�Ĭ��ֵ
	 * @return	Ĭ��ֵ���ߴ˽ڵ��ȡ���Ľ��
	 */
	public static boolean getBoolean(Context ctx,String key,boolean value){

		createFile(ctx);
		return sharedPreferences.getBoolean(key, value);//ȡ���ڵ�,����ڵ㲻����,��Ĭ��ֵ
	}
	/**
	 * д��boolean����ֵsp��
	 * @param ctx �����Ļ���
	 * @param key	�洢�ڵ�����
	 * @param value	�洢�ڵ��ֵboolean
	 */
	public static void putString(Context ctx,String key,String value){
		//�����жϸ��ļ�����
		createFile(ctx);
		//���������Ҫedit,ȡ�����ݲ�Ҫ,
		sharedPreferences.edit().putString(key, value).commit();//���Ǵ洢�ڵ�				
	}
	//��
	/**
	 * ��ȡboolean��ʶ��sp��
	 * @param ctx �����Ļ���
	 * @param key �洢�ڵ�����
	 * @param value	û�д˽ڵ�Ĭ��ֵ
	 * @return	Ĭ��ֵ���ߴ˽ڵ��ȡ���Ľ��
	 */
	public static String getString(Context ctx,String key,String value){
		createFile(ctx);
		return sharedPreferences.getString(key, value);//ȡ���ڵ�,����ڵ㲻����,��Ĭ��ֵ
	}

	public static int getInt(Context ctx,String key,int value) {
		createFile(ctx);
		return sharedPreferences.getInt(key, value);//ȡ���ڵ�,����ڵ㲻����,��Ĭ��ֵ		
	}

	public static void putInt(Context ctx,String key,int value){
		//�����жϸ��ļ�����
		createFile(ctx);
		//���������Ҫedit,ȡ�����ݲ�Ҫ,
		sharedPreferences.edit().putInt(key, value).commit();//���Ǵ洢�ڵ�				
	}
	/**
	 * ���ڵ��sp���Ƴ�
	 * @param ctx	�����Ķ���
	 * @param key ��Ҫ�Ƴ��Ľڵ�
	 */
	public static void remove(Context ctx,String key) {
		//�����жϸ��ļ�����
		createFile(ctx);
		//���������Ҫedit,ȡ�����ݲ�Ҫ,
		sharedPreferences.edit().remove(key).commit();//�Ƴ��ڵ�,��ȻҲtmҪ�ύ

	}
}
