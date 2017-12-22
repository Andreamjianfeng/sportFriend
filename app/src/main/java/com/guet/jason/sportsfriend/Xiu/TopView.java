package com.guet.jason.sportsfriend.Xiu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class TopView extends FrameLayout {
    private ViewGroup mContentView;
    private ViewGroup mTopView;
    private int  mTopViewTop;
    private View mTopContent;
    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public TopView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public TopView(Context context) {
        this(context,null);
    }
    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                //获取它的根目录 view
                mContentView = (ViewGroup) getChildAt(0);//就是LinearLayout
                removeAllViews();//这是移除所有的view对象
                CustomScrollView scrollView = new CustomScrollView(getContext(), TopView.this);//创建ScrollView
                scrollView.addView(mContentView);//把移除的view 添加到scrollview中
                addView(scrollView);//把scrollview添加到自定义的topview中因为需要滑动
            }
        });
    }
    public void setTopView(final int id) {
        post(new Runnable() {
            @Override
            public void run() {
                mTopView = (ViewGroup) mContentView.findViewById(id);//悬挂的搜索布局  就是一个FrameLayout
                int height = mTopView.getChildAt(0).getMeasuredHeight();//LienarLayout 高度
                ViewGroup.LayoutParams params = mTopView.getLayoutParams();
                params.height = height;
                mTopView.setLayoutParams(params);//设置搜索的高度
                mTopViewTop = mTopView.getTop();//离上面的距离
                mTopContent = mTopView.getChildAt(0);//mTopContent  其实就是LinearLayout
            }
        });
    }
    public void onScroll(final int scrollY) {
        post(new Runnable() {
            @Override
            public void run() {
                if (mTopView == null) return;
                if (scrollY >= mTopViewTop&& mTopContent.getParent() == mTopView) {
                    mTopView.removeView(mTopContent);
                    addView(mTopContent);
                } else if (scrollY < mTopViewTop&& mTopContent.getParent() == TopView.this) {
                    removeView(mTopContent);//从布局中移除这个mTopContent
                    mTopView.addView(mTopContent);//把 这个mTopContent添加到搜索布局中
                }
            }
        });
    }
}