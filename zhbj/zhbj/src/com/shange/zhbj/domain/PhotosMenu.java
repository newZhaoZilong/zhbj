package com.shange.zhbj.domain;

import java.util.ArrayList;

public class PhotosMenu {

	public PhotosData data;
	
	public class PhotosData{
		
		public ArrayList<PhotoNews> news;
	}
	public class PhotoNews{
		public int id;
		public String listimage;
		public String title;
		
	}
	
}
