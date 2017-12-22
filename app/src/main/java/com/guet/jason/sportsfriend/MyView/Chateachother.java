package com.guet.jason.sportsfriend.MyView;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Chateachother extends BmobObject{
    private String chat_userA;//用户A
    private String chat_userB;//用户B
    private String chat_conten;//内容
    private BmobFile chat_icon;//头像



    public void setChat_iconB(BmobFile chat_iconB) {
        this.chat_iconB = chat_iconB;
    }

    public BmobFile getChat_iconB() {

        return chat_iconB;
    }

    private BmobFile chat_iconB;//头像
    private boolean chat_OK;   //是否可以聊天

    public void setChat_userA(String chat_userA) {
        this.chat_userA = chat_userA;
    }

    public void setChat_userB(String chat_userB) {
        this.chat_userB = chat_userB;
    }

    public void setChat_conten(String chat_conten) {
        this.chat_conten = chat_conten;
    }

    public void setChat_icon(BmobFile chat_icon) {
        this.chat_icon = chat_icon;
    }

    public void setChat_OK(boolean chat_OK) {
        this.chat_OK = chat_OK;
    }

    public String getChat_userB() {
        return chat_userB;

    }

    public String getChat_conten() {
        return chat_conten;
    }

    public BmobFile getChat_icon() {
        return chat_icon;
    }

    public boolean isChat_OK() {
        return chat_OK;
    }

    public String getChat_userA() {

        return chat_userA;
    }


}
