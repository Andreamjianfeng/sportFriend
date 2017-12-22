package com.guet.jason.sportsfriend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.CircleDb;
import com.guet.jason.sportsfriend.MyView.Model_User;
import com.guet.jason.sportsfriend.R;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CircleAdapter extends BaseAdapter {
    private List<CircleDb> orders;
    private Context context;
    private Model_User my_name;

    public CircleAdapter(Context context, List<CircleDb> orders, Model_User my_name) {
        this.orders = orders;
        this.context = context;
        this.my_name = my_name;
    }


    @Override
    public int getCount() {
        if (orders == null)
            return 0;
        else
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

    public void addorder(List<CircleDb> orders) {
        this.orders = orders;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CircleAdapter.ViewHolder holder;
        final CircleDb circleDb = orders.get(position);
        circleDb.setObjectId(circleDb.getObjectId());
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.circle_list, null);
            holder = new CircleAdapter.ViewHolder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.cir_list_name);
            holder.text_mun = (TextView) convertView.findViewById(R.id.cir_list_im);
            holder.text_say = (TextView) convertView.findViewById(R.id.cir_list_my_say);
            holder.imageView = (ImageView) convertView.findViewById(R.id.cir_list_img);
            holder.nineGrid = (NineGridView) convertView.findViewById(R.id.nineGrid);
            convertView.setTag(holder);
        } else {
            holder = (CircleAdapter.ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(orders.get(position).getAuthor().getIcon().getFileUrl()).into(holder.imageView);
        holder.text_mun.setText(orders.get(position).getAuthor().getIntroduce());
        holder.textViewTitle.setText(orders.get(position).getAuthor().getNickname());
        if (orders.get(position).getMy_say() == null)
            holder.text_say.setVisibility(View.GONE);
        else {
            holder.text_say.setVisibility(View.VISIBLE);
            holder.text_say.setText(orders.get(position).getMy_say());
        }
        if (orders.get(position).getUrls() != null) {
            holder.nineGrid.setVisibility(View.VISIBLE);
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int j = 0; j < orders.get(position).getUrls().size(); j++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(orders.get(position).getUrls().get(j));
                info.setBigImageUrl(orders.get(position).getUrls().get(j));
                imageInfo.add(info);
            }
            holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));
        } else {
            holder.nineGrid.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView textViewTitle;
        private TextView text_mun;
        private TextView text_say;
        private ImageView imageView;
        private NineGridView nineGrid;
    }
}
