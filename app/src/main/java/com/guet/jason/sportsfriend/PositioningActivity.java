package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.App;

public class PositioningActivity extends AppCompatActivity {
    private TextView positioning_my_address;
    private EditText positioning_address;
    private Button start_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positioning);
        Toolbar toolbar = findViewById(R.id.positioning_toolbar);
        toolbar.setTitle("autel");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result();
            }
        });
        positioning_my_address = findViewById(R.id.positioning_my_address);
        start_position = findViewById(R.id.start_position);
        positioning_address = findViewById(R.id.positioning_address);
        positioning_my_address.setText(App.location_address);
        start_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result();
            }
        });
    }

    /**
     * 返回函数
     */
    private void result() {
        String data = positioning_address.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("data", data);
        PositioningActivity.this.setResult(0, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //TODO something\
            result();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
