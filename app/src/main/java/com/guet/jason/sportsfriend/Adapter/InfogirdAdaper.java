package com.guet.jason.sportsfriend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.guet.jason.sportsfriend.Published_aboutActivity;
import com.guet.jason.sportsfriend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴剑锋 on 2017/4/19.
 */

public class InfogirdAdaper extends BaseAdapter {
    private int[]  imglist;
    private Context context;

    public InfogirdAdaper(int[] imglist, Context contxt) {
        this.imglist = imglist;
        this.context = contxt;
    }

    @Override
    public int getCount() {
        return imglist == null ? 0 : imglist.length;
    }

    @Override
    public Object getItem(int position) {
        return imglist[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHoler holer = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.info_gird_layout, null);
            holer = new InfogirdAdaper.viewHoler();
            holer.imageView = (ImageView) convertView.findViewById(R.id.info_gird_img);
            convertView.setTag(holer);
        } else
            holer = (viewHoler) convertView.getTag();
        holer.imageView.setBackgroundResource(imglist[position]);
        return convertView;
    }

    class viewHoler {
        private ImageView imageView;
    }
}
