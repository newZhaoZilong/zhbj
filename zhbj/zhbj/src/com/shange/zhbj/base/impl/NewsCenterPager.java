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
 * ��ҳ
 * @author ɽ��
 *
 */
public class NewsCenterPager extends BasePager {

	private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;//�˵�����ҳ


	private NewsMenu mNewsData;//������Ϣ��������
	public NewsCenterPager(Activity activity) {
		super(activity);//ΪʲôҪʵ�ֹ��췽��,��Ϊ���������ʱ��,��Ҫ���츸��
		//�������Ϊ��,Ĭ�ϴ���,super(),������ʡ�Բ�д,�в������޷�ʡ�Բ�д
		//�ܽ�һ�仰,�޲����Ŀ���ʡ�Բ�д,�в����Ĳ���,why,�в�����Ϊɶ����ʡ�Բ�д,Ĭ���в���������?
	}
	public void initData(){
		System.out.println("�������ĳ�ʼ����----");
		//Ҫ��֡������䲼�ֶ���
		/*	TextView view = new TextView(mActivity);
		view.setText("��������");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);//�ӿؼ��൱�ڵ�ǰ�ؼ�������,�ӿؼ�������ҳ��������

		fl_content.addView(view);
		tv_title.setText("��������");*/
		//���ж���û�л���,����еĻ�,�ͼ��ػ���
		String cache = CacheUtils.getCache(GlobalConstant.CATEGORY_URL, mActivity);
		if(!TextUtils.isEmpty(cache)){//���ڿ���true

			processData(cache);
			System.out.println("����:"+cache);
		}

		//���������,��ȡ����
		//��Ԫ���:XUtils
		getDataFromServer();

	}
	/**
	 * �ӷ�������ȡ����
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		//����ʽΪget,�����ַ"",��Ϊjson���ַ���,������������ַ���,����Ϊstring
		utils.send(HttpMethod.GET,GlobalConstant.CATEGORY_URL,new RequestCallBack<String>() {


			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				//����ɹ�
				String json = responseInfo.result;//��ȡ��Ӧ���json�ַ���
				System.out.println("���������ؽ��:"+json);
				processData(json);
				CacheUtils.putCache(GlobalConstant.CATEGORY_URL, json, mActivity);//���������,
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				//����ʧ��
				error.printStackTrace();
				Toast.makeText(mActivity, msg, 1).show();

			}
		} );

	}
	/**
	 * ��������
	 * @param result
	 */
	protected void processData(String json) {

		Gson gson = new Gson();
		mNewsData = gson.fromJson(json,NewsMenu.class);
		System.out.println(mNewsData);

		//��ȡ���������,������������������
		MainActivity mainUI = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUI.getLeftMenuFragment();
		//�õ�����,�����˵���Ƭ��ֵ,
		leftMenuFragment.setMenuData(mNewsData.data);//��ֵ��ʱ���ʼ��һ��
		//��ʼ��4���˵�����ҳ
		mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
		mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));//��Ҫnewscenter����?
		mMenuDetailPagers.add(new TopicDetailPager(mActivity));//��Ҫnewscenter����?
		mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity,btn_photo));//��Ҫnewscenter����?
		mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));//��Ҫnewscenter����?
		//�����Ų˵�����ҳ����ΪĬ��ҳ��
		setCurrentDetailPager(0);

	}
	public void setCurrentDetailPager(int position){

		//���¸�frameLayout�������
		BaseMenuDetailPager pager = mMenuDetailPagers.get(position);//��ȡ��ǰӦ����ʾ��ҳ��
		View view = pager.mRootView;//��ǰҳ��Ĳ���
		//���֮ǰ�ľɲ���
		fl_content.removeAllViews();
		fl_content.addView(view);//��֡������Ӳ���


		//��ʼ��ҳ������
		pager.initData();//��������µ�����,���һ�κ���

		//���±���
		tv_title.setText(mNewsData.data.get(position).title);
		//�������ͼҳ��,��Ҫ��ʾ�л���ť
		if(pager instanceof PhotosMenuDetailPager){
			btn_photo.setVisibility(View.VISIBLE);
		}else{
			//�����л���ť
			btn_photo.setVisibility(View.GONE);
		}


	}


}
