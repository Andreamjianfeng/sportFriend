package com.guet.jason.sportsfriend.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.Personsport;
import com.guet.jason.sportsfriend.R;
import com.guet.jason.sportsfriend.Xiu.SquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FangJie on 15/10/24.
 * 活动listiew的Adapter类
 */
public class OrderAdapter extends BaseAdapter {

    private List<Personsport> orders;
    private Context context;
    private String my_name;
    private Boolean is_Ok=true;


    public OrderAdapter(Context context, List<Personsport> orders, String my_name) {
        this.orders = orders;
        this.context = context;
        this.my_name = my_name;
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

    public void addorder(List<Personsport> orders,boolean is_Ok) {
        this.orders = orders;
        this.is_Ok=is_Ok;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.use_list, null);
            holder = new ViewHolder();
            holder.textViewTitle =  convertView.findViewById(R.id.list_tv);
            holder.text_mun =  convertView.findViewById(R.id.list_mun);
            holder.text_tymp =  convertView.findViewById(R.id.list_address);
            holder.start_time = convertView.findViewById(R.id.list_start_time);
            holder.end_time =  convertView.findViewById(R.id.list_end_time);
            holder.details =  convertView.findViewById(R.id.con_tv);
            holder.imageView =  convertView.findViewById(R.id.list_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(!is_Ok&&orders.get(position).getUserfrom().equals(my_name)){
            return convertView;
        }
        else {
            Picasso.with(context).load(orders.get(position).getIcons().get(0)).transform(new SquareTransform()).into(holder.imageView);
            holder.textViewTitle.setText("类别：" + orders.get(position).type);
            holder.text_tymp.setText("地址：" + orders.get(position).address);
            holder.text_mun.setText("人数：" + orders.get(position).mun);
            if (orders.get(position).getUserfrom().equals(my_name)) {
                holder.start_time.setText("发起时间" + orders.get(position).getUpdatedAt());
                holder.end_time.setText("开始时间：" + orders.get(position).start_time);
            } else {
                holder.start_time.setText("参与时间" + orders.get(position).start_time);
                holder.end_time.setText("");
            }
            holder.details.setText(orders.get(position).content);
            return convertView;
        }
    }


    private class ViewHolder {
        private TextView textViewTitle;
        private TextView text_tymp;
        private TextView text_mun;
        private TextView start_time;
        private TextView end_time;
        private ImageView imageView;
        private TextView details;

    }
}
