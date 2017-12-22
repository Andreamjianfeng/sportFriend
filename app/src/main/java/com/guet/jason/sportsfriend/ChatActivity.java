package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.guet.jason.sportsfriend.Adapter.CharAdapter;
import com.guet.jason.sportsfriend.Adapter.EncounterAdapter;
import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.Chateachother;
import com.guet.jason.sportsfriend.MyView.Participat;
import com.guet.jason.sportsfriend.MyView.Personsport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView char_list;
    private int sign = 1, num;
    private boolean is_chat = false, is_mun = true;
    private Button encounter_bty, char_bty;
    private List<Chateachother> participats = new ArrayList<>();
    private List<Personsport> personsports = new ArrayList<>();
    private CharAdapter char_ada;
    private EncounterAdapter encounterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bmob.initialize(this, "60b5cef4dc8267246e1cbbc464387755");
        Toolbar toolbar = (Toolbar) findViewById(R.id.char_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inti_view();
        get_data();
    }

    private void get_data() {
        BmobQuery<Personsport> query1 = new BmobQuery<Personsport>();
        query1.addWhereNear("gpsAdd", new BmobGeoPoint(((App) getApplication()).User_Longitude, ((App) getApplication()).User_Latitude));
        query1.setLimit(10);
        query1.findObjects(new FindListener<Personsport>() {
            @Override
            public void done(List<Personsport> list, BmobException e) {
                if (e == null) {
                    personsports.clear();
                    for(int i=0;i<list.size();i++) {
                        if(!list.get(i).getUserfrom().equals(( ((App) getApplication()).model_User.getUsername()))) {
                            personsports .add(list.get(i));
                        }
                    }
                    encounterAdapter.addorder(personsports);
                    encounterAdapter.notifyDataSetChanged();
                } else {
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sign==3) {
            char_ada.clearOrders();
            get_chat("chat_userA");
            get_chat("chat_userB");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void inti_view() {
        char_list = (ListView) findViewById(R.id.char_list);
        encounter_bty = (Button) findViewById(R.id.encounter_bty);
        char_bty = (Button) findViewById(R.id.char_bty);
        encounter_bty.setOnClickListener(this);
        char_bty.setOnClickListener(this);
        char_ada = new CharAdapter(this, participats, ((App) getApplication()).model_User.getUsername());
        encounterAdapter = new EncounterAdapter(this, personsports, ((App) getApplication()).model_User.getUsername());
        char_list.setAdapter(encounterAdapter);
        char_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (sign != 1) {
                    if (char_ada.getOrders().get(i).isChat_OK())
                    {
                        sign = 3;
                    }
                   else if (!char_ada.getOrders().get(i).isChat_OK() && char_ada.getOrders().get(i).getChat_userA().equals(((App) getApplication()).model_User.getUsername())) {
                        Toast.makeText(ChatActivity.this, "请等人家回复", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (char_ada.getOrders().get(i).getChat_userB().equals(((App) getApplication()).model_User.getUsername())) {
                        sign = 2;
                    } else {
                        sign = 3;
                    }
                    ((App) getApplication()).chateachother = char_ada.getOrders().get(i);
                } else
                    ((App) getApplication()).personsport = personsports.get(i);
                Intent intent = new Intent(ChatActivity.this, PersonCharActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("sign", sign);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.char_bty:
                chat();
                break;
            case R.id.encounter_bty:
                encouter();
                break;
        }
    }

    private void encouter() {
        char_bty.setBackgroundResource(R.mipmap.sign3);
        encounter_bty.setBackgroundResource(R.mipmap.sign4);
        char_list.setAdapter(encounterAdapter);
        sign = 1;
        get_data();
        is_chat = false;
        char_bty.setEnabled(true);
        encounter_bty.setEnabled(false);
    }

    private void chat() {
        char_bty.setBackgroundResource(R.mipmap.sign4);
        encounter_bty.setBackgroundResource(R.mipmap.sign3);
        char_ada.clearOrders();
        char_list.setAdapter(char_ada);
        get_chat("chat_userA");
        get_chat("chat_userB");
        sign = 3;
        is_chat = true;
        char_bty.setEnabled(false);
        encounter_bty.setEnabled(true);
    }

    private void get_chat(String key) {
        BmobQuery<Chateachother> query = new BmobQuery<Chateachother>();
        query.addWhereEqualTo(key, ((App) getApplication()).model_User.getUsername());
        List<BmobQuery<Chateachother>> queries = new ArrayList<BmobQuery<Chateachother>>();
        queries.add(query);
        BmobQuery<Chateachother> mainQuery = new BmobQuery<Chateachother>();
        mainQuery.and(queries);
        mainQuery.findObjects(new FindListener<Chateachother>() {
            @Override
            public void done(List<Chateachother> list, BmobException e) {
                participats = list;
                char_ada.addorder(participats);
                char_ada.notifyDataSetChanged();
            }
        });
    }
}
