package com.guet.jason.sportsfriend.Xiu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    private TopView mTopView;
    public CustomScrollView(Context context, TopView topView) {
        super(context);
        this.mTopView = topView;
        setVerticalScrollBarEnabled(false);//禁止竖直滑动条出现
    }
    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CustomScrollView(Context context) {
        super(context);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mTopView.onScroll(t);
    }
}