package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.guet.jason.sportsfriend.Adapter.CircleAdapter;
import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.CircleDb;
import com.guet.jason.sportsfriend.MyView.ListViewForScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CircleActivity extends AppCompatActivity implements View.OnClickListener {
    private ListViewForScrollView circle_list;
    private List<CircleDb> orders = new ArrayList<>();
    private CircleAdapter circleAdapter;
    private ImageView myFriendIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.circle_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.xiangji);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CircleActivity.this, Published_aboutActivity.class));
            }
        });
        inti_view();
    }

    private void inti_view() {
        circle_list = (ListViewForScrollView) findViewById(R.id.circle_list);
        myFriendIcon= (ImageView) findViewById(R.id.myFriendIcon);
        findViewById(R.id.circle_button).setOnClickListener(this);
        circleAdapter = new CircleAdapter(CircleActivity.this, orders, ((App) getApplication()).model_User);
        circle_list.setAdapter(circleAdapter);
        Picasso.with(this).load(((App) getApplication()).model_User.getIcon().getFileUrl()).into(myFriendIcon);
    }
    @Override
    protected void onResume() {
        super.onResume();
        find_data();
    }

    private void find_data() {
        BmobQuery<CircleDb> query = new BmobQuery<CircleDb>();
        query.order("-createdAt");
        query.include("author");
        query.findObjects(new FindListener<CircleDb>() {
            @Override
            public void done(List<CircleDb> list, BmobException e) {
                if (e == null) {
                    orders = list;
                    circleAdapter.addorder(orders);
                    circleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.circle_button:
                finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        orders.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
