package com.shange.zhbj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public abstract class BaseFragment extends Fragment {

	protected Activity mActivity;
	//Fragment����
	@Override
	public void onCreate(Bundle savedInstanceState) {//activity�������Ѿ���������
		//��ȡ��ǰfragment����������activity
		super.onCreate(savedInstanceState);
		System.out.println("onCreate��called");
		mActivity = getActivity();//��ȡ��ǰfragment��������activity,attach
	}
	//��ʼ��fragment�Ĳ���
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView��called");
		View view = initView();
		return view;
	}
	//fragment��������activity��onCreate����ִ�н���,�����Ǵ���activity
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		System.out.println("onActivityCreated��called");
		//��ʼ������
		initData();//Ҳ����˵��start֮ǰ�Ѿ���ʼ����������
	}
	
	//��ʼ������,����������ʵ��
	public abstract View initView();
	public abstract void initData();
	
}
