package com.guet.jason.sportsfriend;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebSettings;
import com.guet.jason.sportsfriend.MyView.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.lang.ref.SoftReference;
import java.util.Timer;
import java.util.TimerTask;

public class InformationActivity extends AppCompatActivity {

    private X5WebView webView;
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
    private View hiddenView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.info_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack(); // goBack()表示返回WebView的上一页面
                }
                else  if (!webView.canGoBack()){
                    finish();
                }
            }
        });
       findViewById(R.id.info_button).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
        webView = (X5WebView) findViewById(R.id.web_filechooser);
        hiddenView=findViewById(R.id.hiddenView);




        webView.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.i("test", "openFileChooser 1");
                InformationActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
                InformationActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.e("test", "openFileChooser 3");
                InformationActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                Log.e("test", "openFileChooser 4:" + filePathCallback.toString());
                InformationActivity.this.uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }

        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setUseWideViewPort(true); //自适应屏幕
        webView.loadUrl("https://jianfengandream.kuaizhan.com/");

        //webView加载监听
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                //加载完毕后进行JS交互
                webView.evaluateJavascript("document.getElementsByTagName('body')[0].getElementsByClassName('kz-float-layer bottom')[0].remove();", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("---TEST---",s);
                    }
                });
                hiddenView.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                hiddenView.setVisibility(View.VISIBLE);
            }
        });
        //webView加载百分比监听
        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if(i>=70){
                    webView.evaluateJavascript("document.getElementsByTagName('body')[0].getElementsByClassName('kz-float-layer bottom')[0].remove();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            Log.e("---TEST---",s);
                        }
                    });
                }
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        else  if ((keyCode == KeyEvent.KEYCODE_BACK) && !webView.canGoBack()){
            finish();
            return true;
        }
        return false;
    }
    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
    }
    /**
     * 确保注销配置能够被释放
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
/*        if (this.webView != null) {
            webView.destroy();
        }*/
        /*if(webView != null) {
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setVisibility(View.GONE);
           *//* long timeout = ViewConfiguration.getZoomControlsTimeout();//timeout ==3000
            Log.i("time==",timeout+"");*//*
            webView.destroy();
          *//*  new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    webView.destroy();
                }
            }, timeout);*//*
        }*/
        Handler handler = new MyHandler(this);
        handler.sendEmptyMessage(0x123);
        super.onDestroy();
    }
    class MyHandler extends Handler {

        SoftReference<Activity> softActivity;

        public MyHandler(Activity activity) {
            softActivity = new SoftReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (webView != null) {
                webView.getSettings().setBuiltInZoomControls(true);
                webView.setVisibility(View.GONE);
                webView.removeAllViews();
                webView.destroy();
                webView = null;
            }
        }
    }
}
