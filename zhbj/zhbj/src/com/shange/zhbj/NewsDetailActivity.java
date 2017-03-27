package com.shange.zhbj;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.shange.zhbj.global.GlobalConstant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class NewsDetailActivity extends Activity implements OnClickListener {

	protected static final String tag = "NewsDetailActivity";
	@ViewInject(R.id.ll_control)
	private LinearLayout llControl;
	@ViewInject(R.id.btn_back)
	private ImageButton btnBack;
	@ViewInject(R.id.btn_textsize)
	private ImageButton btnTextSize;
	@ViewInject(R.id.btn_share)
	private ImageButton btnShare;
	@ViewInject(R.id.btn_menu)
	private ImageButton btnMenu;
	@ViewInject(R.id.wv_news_detail)
	private WebView mWebView;
	@ViewInject(R.id.pb_loading)
	private ProgressBar pbLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsdetail);	
		ViewUtils.inject(this);
		

		llControl.setVisibility(View.VISIBLE);
		btnBack.setVisibility(View.VISIBLE);
		btnMenu.setVisibility(View.GONE);
		
		btnBack.setOnClickListener(this);
		btnTextSize.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		String old_url = getIntent().getExtras().getString("url");
		String[] split = old_url.split("/zhbj/");
		String new_url =  GlobalConstant.SERVER_URL+"/"+split[1];
		mWebView.loadUrl(new_url);
		WebSettings settings = mWebView.getSettings();
		settings.setBuiltInZoomControls(true);//��ʾ���Ű�ť
		settings.setUseWideViewPort(true);//֧��˫������
		settings.setJavaScriptEnabled(true);//֧��js����,Ĭ����false,����js

		mWebView.setWebViewClient(new WebViewClient(){
			//��ʼ������ҳ
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {

				super.onPageStarted(view, url, favicon);

				Log.i(tag, "��ʼ������ҳ��");
				pbLoading.setVisibility(View.VISIBLE);
			}
			//��ҳ���ؽ���
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				Log.i(tag, "��ҳ���ؽ���");
				pbLoading.setVisibility(View.INVISIBLE);
			}
			//����������ת���ߴ˷���
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);//����ת����ʱǿ���ڵ�ǰwebview�м���
				return true;
			}

		});
		//mWebView.goBack();//�����ϸ�ҳ��
		//mWebView.goForward();//������һ��ҳ��
		mWebView.setWebChromeClient(new WebChromeClient(){
			
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				//���ȷ����仯
				super.onProgressChanged(view, newProgress);
				Log.i(tag,"����:"+newProgress);
				if(newProgress == 100){
					pbLoading.setVisibility(View.INVISIBLE);
				}
			}
			@Override
			public void onReceivedTitle(WebView view, String title) {
				//��ҳ����
				super.onReceivedTitle(view, title);
				Log.i(tag,"��ҳ����"+title);
			}
			
		});
		
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			
			break;
		case R.id.btn_textsize:
			//�޸���ҳ�����С
			showChooseDialog();
			break;
		case R.id.btn_share:
			showShare();
			break;

		default:
			break;
		}
		
	}
	//private int mTempWhich;//��¼��ʱѡ��������С
	
	private int mCurrentWhich = 2;//��¼��ǰѡ�е�����Ĵ�С(���ȷ��
	/**
	 * չʾѡ�������С�ĵ���
	 */
	protected void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("��������");
		
		String[] items = new String[]{"���������","�������","��������","С������","��С������"};
		
		builder.setSingleChoiceItems(items, mCurrentWhich, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//mTempWhich = which;
				mCurrentWhich = which;
				
			}
		});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//����ѡ����������޸���ҳ����Ĵ�С
				WebSettings settings = mWebView.getSettings();
				
				switch (mCurrentWhich) {
				case 0://��������
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1://������
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2://��������
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3://С����
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 4://��С����
					settings.setTextSize(TextSize.SMALLEST);
					break;

				default:
					break;
				}
			//	mCurrentWhich = mTempWhich;
			}
		});
		builder.setNegativeButton("ȡ��", null);
		builder.show();
		
	}

	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //�ر�sso��Ȩ
		 oks.disableSSOWhenAuthorize(); 
		 
		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle(getString(R.string.share));
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("���Ƿ����ı�");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl("http://sharesdk.cn");
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		 oks.setSiteUrl("http://sharesdk.cn");
		 
		// ��������GUI
		 oks.show(this);
		 }

}
