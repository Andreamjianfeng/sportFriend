package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Conversion;
import com.guet.jason.sportsfriend.MyView.ImageLoader;
import com.guet.jason.sportsfriend.MyView.LoadingProgressDialog;
import com.guet.jason.sportsfriend.MyView.Participat;
import com.guet.jason.sportsfriend.MyView.Personsport;
import com.guet.jason.sportsfriend.Xiu.SquareTransform;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.ninegrid.ImageInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ReleaseActivity extends AppCompatActivity implements  ViewPager.OnPageChangeListener {
    private ImageView add_icon, safe_icon;
    private EditText add_time, add_address, add_tymp, add_num, add_content, yi_bao_ming, ke_bao_ming;
    private Button relerse_ok;
    private TextView dai_tv;
    private String release;
    private List<Personsport> list;
    private ArrayList<ImageItem> imageItems = new ArrayList<>();
    private LinearLayout bao_or_fa, dailer_dot_point;
    private ViewPager viewPager;
    //定义一个集合用来存放视图的View对象
    private List<View> listOfView = new ArrayList<>();
    private Myadapter myadapter;
    private int size = 0, sign = 0;
    private LoadingProgressDialog dialog;
    private Participat participat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "60b5cef4dc8267246e1cbbc464387755");
        setContentView(R.layout.activity_dailer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.relerse_toolbar);
        toolbar.setTitle("活动详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inti_view();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        add_icon = (ImageView) findViewById(R.id.add_icon_img);
        safe_icon = (ImageView) findViewById(R.id.safe_icon);
        add_address = (EditText) findViewById(R.id.add_address);
        add_content = (EditText) findViewById(R.id.add_content);
        add_time = (EditText) findViewById(R.id.add_time);
        add_tymp = (EditText) findViewById(R.id.add_tymp);
        add_num = (EditText) findViewById(R.id.add_num);
        yi_bao_ming = (EditText) findViewById(R.id.yi_bao_ming);
        ke_bao_ming = (EditText) findViewById(R.id.ke_bao_ming);
        relerse_ok = (Button) findViewById(R.id.relerse_ok);
        add_icon = (ImageView) findViewById(R.id.add_icon_img);
        dai_tv = (TextView) findViewById(R.id.text_dai);
        bao_or_fa = (LinearLayout) findViewById(R.id.bao_or_fa);
        viewPager = (ViewPager) findViewById(R.id.dailer_vp);
        dailer_dot_point = (LinearLayout) findViewById(R.id.dailer_dot_point);

        dialog = new LoadingProgressDialog(ReleaseActivity.this, "正在发表中...", R.drawable.frame);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        release = data.getString("release");
        sign = data.getInt("sign");
        Log.e("size", String.valueOf(sign));
        if (sign == 1 || sign == 2) {
            inti_data();
            if (sign == 2)
                relerse_ok.setText("取消活动");
            else
                relerse_ok.setText("加入活动");
        } else if (sign == 3) {
            relerse_ok.setText("退出活动");
            inti_back();
        } else {
            bao_or_fa.setVisibility(View.GONE);
        }
        add_icon.setOnClickListener(new View.OnClickListener() {//添加图片
            @Override
            public void onClick(View view) {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new ImageLoader());
                imagePicker.setMultiMode(true);   //多选
                imagePicker.setShowCamera(true);  //显示拍照按钮
                imagePicker.setSelectLimit(6);    //最多选择X张
                imagePicker.setCrop(false);       //不进行裁剪
                Intent intent = new Intent(ReleaseActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        safe_icon = (ImageView) findViewById(R.id.safe_icon);
        relerse_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (sign) {
                    case 1:
                        join();
                        break;
                    case 2:
                        ce_release();
                        break;
                    case 3:
                        ce_participation();
                        break;
                    case 4:
                        issue();
                        break;
                }
            }
        });
    }
    private void ce_release(){
        Personsport p2 =new Personsport();
        p2.setObjectId(list.get(0).getObjectId());
        p2.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    finish();
                    showmsg("删除成功");
                }
            }
        });
    }
    private void ce_participation(){
        Participat p2 =new Participat();
        p2.setObjectId(participat.getObjectId());
        p2.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    finish();
                    showmsg("退出成功");
                }
            }
        });
    }
    private void inti_back() {
        participat = ((App) getApplication()).participat;
        size = participat.getIcons().size();
        dai_tv.setVisibility(View.GONE);
        add_icon.setVisibility(View.GONE);
        initPoints();
        //  Picasso.with(this).load(list.get(0).user_icon.getFileUrl()).transform(new SquareTransform()).into(safe_icon);
        add_time.setText(participat.getJoin_start_time());
        add_num.setText(String.valueOf(participat.getJoin_mun()));
        add_tymp.setText(participat.getJoin_type());
        add_content.setText(participat.getJoin_content());
        add_address.setText(participat.getJion_address());
        listOfView.clear();
        for (int i = 0; i < size; i++) {
            //创建ImageView存放图片
            ImageView iv = new ImageView(getApplicationContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(this).load(participat.getIcons().get(i)).into(iv);
            //把图片添加到集合zhong
            listOfView.add(iv);
        }
        myadapter = new Myadapter();
        viewPager.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(this);
        BmobQuery<Participat> query = new BmobQuery<Participat>();
        query.addWhereEqualTo("add_date", participat.getAdd_date());
        query.findObjects(new FindListener<Participat>() {
            @Override
            public void done(List<Participat> list, BmobException e) {
                if (e == null) {
                    String mun = String.valueOf(list.size());
                    yi_bao_ming.setText(mun);
                    String temp = String.valueOf(((App) getApplication()).list.get(0).getMun() - list.size());
                    ke_bao_ming.setText(temp);
                }
            }
        });
        add_address.setEnabled(false);
        add_num.setEnabled(false);
        add_tymp.setEnabled(false);
        add_content.setEnabled(false);
        add_time.setEnabled(false);
        yi_bao_ming.setEnabled(false);
        ke_bao_ming.setEnabled(false);
    }

    private void inti_data() {
        dai_tv.setVisibility(View.GONE);
        add_icon.setVisibility(View.GONE);
        list = ((App) getApplication()).list;
        size = list.get(0).getIcons().size();
        initPoints();
        //  Picasso.with(this).load(list.get(0).user_icon.getFileUrl()).transform(new SquareTransform()).into(safe_icon);
        add_time.setText(list.get(0).start_time);
        add_num.setText(String.valueOf(list.get(0).mun));
        add_tymp.setText(list.get(0).type);
        add_content.setText(list.get(0).content);
        add_address.setText(list.get(0).address);
        listOfView.clear();
        for (int i = 0; i < size; i++) {
            //创建ImageView存放图片
            ImageView iv = new ImageView(getApplicationContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(this).load(list.get(0).getIcons().get(i)).into(iv);
            //把图片添加到集合zhong
            listOfView.add(iv);
        }
        myadapter = new Myadapter();
        viewPager.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(this);
        BmobQuery<Participat> query = new BmobQuery<Participat>();
        query.addWhereEqualTo("add_date", list.get(0).getCreatedAt());
        query.findObjects(new FindListener<Participat>() {
            @Override
            public void done(List<Participat> list, BmobException e) {
                if (e == null) {
                    String mun = String.valueOf(list.size());
                    yi_bao_ming.setText(mun);
                    String temp = String.valueOf(((App) getApplication()).list.get(0).getMun() - list.size());
                    ke_bao_ming.setText(temp);
                }
            }
        });
        add_address.setEnabled(false);
        add_num.setEnabled(false);
        add_tymp.setEnabled(false);
        add_content.setEnabled(false);
        add_time.setEnabled(false);
        yi_bao_ming.setEnabled(false);
        ke_bao_ming.setEnabled(false);
    }

    private void join() {
        Participat paticiat = new Participat();
        paticiat.setJion_address(list.get(0).address);
        paticiat.setJoin_content(list.get(0).content);
        paticiat.setJoin_mun(list.get(0).mun);
        paticiat.setJoin_start_time(list.get(0).start_time);
        paticiat.setJoin_type(list.get(0).type);
        paticiat.setJoin_userfromA(((App) getApplication()).model_User.getUsername());
        paticiat.setJoin_userfromB(list.get(0).userfrom);
        paticiat.setJoin_type(list.get(0).type);
        paticiat.setJoin_end_time(null);
        paticiat.setAdd_date(list.get(0).getCreatedAt());
        paticiat.setIcons(list.get(0).getIcons());
        paticiat.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    finish();
                    showmsg("成功");
                } else {
                    showmsg("失败" + e.toString());
                }
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
                size = imageItems.size();
                listOfView.clear();
                initPoints();
                for (int i = 0; i < size; i++) {
                    //创建ImageView存放图片
                    ImageView iv = new ImageView(getApplicationContext());
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    Bitmap bitmap = BitmapFactory.decodeFile(imageItems.get(i).path);
                    iv.setImageBitmap(bitmap);
                    //把图片添加到集合zhong
                    listOfView.add(iv);
                }
                myadapter = new Myadapter();
                viewPager.setAdapter(myadapter);
                myadapter.notifyDataSetChanged();
                viewPager.addOnPageChangeListener(this);
                //设置图片开始的位置，从中间开始，并且是从八张图片中的第一张开始
                //viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageItems.size()));
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initPoints() {
        //实例化线性布局
        //绘制和图片对应的圆点的数量
        for (int i = 0; i < size; i++) {
            View view = new View(this);
            //设置圆点的大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            //设置间距
            params.setMargins(10, 10, 10, 10);
            //设置图片的自定义效果
            view.setBackgroundResource(R.drawable.stars_select);
            //把设置好的视图属性设置到View中
            view.setLayoutParams(params);
            //把创建好的View添加到线性布局中
            dailer_dot_point.addView(view);
        }
        //设置选中线性布局中的第一个
        dailer_dot_point.getChildAt(0).setSelected(true);
    }

    private void issue() //上传
    {
        String num = add_num.getText().toString();
        final Personsport personsport = new Personsport();
        if (add_num.length() < 1) {
            showmsg("必须填写人数");
            return;
        } else {
            personsport.setType(add_tymp.getText().toString());
            personsport.setStart_time(add_time.getText().toString());
            personsport.setContent(add_content.getText().toString());
            personsport.setMun(Integer.parseInt(add_num.getText().toString()));
            personsport.setAddress(add_address.getText().toString());
            personsport.setUserfrom(((App) getApplication()).model_User.getUsername());
            personsport.setEnd_time(null);
            personsport.setLatitude(((App) getApplication()).User_Latitude);
            personsport.setLongitude(((App) getApplication()).User_Longitude);
            BmobGeoPoint point = new BmobGeoPoint(((App) getApplication()).User_Longitude, ((App) getApplication()).User_Latitude);
            personsport.setGpsAdd(point);
            BmobFile tempbmobfile = BmobFile.createEmptyFile();
            BmobFile tempbmobfile2 = BmobFile.createEmptyFile();
            tempbmobfile2.setUrl(((App) getApplication()).model_User.getIcon().getFileUrl());
            personsport.setUser_icon(tempbmobfile);
            personsport.setUser(tempbmobfile2);
            if (imageItems.size() == 0) {
                String ul = ((App) getApplication()).model_User.getIcon().getFileUrl();
                List<String> list2 = null;
                list2.add(ul);
                personsport.setIcons(list2);
                personsport.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            finish();
                            //  showmsg("成功");
                        } else {
                            showmsg("失败" + e.toString());
                        }
                    }
                });
            } else {
                dialog.show();
                final String[] filePaths = new String[imageItems.size()];
                for (int i = 0; i < imageItems.size(); i++) {
                    filePaths[i] = imageItems.get(i).path;
                }
                BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        if (list1.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                            personsport.setIcons(list1);
                            personsport.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        showmsg("OK");
                                        finish();
                                    }
                                    dialog.cancel();
                                }
                            });
                        }
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {
                    }

                    @Override
                    public void onError(int i, String s) {
                        dialog.cancel();
                        finish();
                        showmsg("上次失败");
                    }
                });

            }
        }
    }

    private void showmsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private int index = 0;

    @Override
    public void onPageSelected(int position) {
        dailer_dot_point.getChildAt(index).setSelected(false);
        //选中下一个显示的视图
        dailer_dot_point.getChildAt(position % size).setSelected(true);
        index = position % size;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            if (!handler.hasMessages(1)) {
                startGuide();
            }
        } else {
            stopGuide();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            sendEmptyMessageDelayed(1, 2000);//延长两秒后给自己发消息，没有被移除前都是会一直循环的
        }
    };


    /**
     * 开始轮播
     */
    private void startGuide() {
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    /**
     * 停止轮播
     */
    private void stopGuide() {
        //把消息移除，轮播页面就会停止
        handler.removeMessages(1);
    }

    private class Myadapter extends PagerAdapter {

        //可滑动的页面的数量
        @Override
        public int getCount() {
            //return listOfView.size();//实现只能滑动一遍图片的效果
            return listOfView.size();//实现无限滑动的效果
        }

        //判断两页的地址是否相同
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //这是google要求的写法
            return view == object;
        }


        //重写构造方法，当创建实例时，把数据添加到集合中
        public Myadapter() {

        }


        //下面的两个方法也是必须重写的

        //这里要返回的是View的视图对象
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //添加一个item 显示
            //Toast.makeText(getBaseContext(), position + "页", Toast.LENGTH_SHORT).show();
            container.addView(listOfView.get(position % size));
            return listOfView.get(position % size);
        }


        //销毁一个View的对象
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(listOfView.get(position % size));
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();

        }
    }
}
