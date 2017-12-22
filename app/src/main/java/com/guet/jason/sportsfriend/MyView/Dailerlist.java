package com.guet.jason.sportsfriend.MyView;

/**
 * Created by 此座位已有人 on 2017/3/24.
 */

public class Dailerlist {
    private String conten;
    private String who_con;

    public void setWho_con(String who_con) {
        this.who_con = who_con;
    }

    public String getWho_con() {

        return who_con;
    }

    public void setConten(String conten) {
        this.conten = conten;
    }

    public void setIs_ok(boolean is_ok) {
        this.is_ok = is_ok;
    }

    public boolean is_ok() {

        return is_ok;
    }

    public String getConten() {
        return conten;
    }

    private   boolean  is_ok;
}
