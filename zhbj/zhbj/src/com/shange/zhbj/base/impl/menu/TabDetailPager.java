package com.shange.zhbj.base.impl.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
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
import com.shange.zhbj.NewsDetailActivity;
import com.shange.zhbj.R;
import com.shange.zhbj.base.BaseMenuDetailPager;
import com.shange.zhbj.domain.NewsTabBean;
import com.shange.zhbj.domain.NewsMenu.NewsTabData;
import com.shange.zhbj.domain.NewsTabBean.NewsData;
import com.shange.zhbj.domain.NewsTabBean.TopNews;
import com.shange.zhbj.global.GlobalConstant;
import com.shange.zhbj.utils.CacheUtils;
import com.shange.zhbj.utils.SpUtils;
import com.shange.zhbj.view.PullToRefreshListView;
import com.shange.zhbj.view.PullToRefreshListView.OnRefreshListener;
import com.shange.zhbj.view.TopNewsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * @author ɽ��
 *ҳǩҳ�����
 */
public class TabDetailPager extends BaseMenuDetailPager{

	public static final String tag = "TabDetailPager";
	private NewsTabData mTabData;//����ҳǩ����������
	//private TextView view;
	@ViewInject(R.id.vp_top_news)
	private TopNewsViewPager mViewPager;
	private String mUrl;
	private ArrayList<TopNews> mTopNewsList;
	@ViewInject(R.id.tv_title)
	private TextView tvTitle;
	@ViewInject(R.id.indicator)
	private CirclePageIndicator mIndicator;

	@ViewInject(R.id.lv_list)
	private PullToRefreshListView mListView;

	private ArrayList<NewsData> mNewsList;
	private String mMoreUrl;
	private NewsAdapter mNewsAdapter;
	
	private Handler mHandler;

	public TabDetailPager(Activity activity, NewsTabData newsTabData) {
		super(activity);//�����Ѿ���ʼ��,view��,�϶��ᱨ��ָ��
		mTabData = newsTabData;
		mUrl = GlobalConstant.SERVER_URL+mTabData.url;
		Log.i(tag, mUrl);

	}

	@Override
	public View initView() {
		//Ҫ��֡������䲼�ֶ���
		/*	view = new TextView(mActivity);
		//view.setText(mTabData.title);
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);*/
		View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
		ViewUtils.inject(this, view);//���view�����ҵ�listview
		//��listview����ͷ����
		View mHeaderView = View.inflate(mActivity, R.layout.list_item_header, null);
		ViewUtils.inject(this, mHeaderView);//���view�����ҵ�textview,imageview��
		mListView.addHeaderView(mHeaderView);
		//5.ǰ�ν������ûص�
		mListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				//ˢ������
				getDataFromServer();
			}

