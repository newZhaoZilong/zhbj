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
	//Fragment创建
	@Override
	public void onCreate(Bundle savedInstanceState) {//activity在这里已经创建好了
		//获取当前fragment所以依赖的activity
		super.onCreate(savedInstanceState);
		System.out.println("onCreate被called");
		mActivity = getActivity();//获取当前fragment所依赖的activity,attach
	}
	//初始化fragment的布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView被called");
		View view = initView();
		return view;
	}
	//fragment所依赖的activity的onCreate方法执行结束,而不是创建activity
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		System.out.println("onActivityCreated被called");
		//初始化数据
		initData();//也就是说在start之前已经初始化好数据了
	}
	
	//初始化布局,必须由子类实现
	public abstract View initView();
	public abstract void initData();
	
}
