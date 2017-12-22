package com.guet.jason.sportsfriend;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.guet.jason.sportsfriend.MyView.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class AboutMeActivity extends AppCompatActivity {
    private X5WebView webView;
    private boolean isHidden=true;
    private View hiddenAboutView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView= (X5WebView) findViewById(R.id.aboutWeb);
        hiddenAboutView=findViewById(R.id.hiddenAboutView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setUseWideViewPort(true); //自适应屏幕
        webView.loadUrl("https://jianfengandream.kuaizhan.com/19/29/p454686399ffe1c");
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
                hiddenAboutView.setVisibility(View.GONE);

            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
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

}
