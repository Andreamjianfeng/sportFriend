package com.guet.jason.sportsfriend.Xiu;

import cn.bmob.v3.BmobObject;

/**
 * Created by 吴剑锋 on 2017/4/19.
 *
 */

public class Infolistdata extends BmobObject {
    private String icon_url;
    private TModel info_text;
    private String info_time;
    private String info_adress;

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }


    public void setInfo_time(String info_time) {
        this.info_time = info_time;
    }

    public void setInfo_adress(String info_adress) {
        this.info_adress = info_adress;
    }

    public void setInfo_name(String info_name) {
        this.info_name = info_name;
    }

    public String getInfo_name() {

        return info_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setInfo_text(TModel info_text) {
        this.info_text = info_text;
    }

    public TModel getInfo_text() {

        return info_text;
    }

    public String getInfo_time() {
        return info_time;
    }

    public String getInfo_adress() {
        return info_adress;
    }

    private String info_name;
}
