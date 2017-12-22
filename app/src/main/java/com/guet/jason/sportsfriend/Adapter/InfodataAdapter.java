package com.guet.jason.sportsfriend.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.Chateachother;
import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Participat;
import com.guet.jason.sportsfriend.R;
import com.guet.jason.sportsfriend.Xiu.Infolistdata;
import com.guet.jason.sportsfriend.Xiu.MyTextView;
import com.guet.jason.sportsfriend.Xiu.TModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class InfodataAdapter extends BaseAdapter {
    private List<Infolistdata> orders;
    private Context context;

    public InfodataAdapter(Context context, List<Infolistdata> orders) {
        this.orders = orders;
        this.context = context;
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addorder(List<Infolistdata> orders) {
        this.orders = orders;
    }


    public List<Infolistdata> getOrders() {
        return orders;
    }

    public void clearOrders() {
        orders.clear();
    }

    @SuppressLint("InlinedApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = new MyTextView(context);
        }
        final TModel tmodel = orders.get(position).getInfo_text();
        ((MyTextView) convertView).setText(orders.get(position).getInfo_text().text);
        ((MyTextView) convertView).settime(orders.get(position).getInfo_time());
        ((MyTextView) convertView).setname(orders.get(position).getInfo_name());
        ((MyTextView) convertView).setadress(orders.get(position).getInfo_adress());
        ((MyTextView) convertView).setimg(orders.get(position).getIcon_url());
        ((MyTextView) convertView)
                .setOnStateChangeListener(new MyTextView.OnStateChangeListener() {

                    @Override
                    public void onStateChange(boolean isExpand) {
                        tmodel.isExpand = isExpand;
                    }
                });
        ((MyTextView) convertView).setIsExpand(tmodel.isExpand);
        return convertView;
    }

}