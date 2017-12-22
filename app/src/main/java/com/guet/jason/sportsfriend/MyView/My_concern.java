package com.guet.jason.sportsfriend.MyView;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by 吴剑锋 on 2017/4/25.
 * 关注用户date
 */

public class My_concern extends BmobObject{
        public void setCircleDb(CircleDb circleDb) {
                this.circleDb = circleDb;
        }
        private String id;

        public void setId(String id) {
                this.id = id;
        }

        public String getId() {

                return id;
        }

        public CircleDb getCircleDb() {

                return circleDb;
        }

        public String my_name;
        public List<String> concern;
        private CircleDb circleDb;
        private Model_User model_user;

        public void setModel_user(Model_User model_user) {
                this.model_user = model_user;
        }

        public Model_User getModel_user() {

                return model_user;
        }
}
