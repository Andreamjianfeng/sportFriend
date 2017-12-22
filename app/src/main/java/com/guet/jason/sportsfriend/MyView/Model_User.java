package com.guet.jason.sportsfriend.MyView;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Jason on 2017/3/1.
 */
public class Model_User extends BmobUser implements java.io.Serializable{
private BmobObject myicon;
private String nickname;

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduce() {

        return introduce;
    }

    private String introduce;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {

        return nickname;
    }

    public void setMyicon(BmobObject myicon) {

        this.myicon = myicon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    public BmobFile getIcon() {

        return icon;
    }

    public BmobFile icon;


    public BmobObject getMyicon() {
        return myicon;
    }
}
