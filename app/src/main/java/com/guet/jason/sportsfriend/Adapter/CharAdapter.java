package com.guet.jason.sportsfriend.Adapter;

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
import com.guet.jason.sportsfriend.R;
import com.guet.jason.sportsfriend.Xiu.SquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CharAdapter extends BaseAdapter {
    private List<Chateachother> orders;
    private Context context;
    private String my_nane;

    public CharAdapter(Context context, List<Chateachother> orders, String my_nane) {
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

    public void addorder(List<Chateachother> orders) {
        this.orders.addAll(orders);
    }


    public List<Chateachother> getOrders() {
        return orders;
    }

    public void clearOrders() {
        orders.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CharAdapter.ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.char_list, null);
            holder = new CharAdapter.ViewHolder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.user_name);
            holder.text_mun = (TextView) convertView.findViewById(R.id.user_char);
            holder.imageView = (ImageView) convertView.findViewById(R.id.user_icon);
            convertView.setTag(holder);
        } else {
            holder = (CharAdapter.ViewHolder) convertView.getTag();
        }
        if (my_nane.equals(orders.get(position).getChat_userA())) {
            holder.textViewTitle.setText(orders.get(position).getChat_userB());
            Picasso.with(context).load(orders.get(position).getChat_iconB().getFileUrl()).transform(new SquareTransform()).into(holder.imageView);
        } else {
            holder.textViewTitle.setText(orders.get(position).getChat_userA());
            Picasso.with(context).load(orders.get(position).getChat_icon().getFileUrl()).transform(new SquareTransform()).into(holder.imageView);
        }
        holder.text_mun.setText(orders.get(position).getChat_conten());
        return convertView;
    }


    class ViewHolder {
        private TextView textViewTitle;
        private TextView text_mun;
        private ImageView imageView;
    }
}
