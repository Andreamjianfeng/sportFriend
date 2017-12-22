package com.guet.jason.sportsfriend.MyView;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 此座位已有人 on 2017/3/21.
 *
 */

public class Participat extends BmobObject implements java.io.Serializable  {
    public String join_type; //类型
    public String jion_address; //地址
    public int join_mun; //人数
    public String join_start_time; //开始时间
    public String join_end_time;  //结束时间
    public BmobFile join_user_icon;  //地点图片
    public String join_content;  //内容
    public String join_userfromA;//发起者
    public String join_userfromB;//加入者
    private String add_date;
    private List<String> icons;

    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    public List<String> getIcons() {

        return icons;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public String getAdd_date() {

        return add_date;
    }

    public void setJoin_type(String join_type) {
        this.join_type = join_type;
    }

    public void setJion_address(String jion_address) {
        this.jion_address = jion_address;
    }

    public void setJoin_start_time(String join_start_time) {
        this.join_start_time = join_start_time;
    }

    public void setJoin_mun(int join_mun) {
        this.join_mun = join_mun;
    }

    public void setJoin_end_time(String join_end_time) {
        this.join_end_time = join_end_time;
    }

    public void setJoin_user_icon(BmobFile join_user_icon) {
        this.join_user_icon = join_user_icon;
    }

    public void setJoin_content(String join_content) {
        this.join_content = join_content;
    }

    public void setJoin_userfromA(String join_userfromA) {
        this.join_userfromA = join_userfromA;
    }

    public void setJoin_userfromB(String join_userfromB) {
        this.join_userfromB = join_userfromB;
    }

    public String getJoin_type() {
        return join_type;
    }

    public String getJion_address() {
        return jion_address;
    }

    public int getJoin_mun() {
        return join_mun;
    }

    public String getJoin_start_time() {
        return join_start_time;
    }

    public String getJoin_end_time() {
        return join_end_time;
    }

    public BmobFile getJoin_user_icon() {
        return join_user_icon;
    }

    public String getJoin_userfromA() {
        return join_userfromA;
    }

    public String getJoin_content() {
        return join_content;
    }

    public String getJoin_userfromB() {
        return join_userfromB;
    }
}
