package com.shange.zhbj.base.impl.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shange.zhbj.R;
import com.shange.zhbj.base.BaseMenuDetailPager;
import com.shange.zhbj.domain.PhotosMenu;
import com.shange.zhbj.domain.PhotosMenu.PhotoNews;
import com.shange.zhbj.global.GlobalConstant;
import com.shange.zhbj.utils.CacheUtils;
import com.shange.zhbj.utils.MyBitmapUtils;

/**
 * @author 山哥
 *菜单详情页-组图
 *
 *
 */
public class PhotosMenuDetailPager extends BaseMenuDetailPager {

	@ViewInject(R.id.lv_photo)
	private ListView mListView;
	@ViewInject(R.id.gv_photo)
	private GridView mGridView;
	private ArrayList<PhotoNews> mPhotoNewsList;
	private ImageButton mBtnPhoto;
	private boolean isListView = true;


	public PhotosMenuDetailPager(Activity activity,ImageButton btnPhoto) {
		super(activity);//这儿如果设置点击事件的话,mBtnPhoto为空,会出现空指针异常
		mBtnPhoto = btnPhoto;//引用的传递
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_photo_menu_detail, null);
		ViewUtils.inject(this, view);


		return view;
	}
	@Override
	public void initData() {
		//那当然是在这里初始化listview了

		String cache = CacheUtils.getCache(GlobalConstant.PHOTO_URL, mActivity);
		if(!TextUtils.isEmpty(cache)){//如果为空是true,记得加!
			processData(cache);
		}

		getDataFromServer();
		mBtnPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//点击切换为gridview
				if(isListView){
					mListView.setVisibility(View.GONE);
					mGridView.setVisibility(View.VISIBLE);
					mBtnPhoto.setImageResource(R.drawable.icon_pic_list_type);

				}else{
					mListView.setVisibility(View.VISIBLE);
					mGridView.setVisibility(View.GONE);
					mBtnPhoto.setImageResource(R.drawable.icon_pic_grid_type);
				}
				isListView = !isListView;

			}
		});
	}

	private void getDataFromServer() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, GlobalConstant.PHOTO_URL, new RequestCallBack<String>() {//返回的数据类型

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;//获取相应结果
				processData(result);//加工数据
				CacheUtils.putCache(GlobalConstant.PHOTO_URL, result, mActivity);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				//请求失败
				error.printStackTrace();
				Toast.makeText(mActivity, msg, 1).show();

			}
		});

	}

	protected void processData(String result) {
		Gson gson = new Gson();

		PhotosMenu photosMenu = gson.fromJson(result, PhotosMenu.class);//赋值

		mPhotoNewsList = photosMenu.data.news;//集合里有数据了
		//给listview赋值
		if(mPhotoNewsList!=null){
			MyAdapter myAdapter = new MyAdapter();
			mListView.setAdapter(myAdapter);
			mGridView.setAdapter(myAdapter);
		}



	}

	class MyAdapter extends BaseAdapter{

		//	private BitmapUtils  mbitmap;
		private MyBitmapUtils  mbitmap;
		public MyAdapter(){

			//	mbitmap = new BitmapUtils(mActivity);
			mbitmap = new MyBitmapUtils();//自己的太慢
			
			//mbitmap.configDefaultLoadingImage(R.drawable.image_demo);//配置默认图片
		}
		@Override
		public int getCount() {

			return mPhotoNewsList.size();
		}

		@Override
		public PhotoNews getItem(int position) {

			return mPhotoNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView==null){
				convertView = View.inflate(mActivity, R.layout.list_item_photos, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
				holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();

			}
			//获取数据对象
			PhotoNews news = getItem(position);
			//赋值
			String old_listimage = news.listimage;
			String[] split = old_listimage.split("/zhbj/");//这样写防止切错
			String new_listimage = GlobalConstant.SERVER_URL + "/" + split[1];
			mbitmap.display(holder.iv_icon, new_listimage);//给imageview附上图片
			holder.tv_title.setText(news.title);

			return convertView;
		}

	}
	static class ViewHolder{
		ImageView iv_icon;
		TextView tv_title;
	}

}
