package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.My_concern;
import com.guet.jason.sportsfriend.MyView.Tool;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PeopleActivity extends AppCompatActivity {
    private ImageView my_people_img;
    private TextView my_name, my_people_id, my_people_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.people_toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init_view();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (((App) getApplication()).model_User.getNickname() == null)
            my_name.setText(((App) getApplication()).model_User.getUsername());
        else
            my_name.setText(((App) getApplication()).model_User.getNickname());
        String id = "id:" + ((App) getApplication()).model_User.getObjectId();
        my_people_id.setText(id);
        my_people_address.setText("广西—桂林");
        Picasso.with(PeopleActivity.this).load(((App) getApplication()).model_User.getIcon().getFileUrl()).into(my_people_img);
        BmobQuery<My_concern> query = new BmobQuery<>();
        query.addWhereEqualTo("my_name", ((App) getApplication()).model_User.getUsername());
        query.findObjects(new FindListener<My_concern>() {
            @Override
            public void done(List<My_concern> list, BmobException e) {

            }
        });
    }

    private void init_view() {
        my_name = (TextView) findViewById(R.id.my_name);
        my_people_address = (TextView) findViewById(R.id.my_people_address);
        my_people_id = (TextView) findViewById(R.id.my_people_id);
        my_people_img = (ImageView) findViewById(R.id.my_people_img);
        my_people_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PeopleActivity.this, User_Info_SettingActivity.class));
            }
        });
        findViewById(R.id.backLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tool.launchActivity(PeopleActivity.this, WelcomActivity.class);
                finish();
                Tool.removeAllActivity();
            }
        });
        findViewById(R.id.aboutMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tool.launchActivity(PeopleActivity.this, AboutMeActivity.class);
            }
        });
    }
}
