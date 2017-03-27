package com.shange.zhbj.domain;

import java.util.ArrayList;

/**
 * 页签详情数据对象
 * @author 山哥
 *
 */
public class NewsTabBean {

	public NewsTab data;
	public class NewsTab{
		public String more;
		public ArrayList<TopNews> topnews;
		public ArrayList<NewsData> news;
	}
	//头条新闻
	public class TopNews{
		public int id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

	}
	//新闻列表对象
	public class NewsData{
		public int id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
	}


}