			@Override
			public void onLoadMore() {
				if(mMoreUrl != null){
					//����һҳ
					getMoreDataFromServer();
				}else{
					//û����һҳ
					Toast.makeText(mActivity, "û�и���������",0).show();
					mListView.onRefreshComplete(false);//ʧ��,����Ҫ����ʱ��
					
				}

			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int headerViewsCount = mListView.getHeaderViewsCount();//��ȡͷ��������
				
				position = position - headerViewsCount;//��Ҫ��ȥͷ���ֵ�ռλ
				NewsData newsData = mNewsList.get(position);
				String readIds = SpUtils.getString(mActivity, "read_ids", "");
				if(!readIds.contains(newsData.id +"")){//ֻ�в�������ǰid,��׷��
					//�����ظ�����ͬһ��id
					
					readIds = readIds + newsData.id +",";
					
					SpUtils.putString(mActivity, "read_ids", readIds);
					
				}
				//Ҫ���������item��������ɫ��Ϊ��ɫ
				TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
				tvTitle.setTextColor(Color.GRAY);
				//���֮����ת����һ��activity
				Intent intent = new Intent(mActivity,NewsDetailActivity.class);
				//��url����ȥ
				String url = newsData.url;
				intent.putExtra("url", url);
				mActivity.startActivity(intent);
				
			}
		});
		/*mListView.setOnScrollListener(new PullToRefreshListView(mActivity)//�����������,����ˢ�� 
		new/*new PullToRefreshListView(mActivity){
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				Log.i(tag, "���Ӹ�������ķ���1===>onScrollStateChanged");
				

			}
			//�������̻ص�
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				Log.i(tag, "���Ӹ�������ķ���2===>onScroll");
			}
			
		});*/
		return view;
	}


	public void initData(){

		//	view.setText(mTabData.title);
		String cache = CacheUtils.getCache(mUrl, mActivity);//���ݼ���ȡ����
		if(!TextUtils.isEmpty(cache)){
			processData(cache,false);
		}
		getDataFromServer();
	}
	private void getDataFromServer(){
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				processData(result,false);
				Log.i(tag, result);
				CacheUtils.putCache(mUrl, result, mActivity);//����sheredPreference

				//��������ˢ���¼�;
				mListView.onRefreshComplete(true);//,��������ˢ�µ���
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				error.printStackTrace();
				Toast.makeText(mActivity, msg, 0).show();
				mListView.onRefreshComplete(false);//ʧ��,����Ҫ����ʱ��
			}
		});

	}
	protected void getMoreDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				processData(result,true);
				Log.i(tag, result);
				//	CacheUtils.putCache(mUrl, result, mActivity);//����sheredPreference

				//��������ˢ���¼�;
					mListView.onRefreshComplete(true);//,��������ˢ�µ���
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				error.printStackTrace();
				Toast.makeText(mActivity, msg, 0).show();
					mListView.onRefreshComplete(false);//ʧ��,����Ҫ����ʱ��
			}
		});

	}

	/**
	 * �ӹ�����
	 * @param result
	 */
	//��Ҫ����Ϊ,�кܶණ�����ظ����ʱ�򶼲�����
	protected void processData(String result,boolean isMore) {

		//һ����ȡ������,�����ǻ�����Ļ��Ƿ��������,gson�Զ���ֵ��NewsTabBean
		Gson gson = new Gson();

		NewsTabBean newsTabBean = gson.fromJson(result, NewsTabBean.class);

		String moreUrl = newsTabBean.data.more;
		if(!TextUtils.isEmpty(moreUrl)){
			mMoreUrl = GlobalConstant.SERVER_URL + moreUrl;
		}else{
			mMoreUrl = null;
		}
		if(!isMore){
			//�ȸ�ͷ������,ͷ����������viewpager����
			//���ü���
			mTopNewsList = newsTabBean.data.topnews;
			if(mTopNewsList!=null){
				/*String imageUrl = mTopNewsList.get(0).topimage;
				Log.i(tag, "imageUrl---------------"+imageUrl);*/
				mViewPager.setAdapter(new TopNewsAdapter());
				mIndicator.setViewPager(mViewPager);//ʲô��������,��Ҫ���ø�ָʾ��
				mIndicator.setSnap(true);//���շ�ʽչʾ
				mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						//����ͷ�����ű���
						TopNews topNews = mTopNewsList.get(position);
						tvTitle.setText(topNews.title);

					}

					@Override
					public void onPageScrolled(int position, float positionOffset,
							int positionOffsetPixels) {


					}

					@Override
					public void onPageScrollStateChanged(int state) {


					}
				});
				tvTitle.setText(mTopNewsList.get(0).title);//��ʼ����һ��

			}
			mNewsList = newsTabBean.data.news;
			if(mNewsList!=null){
				mNewsAdapter = new NewsAdapter();
				mListView.setAdapter(mNewsAdapter);
			}	
			if(mHandler == null){
				mHandler = new Handler(){
					public void handleMessage(android.os.Message msg) {
						
						int currentItem = mViewPager.getCurrentItem();
						currentItem++;
						if(currentItem>mTopNewsList.size() - 1){
							currentItem = 0;//����Ѿ��������һ��ҳ��,������һҳ
						}
							mViewPager.setCurrentItem(currentItem);
							mHandler.sendEmptyMessageDelayed(0, 3000);//����������ʱ3�����Ϣ,�γ���ѭ��
							//�ݹ�			
					};
					
				};
				//��֤�����Զ��ֲ��߼�ִֻ��һ��
				mHandler.sendEmptyMessageDelayed(0, 3000);//������ʱ3�����Ϣ
				
				mViewPager.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						//ֹͣ
						switch (event.getAction()) {
						
						case MotionEvent.ACTION_DOWN:
							//ֹͣ����Զ��ֲ�
							//ɾ��handler��������Ϣ
							mHandler.removeCallbacksAndMessages(null);
							/*mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									
									
								}
							});*/
							break;
						case MotionEvent.ACTION_CANCEL://ȡ���¼�,������viewpager��,ֱ�ӻ���listview,�����¼��޷���Ӧ,
							mHandler.sendEmptyMessageDelayed(0, 3000);
							
							
							break;
						case MotionEvent.ACTION_UP:
							//�������
							mHandler.sendEmptyMessageDelayed(0, 3000);
							
							break;

						default:
							break;
						}
						
						return true;
					}
				});
			}
			
			
			
		}else{
			//����ֻ��Ҫ������news���Ͼ�����
			 ArrayList<NewsData> morenews = newsTabBean.data.news;
			 mNewsList.addAll(morenews);
			 mNewsAdapter.notifyDataSetChanged();
			
		}


	}

	class TopNewsAdapter extends PagerAdapter{

		private BitmapUtils mBitmapUtils;

		public TopNewsAdapter() {
			mBitmapUtils = new BitmapUtils(mActivity);
			//���ü����е�Ĭ��ͼƬ
			mBitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);

		}
		@Override
		public int getCount() {

			return mTopNewsList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			boolean test = (view == object);

			Log.i(tag,String.valueOf(test));
			return view == object;
		}



		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//�����������Ҫ,viewPager���涼��ͼƬ
			ImageView view = new ImageView(mActivity);
			view.setScaleType(ScaleType.FIT_XY);//������������
			//	view.setImageResource(R.drawable.topnews_item_default);
			//����ͼƬ-��ͼƬ���ø�imageView-�����ڴ����-����
			String imageUrl = mTopNewsList.get(position).topimage;
			String[] split = imageUrl.split("zhbj");
			imageUrl = GlobalConstant.SERVER_URL+split[1];//�������¸ĵ�ַ
			Log.i(tag, imageUrl);
			mBitmapUtils.display(view, imageUrl);//view,���ͼƬ������,imageUrl,url��ַ
			//һ���ǵ�Ҫ����view
			container.addView(view);

			return view;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//ɾ��ͨ��instantiateItem�����ص���ͬ�Ķ���,Ҳ����˵,
			container.removeView((View)object);
		}

	}

	class NewsAdapter extends BaseAdapter{

		private BitmapUtils mBitmapUtils;

		public NewsAdapter(){
			mBitmapUtils = new BitmapUtils(mActivity);
			//����Ĭ��ͼƬ
			mBitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}
		@Override
		public int getCount() {

			return mNewsList.size();
		}

		@Override
		public NewsData getItem(int position) {

			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_item_news, null);
				holder = new ViewHolder();
				holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
				convertView.setTag(holder);

			}else{
				holder = (ViewHolder)convertView.getTag();
			}
			NewsData news = getItem(position);//��ȡnews����
			//��ֵ
			holder.tvTitle.setText(news.title);
			//���ݱ��ؼ�¼������Ѷ�δ��
			String readIds = SpUtils.getString(mActivity, "read_ids", "");
			if(readIds.contains(news.id +"")){//ֻ�в�������ǰid,��׷��
				//�����ظ�����ͬһ��id
				holder.tvTitle.setTextColor(Color.GRAY);
			}else{
				holder.tvTitle.setTextColor(Color.BLACK);
			}
			
			holder.tvDate.setText(news.pubdate);
			
			String url = news.listimage;
			String[] split = url.split("/zhbj");

			mBitmapUtils.display(holder.ivIcon, GlobalConstant.SERVER_URL+split[1]);

			return convertView;
		}


	}
	static class ViewHolder{
		public ImageView ivIcon;
		public TextView tvTitle;
		public TextView tvDate;

	}
	

}