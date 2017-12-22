package com.guet.jason.sportsfriend.MyView;

import cn.bmob.v3.BmobObject;

/**
 * Created by 吴剑锋 on 2017/4/20.
 */

public class Colundata extends BmobObject {
    private String uri;
    private String text_up;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setText_up(String text_up) {
        this.text_up = text_up;
    }

    public void setText_on(String text_on) {
        this.text_on = text_on;
    }

    public String getText_on() {

        return text_on;
    }

    public String getUri() {
        return uri;
    }

    public String getText_up() {
        return text_up;
    }

    private String text_on;
}
