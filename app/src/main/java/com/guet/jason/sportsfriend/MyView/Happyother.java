package com.guet.jason.sportsfriend.MyView;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 此座位已有人 on 2017/3/24.
 */

public class Happyother extends BmobObject {
    private String happy_userA;//用户A
    private String happy_userB;//用户B
    private String who_con;
    public void setChat_voice(BmobFile chat_voice) {
        this.chat_voice = chat_voice;
    }

    public BmobFile getChat_voice() {

        return chat_voice;
    }

    private BmobFile chat_voice;//语言消息
    public void setWho_con(String who_con) {
        this.who_con = who_con;
    }

    public String getWho_con() {

        return who_con;
    }

    private String happy_conten;//内容
    private BmobFile happy_icon;//头像
    private BmobFile happy_iconB;//头像

    public void setHappy_iconB(BmobFile happy_iconB) {
        this.happy_iconB = happy_iconB;
    }

    public BmobFile getHappy_iconB() {

        return happy_iconB;
    }

    private boolean happy_OK;   //是否可以聊天

    public void setHappy_list(ArrayList<Dailerlist> happy_list) {
        this.happy_list = happy_list;
    }

    private ArrayList<Dailerlist> happy_list;

    public void setHappy_userA(String happy_userA) {
        this.happy_userA = happy_userA;
    }

    public void setHappy_userB(String happy_userB) {
        this.happy_userB = happy_userB;
    }

    public void setHappy_conten(String happy_conten) {
        this.happy_conten = happy_conten;
    }

    public ArrayList<Dailerlist> getHappy_list() {
        return happy_list;
    }

    public void setHappy_icon(BmobFile happy_icon) {
        this.happy_icon = happy_icon;
    }

    public void setHappy_OK(boolean happy_OK) {
        this.happy_OK = happy_OK;
    }

    public String getHappy_userA() {

        return happy_userA;
    }

    public String getHappy_userB() {
        return happy_userB;
    }

    public String getHappy_conten() {
        return happy_conten;
    }

    public BmobFile getHappy_icon() {
        return happy_icon;
    }

    public boolean isHappy_OK() {
        return happy_OK;
    }
}
