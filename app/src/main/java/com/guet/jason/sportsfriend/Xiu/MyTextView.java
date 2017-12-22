package com.guet.jason.sportsfriend.Xiu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.R;
import com.squareup.picasso.Picasso;

public class MyTextView extends FrameLayout {

    private TextView contentView; // 内容文本
    private ImageView icon_img;
    private Context context;
    private TextView openView, adress, name, time; // “显示全文”或“收起”文本
    protected boolean isExpand = false; // 显示或收起标记
    private int defaultLine = 3; // 显示的行数,超过就隐藏

    public MyTextView(Context context) {
        super(context);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.infodata_list, this);
        contentView = (TextView) findViewById(R.id.content_text);
        openView = (TextView) findViewById(R.id.open_view);
        time = (TextView) findViewById(R.id.info_list_time);
        adress = (TextView) findViewById(R.id.info_list_adress);
        name = (TextView) findViewById(R.id.info_list_name);
        icon_img = (ImageView) findViewById(R.id.use_info_list_im);

        // 监听显示或收起按钮事件
        openView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                isExpand = !isExpand;
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(isExpand);
                }
                if (isExpand) {
                    contentView.setLines(contentView.getLineCount());
                    openView.setText("收起");
                } else {
                    contentView.setLines(defaultLine);
                    openView.setText("展开显示");
                }
            }
        });
    }

    public void settime(String time) {
        this.time.setText(time);
    }

    public void setname(String name) {
        this.name.setText(name);
    }

    public void setadress(String adr) {
        this.adress.setText(adr);
    }

    public void setimg(String url) {
        Picasso.with(context).load(url).transform(new CircleTransform()).into(icon_img);
    }

    // 给内容文本框赋值
    public void setText(String str) {
        contentView.setText(str);

        int count = contentView.getLayout() == null ? getLineNumber()
                : contentView.getLineCount();
        if (count > defaultLine) {
            contentView.setLines(defaultLine);
            openView.setVisibility(View.VISIBLE);
        } else {
            openView.setVisibility(View.GONE);
        }
    }

    // 计算内容文本框的占用的行数
    private int getLineNumber() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                MeasureSpec.AT_MOST);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        contentView.measure(widthMeasureSpec, heightMeasureSpec);
        int lineHeight = contentView.getLineHeight();
        int lineNumber = contentView.getMeasuredHeight() / lineHeight;
        return lineNumber;
    }

    // 改变状态的接口
    public interface OnStateChangeListener {
        void onStateChange(boolean isExpand);
    }

    public OnStateChangeListener onStateChangeListener;

    public void setOnStateChangeListener(
            OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    // 改变当前标记的值，并判断当前处于何种状态
    public void setIsExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (isExpand) {
            contentView.setLines(contentView.getLineCount());
            openView.setText("收起");
        } else {
            contentView.setLines(defaultLine);
            openView.setText("展开显示");
        }
    }
}
