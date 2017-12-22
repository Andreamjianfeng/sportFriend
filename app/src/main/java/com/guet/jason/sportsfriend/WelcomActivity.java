package com.guet.jason.sportsfriend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.Model_User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class WelcomActivity extends AppCompatActivity {
    private Boolean mAuthTask = false;
    private ViewPager vp;
    private EditText username;
    private EditText password;
    private Button signButton;
    private Button signUpButton;
    private List<Fragment> fragments;
    private ProgressBar welcomeProgressBar;

    private void assignViews() {
        vp = (ViewPager) findViewById(R.id.vp);
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        signButton = (Button) findViewById(R.id.signBtn);
        signUpButton = (Button) findViewById(R.id.signupBtn);
        welcomeProgressBar= (ProgressBar) findViewById(R.id.welcomeProgressBar);
        signButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                attemptLogin();
           }
       });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "60b5cef4dc8267246e1cbbc464387755");
        //申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(WelcomActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(WelcomActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ActivityCompat.requestPermissions(WelcomActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }
        setContentView(R.layout.activity_welcom);
        //设置全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        assignViews();
        initData();
        initView();

    }

    /**
     * 初始化数据,添加三个Fragment
     */
    private void initData() {
        fragments = new ArrayList<>();
        Fragment fragment1 = new GuildFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 1);
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);
    }

    /**
     * 设置ViewPager的适配器和滑动监听
     */
    private void initView() {
        vp.setOffscreenPageLimit(1); //原为3
        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * ViewPager适配器
     */
    private class MyPageAdapter extends FragmentPagerAdapter {


        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * ViewPager滑动页面监听器
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 根据页面不同动态改变红点和在最后一页显示立即体验按钮
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void attemptLogin() {
        if (mAuthTask == true) {
            return;
        }

        // Reset errors.
        username.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String musername = username.getText().toString();
        String mpassword = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(mpassword) && !isPasswordValid(mpassword)) {//是否为空
            username.setError("输入有误");
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(musername)) {
            username.setError("输入有误");
            focusView = username;
            cancel = true;
        } else if (!isEmailValid(musername)) {
            username.setError("输入有误");
            focusView = username;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            welcomeProgressBar.setVisibility(View.VISIBLE);
            mAuthTask = true;
            Model_User bu2 = new Model_User();
            bu2.setUsername(musername);
            bu2.setPassword(mpassword);
       
            bu2.login(new SaveListener<Model_User>() {
                @Override
                public void done(Model_User model_user, BmobException e) {
                    if (e == null) {
                        mAuthTask = false;
                        showmsg("登陆成功");
                        welcomeProgressBar.setVisibility(View.GONE);
                        ((App) getApplication()).model_User = model_user;
                        ((App) getApplication()).isSign = true;
                        WelcomActivity.this.finish();
                        Intent intent = new Intent(WelcomActivity.this, FirstActivity.class);
                        startActivity(intent);
                    } else {
                        mAuthTask = false;
                        welcomeProgressBar.setVisibility(View.GONE);
                        showmsg("登陆失败" + e.toString());
                    }
                }
            });

        }
    }

    private void attemptRegister() {
        if (mAuthTask == true) {
            return;
        }

        // Reset errors.
        username.setError(null);
        password.setError(null);
        String email = username.getText().toString();
        String mpassword = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(mpassword) && !isPasswordValid(mpassword)) {
            password.setError("输入有误");
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            username.setError("输入有误");
            focusView = username;
            cancel = true;
        } else if (!isEmailValid(email)) {
            username.setError("输入有误");
            focusView = username;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {
            welcomeProgressBar.setVisibility(View.VISIBLE);
            mAuthTask = true;
            Model_User bu2 = new Model_User();
            bu2.setUsername(email);
            bu2.setPassword(mpassword);
            bu2.setNickname(email);
            BmobFile tempbmobfile = BmobFile.createEmptyFile();
            tempbmobfile.setUrl("http://file.bmob.cn/M03/28/FE/oYYBAFcNtReAc4WkAACceyr8Oc8966.png");
            bu2.setIcon(tempbmobfile);
            bu2.signUp(new SaveListener<Model_User>() {
                @Override
                public void done(Model_User model_user, BmobException e) {
                    if (e == null) {
                        mAuthTask = false;
                        showmsg("注册成功");
                        welcomeProgressBar.setVisibility(View.GONE);
                    } else {
                        mAuthTask = false;
                        showmsg("注册失败" + e.toString());
                        welcomeProgressBar.setVisibility(View.GONE);

                    }
                }
            });


        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 2;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    private void showmsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
