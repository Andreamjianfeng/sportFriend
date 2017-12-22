package com.guet.jason.sportsfriend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Colundata;
import com.guet.jason.sportsfriend.R;
import com.guet.jason.sportsfriend.Xiu.SquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 吴剑锋 on 2017/4/20.
 * 专栏精选适配器
 */

public class columAdaper extends BaseAdapter {
    private List<Colundata> orders;
    private Context context;

    public columAdaper(Context context, List<Colundata> orders) {
        this.orders = orders;
        this.context = context;
    }
    @Override
    public int getCount() {
        return orders.size();
    }
    public void add(List<Colundata> orders){
        this.orders=orders;
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        columAdaper.ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.info_colum_layout, null);
            holder = new columAdaper.ViewHolder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.colum_tv_up);
            holder.text_mun = (TextView) convertView.findViewById(R.id.colum_tv_on);
            holder.imageView = (ImageView) convertView.findViewById(R.id.colum_img);
            convertView.setTag(holder);
        } else {
            holder = (columAdaper.ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(orders.get(position).getUri()).transform(new SquareTransform()).into(holder.imageView);
        holder.textViewTitle.setText(orders.get(position).getText_on());
        holder.text_mun.setText(orders.get(position).getText_up());
        return convertView;
    }
    class ViewHolder {
        private TextView textViewTitle;
        private TextView text_mun;
        private ImageView imageView;
    }
}
