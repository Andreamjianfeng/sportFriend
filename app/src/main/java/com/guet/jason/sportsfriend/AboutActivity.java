package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.Adapter.OrderAdapter;
import com.guet.jason.sportsfriend.MyView.Participat;
import com.guet.jason.sportsfriend.Adapter.ParticipatsAdapter;
import com.guet.jason.sportsfriend.MyView.Personsport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    private Button release_about, participation_about, tui_bty;
    private RelativeLayout add_activity;
    private ListView add_list;
    private OrderAdapter adpter;
    private ParticipatsAdapter participatsAdapter;
    private List<Personsport> orders = new ArrayList<>();
    private List<Participat> participats = new ArrayList<>();
    private String my_name;
    private boolean go_find_friemd = false;
    private int sign=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "60b5cef4dc8267246e1cbbc464387755");
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        toolbar.setTitle("活动");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        my_name = ((App) getApplication()).model_User.getUsername();
        inti_view();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String about = data.getString("about");
        if (about.equals("participation")) {
            participation_about.setBackgroundResource(R.mipmap.sign4);
            release_about.setBackgroundResource(R.mipmap.sign3);
            tui_bty.setBackgroundResource(R.mipmap.sign3);
            add_activity.setVisibility(View.GONE);
            get_about();
            sign=3;
            add_list.setAdapter(participatsAdapter);
        } else if (about.equals("release")) {
            getData();
            add_list.setAdapter(adpter);
            participation_about.setBackgroundResource(R.mipmap.sign3);
            tui_bty.setBackgroundResource(R.mipmap.sign3);
            release_about.setBackgroundResource(R.mipmap.sign4);
            add_activity.setVisibility(View.VISIBLE);
            sign=2;
            go_find_friemd=true;
        } else {
            participation_about.setBackgroundResource(R.mipmap.sign3);
            tui_bty.setBackgroundResource(R.mipmap.sign4);
            release_about.setBackgroundResource(R.mipmap.sign3);
            add_activity.setVisibility(View.GONE);
            add_list.setAdapter(adpter);
            go_find_friemd = true;
            sign=1;
            go_tui();
        }
    }

    private void get_about() {
        BmobQuery<Participat> query = new BmobQuery<Participat>();
        query.addWhereEqualTo("join_userfromA", ((App) getApplication()).model_User.getUsername());
        query.findObjects(new FindListener<Participat>() {
            @Override
            public void done(List<Participat> list, BmobException e) {
                if (e == null) {
                    Collections.reverse(list);
                    participats.clear();
                    participats = list;
                    participatsAdapter.addorder(participats);
                    participatsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getData() {
        BmobQuery<Personsport> query = new BmobQuery<Personsport>();
        query.addWhereEqualTo("userfrom", ((App) getApplication()).model_User.getUsername());
        List<BmobQuery<Personsport>> queries = new ArrayList<BmobQuery<Personsport>>();
        queries.add(query);
        BmobQuery<Personsport> mainQuery = new BmobQuery<Personsport>();
        mainQuery.and(queries);
        mainQuery.findObjects(new FindListener<Personsport>() {
            @Override
            public void done(List<Personsport> list, BmobException e) {
                if (e == null) {
                    Collections.reverse(list);
                    orders.clear();
                    orders = list;
                    adpter.addorder(list, true);
                    adpter.notifyDataSetChanged();
                }
            }
        });
    }

    private void inti_view() {
        release_about = (Button) findViewById(R.id.release_bty);
        participation_about = (Button) findViewById(R.id.participation_bty);
        tui_bty = (Button) findViewById(R.id.tui_bty);
        release_about.setOnClickListener(this);
        participation_about.setOnClickListener(this);
        tui_bty.setOnClickListener(this);
        add_activity = (RelativeLayout) findViewById(R.id.add_activity);
        add_activity.setOnClickListener(this);
        add_list = (ListView) findViewById(R.id.about_list);
        adpter = new OrderAdapter(this, orders, my_name);
        participatsAdapter = new ParticipatsAdapter(this, participats);

        add_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AboutActivity.this, ReleaseActivity.class);
                Bundle bundle = new Bundle();
                if (go_find_friemd) {
                    ((App) getApplication()).list.clear();
                    ((App) getApplication()).list.add(orders.get(position));
                    bundle.putString("release", "加入");
                    bundle.putInt("sign",sign);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    ((App) getApplication()).participat=participats.get(position);
                    bundle.putString("release", "加入");
                    bundle.putInt("sign",sign);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.release_bty:
                participation_about.setBackgroundResource(R.mipmap.sign3);
                tui_bty.setBackgroundResource(R.mipmap.sign3);
                release_about.setBackgroundResource(R.mipmap.sign4);
                add_activity.setVisibility(View.VISIBLE);
                add_list.setAdapter(adpter);
                go_find_friemd = true;
                sign=2;
                getData();
                break;
            case R.id.participation_bty:
                participation_about.setBackgroundResource(R.mipmap.sign4);
                sign=3;
                tui_bty.setBackgroundResource(R.mipmap.sign3);
                release_about.setBackgroundResource(R.mipmap.sign3);
                add_activity.setVisibility(View.GONE);
                add_list.setAdapter(participatsAdapter);
                go_find_friemd = false;
                get_about();
                break;
            case R.id.add_activity:
                go_release("发布");
                break;
            case R.id.tui_bty:
                sign=1;
                participation_about.setBackgroundResource(R.mipmap.sign3);
                tui_bty.setBackgroundResource(R.mipmap.sign4);
                release_about.setBackgroundResource(R.mipmap.sign3);
                add_activity.setVisibility(View.GONE);
                add_list.setAdapter(adpter);
                go_find_friemd = true;
                go_tui();
                break;

        }
    }

    private void go_tui() {
        BmobQuery<Personsport> query = new BmobQuery<Personsport>();
        query.findObjects(new FindListener<Personsport>() {
            @Override
            public void done(List<Personsport> list, BmobException e) {
                if (e == null) {
                    Collections.reverse(list);
                    orders.clear();
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getUserfrom().equals(my_name)) {
                            orders.add(list.get(i));
                        }
                    }
                    adpter.addorder(orders, true);
                    adpter.notifyDataSetChanged();
                }
            }
        });

    }

    private void go_release(String s) {
        Intent intent = new Intent(AboutActivity.this, ReleaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("release", s);
        bundle.putInt("sign",4);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
