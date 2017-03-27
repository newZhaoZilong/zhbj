package com.shange.zhbj.fragment;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shange.zhbj.MainActivity;
import com.shange.zhbj.R;
import com.shange.zhbj.base.impl.NewsCenterPager;
import com.shange.zhbj.domain.NewsMenu.NewsMenuData;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * �����fragment
 * @author ɽ��
 *
 */
public class LeftMenuFragment extends BaseFragment {

	//@ViewInject(R.id.lv_list)//����װ��
	private ListView lv_list;
	private ArrayList<NewsMenuData> mNewsMenuData;
	
	private int mCurrentPos;//��ǰ��ѡ�е�item��λ��
	private LeftMenuAdapter mAdapter;
	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//ѡ���ĸ�,����mCurrentPos
				mCurrentPos = position;
				//����������
				mAdapter.notifyDataSetChanged();
				//��������
				toggle();
				//��������֮��,Ҫ�޸��������ĵ�FrameLayout�е�����
				//Ҫ�޸��������ĵ�framelayout�е�����,��Ҫ��ȡ�������Ķ���
				setCurrentDetailPager(position);
				
			}
		});
		//ViewUtils.inject(this, view);
		return view;
	}

	/**
	 * ���ݱ����޸�ҳ��
	 * @param position
	 */
	protected void setCurrentDetailPager(int position) {
		//mianactivity
		MainActivity mainUI = (MainActivity) mActivity;
		ContentFragment fragment = mainUI.getContentFragment();
		//��ȡNewsCenterPager,��ô��ȡ��,
		NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();
		//�޸��������ĵ�FragmentLayout�Ĳ���
		newsCenterPager.setCurrentDetailPager(position);
		
	}

	/**
	 * �򿪻��߹رղ����
	 */
	protected void toggle() {
		MainActivity mainUI = (MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();//�����ǰ״̬�ǿ�,���ú�͹�,��֮��Ȼ
		
	}

	@Override
	public void initData() {//�������һ��ʼ�͵�����,���Ǳ����ߵ��������Ų�������,���Կ���˵����

	}

	//���������������
	public void setMenuData(ArrayList<NewsMenuData> data){

		mCurrentPos = 0;
		//����ҳ��
		mNewsMenuData = data;
		mAdapter = new LeftMenuAdapter();
		if(mNewsMenuData!=null){
			System.out.println("������");
			lv_list.setAdapter(mAdapter);
		}

	}
	class LeftMenuAdapter extends BaseAdapter{

		@Override
		public int getCount() {

			return mNewsMenuData.size();
		}

		@Override
		public NewsMenuData getItem(int position) {

			return mNewsMenuData.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
			//�ҵ��ؼ�
			TextView tv_menu = (TextView) view.findViewById(R.id.tv_menu);
			//��ֵ
			tv_menu.setText(mNewsMenuData.get(position).title);

			if(mCurrentPos == position){
				tv_menu.setEnabled(true);//���Ϊ��ɫ,Ĭ��Ϊ0,Ҳ����˵��һ��Ĭ��Ϊ��ɫ
			}else{
				tv_menu.setEnabled(false);//�����Ϊ��ɫ
			}

			return view;
		}

	}


}
