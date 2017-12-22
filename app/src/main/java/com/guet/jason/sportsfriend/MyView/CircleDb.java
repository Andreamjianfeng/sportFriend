package com.guet.jason.sportsfriend.MyView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 此座位已有人 on 2017/4/6.
 */

public class CircleDb extends BmobObject {
    private String my_say;
    private ArrayList<BmobFile> bmobFiles;
     private List<String> urls;
    private Model_User author;
    private List<String> concerns=new ArrayList<>();

    public void setConcerns(List<String> concerns) {
        this.concerns = concerns;
    }

    public List<String> getConcerns() {

        return concerns;
    }

    public void setAuthor(Model_User author) {
        this.author = author;
    }

    public Model_User getAuthor() {
        return author;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {

        return urls;
    }

    private BmobFile my_icon;
    private String my_name;
    private String my_introduce;

    public void setMy_say(String my_say) {
        this.my_say = my_say;
    }

    public void setBmobFiles(ArrayList<BmobFile> bmobFiles) {
        this.bmobFiles = bmobFiles;
    }

    public void setMy_icon(BmobFile my_icon) {
        this.my_icon = my_icon;
    }

    public void setMy_name(String my_name) {
        this.my_name = my_name;
    }

    public void setMy_introduce(String my_introduce) {
        this.my_introduce = my_introduce;
    }

    public String getMy_say() {

        return my_say;
    }

    public ArrayList<BmobFile> getBmobFiles() {
        return bmobFiles;
    }

    public BmobFile getMy_icon() {
        return my_icon;
    }

    public String getMy_name() {
        return my_name;
    }

    public String getMy_introduce() {
        return my_introduce;
    }
}
