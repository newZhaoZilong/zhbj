package com.shange.zhbj;

import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import com.shange.zhbj.utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {

	private ViewPager mViewPager;

	private int[] mImageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

	private ArrayList<ImageView> mImageList;

	private LinearLayout ll_container;

	private int mPointDis;

	private ImageView iv_red_point;

	private Button btn_start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ����,������setContentView֮ǰ����
		setContentView(R.layout.activity_guide);
		btn_start = (Button) findViewById(R.id.btn_start);
		iv_red_point = (ImageView) findViewById(R.id.iv_red_point);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		mViewPager = (ViewPager) findViewById(R.id.vp_guide);//viewpager�Դ���������
		initData();
		mViewPager.setAdapter(new GuideAdapter());


		//����layout�����������¼�,λ��ȷ����֮��,�ٻ�ȡԲ����
		//��ͼ��
		iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				//layout����ִ�н����Ļص�
				mPointDis = ll_container.getChildAt(1).getLeft()
						-ll_container.getChildAt(0).getLeft();//���
				iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);//ֱ����this,����
			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			@Override
			public void onPageSelected(int position) {
				//ĳ��ҳ�汻ѡ��
				if(position == mImageIds.length - 1){//����ֱ�Ӵ���ԭʼ������ȡ����
					btn_start.setVisibility(View.VISIBLE);//����Ϊ�ɼ���
					
				}else{
					btn_start.setVisibility(View.INVISIBLE);//����Ϊ���ɼ���
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				params.leftMargin = (int) (mPointDis*positionOffset)+mPointDis*position;//�������������,����Ϊ���ô���Ķ�������						

				// ��ҳ�滬�������еĻص�,ֻҪ�����ͻ�ִ��
				iv_red_point.setLayoutParams(params);



			}

			@Override
			public void onPageScrollStateChanged(int state) {
				//ҳ��״̬�����仯�Ļص�,
				

			}
		});
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//����sp,�洢״̬
				SpUtils.putBoolean(getApplicationContext(),"is_first_enter",false);
				
				//������ҳ��
				startActivity(new Intent(getApplicationContext(),MainActivity.class));
				
				finish();
			}
		});
	}

	//��ʼ������
	private void initData(){
		mImageList = new ArrayList<ImageView>();
		ImageView imageview;
		ImageView pointImage;//�����ܳ�,˵��linearlayout���������ظ���

		for(int i = 0;i<3;i++){
			imageview = new ImageView(this);
			//���ñ���ͼƬ
			imageview.setBackgroundResource(mImageIds[i]);
			mImageList.add(imageview);
			//��ʼ��СԲ��
			pointImage = new ImageView(this);
			pointImage.setImageResource(R.drawable.shape_point_gray);//����ͼƬ
			//��ʼ�����ֲ���,���߰�������,���ؼ���˭,����˭�����Ĳ��ֲ���,������ؼ�,ָ����ImageView�ĸ��ؼ�
			LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			if(i>0){//��֤����ߵĵ��Ǵ�0��ʼ
				//������߾�
				layoutParams.leftMargin = 20;
			}
			//���ֲ������涯��,����ÿ��view����Ҫһ��,����ߴ���ͬ���Թ���
			//�ͼ�סһ��view��Ӧһ�����ֲ���
			pointImage.setLayoutParams(layoutParams);//���ò��ֲ���,������,���Ǹ�ͼƬ���õ�
			//����Բ���,���Բ�����ʲô��ϵ,�й�ϵ,������ؼ��Բ��Ϻ�,�ͻ����

			ll_container.addView(pointImage);//����������Բ��,
		}

	}
	class GuideAdapter extends PagerAdapter{//ViewPager��������

		//item�ĸ���
		@Override
		public int getCount() {

			return mImageList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			//�̶�д��,�ж�arg1�Ƿ���view
			return arg0 == arg1;
		}
		//��ʼ��item����
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = mImageList.get(position);
			container.addView(imageView);//���ӿؼ�,������,һ�Ŷ�������
			return imageView;
		}
		//����item,һ�����������ViewGroupָ�Ķ���setAdapter��View
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View)object);
		}


	}

}