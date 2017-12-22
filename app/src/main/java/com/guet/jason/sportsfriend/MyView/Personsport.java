package com.guet.jason.sportsfriend.MyView;

import android.app.Application;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class Personsport extends BmobObject implements java.io.Serializable {
    public String type; //类型
    public String address; //地址
    public int mun; //人数
    public String start_time; //开始时间
    public String end_time;  //结束时间
    public BmobFile user_icon;  //地点图片
    public BmobFile user;  //用户头像图片
    private BmobGeoPoint gpsAdd;

    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    public List<String> getIcons() {

        return icons;
    }

    private List<String> icons;

    public BmobGeoPoint getGpsAdd() {
        return gpsAdd;
    }
    public void setGpsAdd(BmobGeoPoint gpsAdd) {
        this.gpsAdd = gpsAdd;
    }

    public void setUser(BmobFile user) {
        this.user = user;
    }

    public BmobFile getUser() {

        return user;
    }

    public String content;  //内容
    public String userfrom;

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {

        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public   double Latitude;  //坐标
    public   double Longitude;
    public void setUserfrom(String userfrom) {
        this.userfrom = userfrom;
    }
    public void setUser_icon(BmobFile user_icon) {
        this.user_icon = user_icon;
    }

    public BmobFile getUser_icon() {

        return user_icon;
    }
    public String getUserfrom() {

        return userfrom;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {

        return content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public void setMun(int mun) {
        this.mun = mun;
    }

    public int getMun() {

        return mun;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }


}
