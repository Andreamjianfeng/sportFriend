package com.guet.jason.sportsfriend.MyView;

import cn.bmob.v3.datatype.BmobFile;

public class OrderInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L; // 定义序列化ID

	public int id;
	public String type; //类型
	public String address; //地址
	public String mun; //人数
	public String start_time; //开始时间
	public String end_time;  //结束时间
	public BmobFile user_icon;  //地点图片
	public String content;  //内容
	public String userfrom;
	
}
