package com.guet.jason.sportsfriend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Participat;
import com.guet.jason.sportsfriend.R;
import com.guet.jason.sportsfriend.Xiu.SquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 此座位已有人 on 2017/3/21.
 */

public class ParticipatsAdapter extends BaseAdapter {
    private List<Participat> orders;
    private Context context;



    public   ParticipatsAdapter(Context context,List<Participat> orders){
        this.orders=orders;
        this.context=context;
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
    public void addorder( List<Participat> orders){
        this.orders=orders;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParticipatsAdapter.ViewHolder holder=null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.use_list, null);
            holder = new ParticipatsAdapter.ViewHolder();
            holder.textViewTitle=(TextView)convertView.findViewById(R.id.list_tv);
            holder.text_mun=(TextView)convertView.findViewById(R.id.list_mun);
            holder.text_tymp=(TextView)convertView.findViewById(R.id.list_address);
            holder.start_time=(TextView)convertView.findViewById(R.id.list_start_time);
            holder.end_time=(TextView)convertView.findViewById(R.id.list_end_time);
            holder.details=(TextView)convertView.findViewById(R.id.con_tv);
            holder.imageView=(ImageView)convertView.findViewById(R.id.list_img);
            convertView.setTag(holder);
        } else {
            holder = (ParticipatsAdapter.ViewHolder)convertView.getTag();
        }
        //User.getUserIconByName(orders.get(position).userfrom)
        Picasso.with(context).load(orders.get(position).getIcons().get(0)).transform(new SquareTransform()).into(holder.imageView);
        holder.textViewTitle.setText("类别："+orders.get(position).join_type);
        holder.text_tymp.setText("地址："+orders.get(position).jion_address);
        holder.text_mun.setText("人数："+orders.get(position).join_mun);
        String start_time="时间："+orders.get(position).join_start_time;
        holder.start_time.setText(start_time);
        holder.end_time.setText(orders.get(position).join_end_time);
        holder.details.setText(orders.get(position).join_content);
        return convertView;
    }


    class ViewHolder{
        private TextView textViewTitle;
        private TextView text_tymp;
        private TextView text_mun;
        private TextView start_time;
        private TextView end_time;
        private ImageView imageView;
        private TextView details;

    }
}
