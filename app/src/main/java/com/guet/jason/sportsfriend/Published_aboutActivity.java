package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.CircleDb;
import com.guet.jason.sportsfriend.MyView.ImageLoader;
import com.guet.jason.sportsfriend.MyView.LoadView;
import com.guet.jason.sportsfriend.MyView.Tool;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.ninegrid.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class Published_aboutActivity extends AppCompatActivity {
    private EditText my_want;
    private ArrayList<ImageItem> imageItems;
    private GridAdapter gridAdapter;
    private TextView upload_some_img,textLoadView;
    private LoadView loadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published_about);
        Bmob.initialize(this, "60b5cef4dc8267246e1cbbc464387755");
        Toolbar toolbar = findViewById(R.id.published_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        my_want = findViewById(R.id.my_want);
        loadView=findViewById(R.id.loadView);
        textLoadView=findViewById(R.id.textLoadView);
        upload_some_img = findViewById(R.id.upload_some_img);
        TextView location_address = findViewById(R.id.location_address);
        location_address.setText(App.location_address);
        upload_some_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unload();
            }
        });
        GridView gridView = findViewById(R.id.my_grid);
        gridAdapter = new GridAdapter();
        gridView.setAdapter(gridAdapter);
    }


    private void unload() {
        upload_some_img.setEnabled(false);
        final CircleDb circleDb = new CircleDb();
        final String my_say = my_want.getText().toString();
        if (imageItems != null) {
            loadView.setVisibility(View.VISIBLE);
            textLoadView.setVisibility(View.VISIBLE);
            final String[] filePaths = new String[imageItems.size()];
            for (int i = 0; i < imageItems.size(); i++) {
                filePaths[i] = imageItems.get(i).path;
            }
            BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    if (list1.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成

                        circleDb.setUrls(list1);
                        if (my_say.length() != 0)
                            circleDb.setMy_say(my_say);
                        circleDb.setAuthor(((App) getApplication()).model_User);
                        circleDb.save(new SaveListener<String>() {

                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    loadView.setVisibility(View.GONE);
                                    textLoadView.setVisibility(View.GONE);
                                }
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {
                    loadView.setProgress(i3);
                }

                @Override
                public void onError(int i, String s) {
                    loadView.setVisibility(View.GONE);
                    textLoadView.setVisibility(View.GONE);
                    Tool.showData(Published_aboutActivity.this,"上传失败");
                    finish();
                }
            });
        }
        if (my_say.length() != 0 && imageItems == null) {
            circleDb.setMy_say(my_say);
            circleDb.setAuthor(((App) getApplication()).model_User);
            circleDb.save(new SaveListener<String>() {

                @Override
                public void done(String s, BmobException e) {
                    if (e == null)
                        finish();
                }
            });
        }
        if (imageItems == null && my_say.length() == 0)
            upload_some_img.setEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                gridAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GridAdapter extends BaseAdapter {
        public GridAdapter() {
        }

        @Override
        public int getCount() {
            if (imageItems == null)
                return 1;
            else
                return imageItems.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return imageItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            GridAdapter.ViewHolder holder = null;
            if (view == null) {
                holder = new GridAdapter.ViewHolder();
                view = LayoutInflater.from(Published_aboutActivity.this).inflate(R.layout.grid_layout, null);
                holder.image_voice = (ImageView) view.findViewById(R.id.gird_img);
                view.setTag(holder);
            } else {
                holder = (GridAdapter.ViewHolder) view.getTag();
            }
            if (imageItems == null) {
                holder.image_voice.setImageResource(R.drawable.add_icon);
            } else {
                if (i == imageItems.size()) {
                    holder.image_voice.setImageResource(R.drawable.add_icon);
                } else {
                    File file = new File(imageItems.get(i).path);
                    if (file.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(imageItems.get(i).path);
                        int width = bm.getWidth();
                        int height = bm.getHeight();
                        // 设置想要的大小
                        int newWidth = 100;
                        int newHeight = 100;
                        // 计算缩放比例
                        float scaleWidth = ((float) newWidth) / width;
                        float scaleHeight = ((float) newHeight) / height;
                        Matrix matrix = new Matrix();
                        matrix.postScale(scaleWidth, scaleHeight);
                        Bitmap mbitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
                        holder.image_voice.setImageBitmap(mbitmap);
                        bm.recycle();
                    }
                }
            }
            holder.image_voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((imageItems != null && i == imageItems.size()) || imageItems == null) {
                        ImagePicker imagePicker = ImagePicker.getInstance();
                        imagePicker.setImageLoader(new ImageLoader());
                        imagePicker.setMultiMode(true);   //多选
                        imagePicker.setShowCamera(true);  //显示拍照按钮
                        imagePicker.setSelectLimit(6);    //最多选择X张
                        imagePicker.setCrop(false);       //不进行裁剪
                        Intent intent = new Intent(Published_aboutActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent, 100);
                    }
                }
            });
            return view;
        }

        class ViewHolder {
            private ImageView image_voice;
        }
    }
}
