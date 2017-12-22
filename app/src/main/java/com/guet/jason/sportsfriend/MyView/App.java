package com.guet.jason.sportsfriend.MyView;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.guet.jason.sportsfriend.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.ninegrid.NineGridView;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/3/1.
 * App主activity
 */
public class App extends Application {
    public Model_User model_User;
    public double User_Latitude;
    public double User_Longitude;
    public boolean isSign;
    public List<Personsport> list=new ArrayList<>();
    public  Chateachother chateachother;
    public  Personsport personsport;
    public Participat participat;
    public static Typeface TEXT_TYPE ;
    public static String location_address;
    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .placeholder(R.drawable.jiazaihuancun)//
                    .error(R.drawable.ic_default_image)//
                    .into(imageView);
        }
        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        isSign = false;
        NineGridView.setImageLoader(new GlideImageLoader());
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
        inti_text();
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    /**
     * 初始化字体
     */
  private void inti_text(){
      try{
          TEXT_TYPE = Typeface.createFromAsset(getAssets(),"fonts/new_text.ttf");
      }catch(Exception e){
          Log.i("MyApp","加载第三方字体失败。") ;
          TEXT_TYPE = null ;
      }
  }
}
