package com.guet.jason.sportsfriend.Adapter;

/**
 * Created by 吴剑锋 on 2017/4/13.EncounterAdapter
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.Chateachother;
import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Participat;
import com.guet.jason.sportsfriend.MyView.Personsport;
import com.guet.jason.sportsfriend.R;
import com.guet.jason.sportsfriend.Xiu.SquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;


public class EncounterAdapter extends BaseAdapter {
    private List<Personsport> orders;
    private Context context;
    private String my_nane;

    public EncounterAdapter(Context context, List<Personsport> orders, String my_nane) {
        this.orders = orders;
        this.context = context;
        this.my_nane = my_nane;
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

    public void addorder(List<Personsport> orders) {
        this.orders=orders;
    }


    public List<Personsport> getOrders() {
        return orders;
    }

    public void clearOrders() {
        orders.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EncounterAdapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.char_list, null);
            holder = new EncounterAdapter.ViewHolder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.user_name);
            holder.text_mun = (TextView) convertView.findViewById(R.id.user_char);
            holder.imageView = (ImageView) convertView.findViewById(R.id.user_icon);
            convertView.setTag(holder);
        } else {
            holder = (EncounterAdapter.ViewHolder) convertView.getTag();
        }
        holder.textViewTitle.setText(orders.get(position).getUserfrom());
        Picasso.with(context).load(orders.get(position).getUser().getFileUrl()).transform(new SquareTransform()).into(holder.imageView);
        return convertView;
    }

    class ViewHolder {
        private TextView textViewTitle;
        private TextView text_mun;
        private ImageView imageView;
    }
}