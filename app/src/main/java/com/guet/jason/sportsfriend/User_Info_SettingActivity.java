package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Conversion;
import com.guet.jason.sportsfriend.MyView.ImageLoader;
import com.guet.jason.sportsfriend.MyView.Model_User;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.ninegrid.ImageInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class User_Info_SettingActivity extends AppCompatActivity {
    private LinearLayout lo_icon;
    private EditText set_my_name, set_my_introduce;
    private ImageView avatar;
    private Button save_setting;
    public static final int IMAGE_REQUEST_CODE =1 ;
    public static final int RESIZE_REQUEST_CODE =2 ;
    private String img_url;
    private ArrayList<ImageItem> imageItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__info__setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setTitle("个人设置");
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void init_view() {
        lo_icon = (LinearLayout) findViewById(R.id.lo_icon);
        set_my_name = (EditText) findViewById(R.id.set_my_name);
        set_my_introduce = (EditText) findViewById(R.id.set_my_introduce);
        save_setting= (Button) findViewById(R.id.save_setting);
        avatar = (ImageView) findViewById(R.id.avatar);
        if (((App) getApplication()).model_User.getNickname() == null)
            set_my_name.setText(((App) getApplication()).model_User.getUsername());
        else
            set_my_name.setText(((App) getApplication()).model_User.getNickname());
        if (((App) getApplication()).model_User.getIntroduce() != null)
            set_my_introduce.setText(((App) getApplication()).model_User.getIntroduce());
        Picasso.with(User_Info_SettingActivity.this).load(((App) getApplication()).model_User.getIcon().getFileUrl()).into(avatar);
        lo_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new ImageLoader());
                imagePicker.setMultiMode(false);   //多选
                imagePicker.setShowCamera(true);  //显示拍照按钮
                imagePicker.setSelectLimit(1);    //最多选择X张
                imagePicker.setCrop(true);       //不进行裁剪
                Intent intent = new Intent(User_Info_SettingActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        save_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(img_url);
                save_setting.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Bitmap bitmap= BitmapFactory.decodeFile(imageItems.get(0).path);
                avatar.setImageBitmap(bitmap);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        save_icon();
                    }
                }.start();
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 保存图片
     */
    private void save_icon() {

        final BmobFile bmobFile=new BmobFile(new File(imageItems.get(0).path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Picasso.with(User_Info_SettingActivity.this).load(bmobFile.getFileUrl()).into(avatar);
                    Model_User myUser =new Model_User();
                    BmobFile tempbmobfile = BmobFile.createEmptyFile();
                    tempbmobfile.setUrl(bmobFile.getUrl());
                    myUser.setIcon(tempbmobfile);
                    ((App) getApplication()).model_User.setIcon(tempbmobfile);
                    save_data(myUser);
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }
        });
    }
    /**
     * 保存用户修改的数据
     *
     * @param myUser
     */
    private void save_data(Model_User myUser) {
        myUser.update(((App) getApplication()).model_User.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                }
            }
        });
    }

    private void upload(String imgpath) {
        final Model_User model=new Model_User();
        if (imgpath == null) {
            model.setIntroduce(set_my_introduce.getText().toString());
            model.setNickname(set_my_name.getText().toString());
            BmobFile temp_file2 = BmobFile.createEmptyFile();
            temp_file2.setUrl(((App) getApplication()).model_User.getIcon().getFileUrl());
            model.setIcon(temp_file2);
            model.update(((App) getApplication()).model_User.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        ((App) getApplication()).model_User.setNickname(set_my_name.getText().toString());
                        ((App) getApplication()).model_User.setIntroduce(set_my_introduce.getText().toString());
                        finish();
                    }
                }
            });
        }
    }
}
