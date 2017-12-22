package com.guet.jason.sportsfriend.MyView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.R;


///**
// * Created by Administrator on 2016/11/16.
// */
public class LoadingProgressDialog extends ProgressDialog {

    private AnimationDrawable mAnimation;
    private Context mContext;
    private ImageView mImageView;
    private String mLoadingTitle;
    private TextView mLoadingTv;
    private int mResid;

    public LoadingProgressDialog(Context context, String content, int id) {
        super(context);
        this.mContext = context;
        this.mLoadingTitle = content;
        this.mResid = id;
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {

        mImageView.setBackgroundResource(mResid);
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        mAnimation.setExitFadeDuration(1000);
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        mLoadingTv.setText(mLoadingTitle);

    }

    public void setContent(String str) {
        mLoadingTv.setText(str);
    }

    private void initView() {
        setContentView(R.layout.progress_dialog);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
    }
}
