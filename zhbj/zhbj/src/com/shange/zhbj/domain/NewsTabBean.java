package com.shange.zhbj.domain;

import java.util.ArrayList;

/**
 * ҳǩ�������ݶ���
 * @author ɽ��
 *
 */
public class NewsTabBean {

	public NewsTab data;
	public class NewsTab{
		public String more;
		public ArrayList<TopNews> topnews;
		public ArrayList<NewsData> news;
	}
	//ͷ������
	public class TopNews{
		public int id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

	}
	//�����б�����
	public class NewsData{
		public int id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
	}


}