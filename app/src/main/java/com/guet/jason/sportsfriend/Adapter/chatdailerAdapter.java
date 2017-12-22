package com.guet.jason.sportsfriend.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.Chateachother;
import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Happyother;
import com.guet.jason.sportsfriend.MyView.Participat;
import com.guet.jason.sportsfriend.PersonCharActivity;
import com.guet.jason.sportsfriend.R;
import com.guet.jason.sportsfriend.Xiu.SquareTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class chatdailerAdapter extends BaseAdapter {
    private Context context;
    private MediaPlayer mediaPlayer;
    private List<Happyother> orders;
    private String username = "sss";
    private Happyother entity;

    public chatdailerAdapter(PersonCharActivity context, List<Happyother> orders, String username) {
        this.orders = orders;
        this.context = context;
        this.username = username;
    }

    @Override
    public int getCount() {
        if (orders.size() == 0)
            return 0;
        else
            return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addorder(List<Happyother> orders) {
        this.orders = orders;
        Log.e("size", String.valueOf(orders.size()));
    }

    public void addHappy(Happyother happyother) {
        orders.add(happyother);
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        entity = orders.get(i);
        chatdailerAdapter.ViewHolder holder = null;
        Log.e("THIS", String.valueOf(i));
        Log.e("THIS", String.valueOf(entity.isHappy_OK()));
        boolean isComMsg = entity.getWho_con().equals(username);
        if (convertView == null) {
            holder = new chatdailerAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.chatting_item_msg_text_right, null);
            holder.textViewTitle_right = (TextView) convertView.findViewById(R.id.my_msg_right);
            holder.imageView_right = (ImageView) convertView.findViewById(R.id.user_img_right);
            holder.image_voice_right = (ImageView) convertView.findViewById(R.id.right_img);
            holder.linearLayout_right = (LinearLayout) convertView.findViewById(R.id.right_layout);
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.my_msg_left);
            holder.imageView = (ImageView) convertView.findViewById(R.id.user_img_left);
            holder.image_voice = (ImageView) convertView.findViewById(R.id.left_img);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.left_layout);
            holder.linearLayout1 = (LinearLayout) convertView.findViewById(R.id.lin_left);
            holder.linearLayout2 = (LinearLayout) convertView.findViewById(R.id.lin_right);
            convertView.setTag(holder);
        } else {
            holder = (chatdailerAdapter.ViewHolder) convertView.getTag();
        }
        if (isComMsg) {
            holder.textViewTitle_right.setText(orders.get(i).getHappy_conten());
            holder.linearLayout1.setVisibility(View.GONE);
            holder.linearLayout2.setVisibility(View.VISIBLE);
            if (entity.isHappy_OK())
                holder.image_voice_right.setBackgroundResource(R.drawable.right_voice);
            Picasso.with(context).load(orders.get(i).getHappy_icon().getFileUrl()).transform(new SquareTransform()).into(holder.imageView_right);
        } else {
            holder.linearLayout2.setVisibility(View.GONE);
            holder.linearLayout1.setVisibility(View.VISIBLE);
            holder.textViewTitle.setText(orders.get(i).getHappy_conten());
            if (entity.isHappy_OK())
                holder.image_voice.setBackgroundResource(R.drawable.left_voice);
            Picasso.with(context).load(orders.get(i).getHappy_iconB().getFileUrl()).transform(new SquareTransform()).into(holder.imageView);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               play_voice(i);
                Log.e("PLAT1", String.valueOf(i));
            }
        });
        holder.linearLayout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               play_voice(i);
                Log.e("PLAT2", String.valueOf(i));
            }
        });
        return convertView;
    }

    private void play_voice(final int mun) {
        Log.e("isOK", String.valueOf(orders.get(mun).isHappy_OK()));
        if (orders.get(mun).isHappy_OK()) {
            mediaPlayer = new MediaPlayer();
            Log.e("PLAT","t");
            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                    if (i == 100) {
                        mediaPlayer.start();
                        Log.e("PLAT","this");
                    }
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                              /*  *
                                 * 调用setDataSource();方法，并传入想要播放的音频文件的HTTP位置。
                                 */
                        mediaPlayer
                                .setDataSource(orders.get(mun).getChat_voice().getFileUrl());
                        Log.e("URL",entity.getChat_voice().getFileUrl());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                          /*  *
                             * 调用prepareAsync方法，它将在后台开始缓冲音频文件并返回。
                             */
                    mediaPlayer.prepareAsync();
                }
            }).start();
        }
    }

    class ViewHolder {
        private TextView textViewTitle;
        private TextView textViewTitle_right;
        private ImageView imageView;
        private ImageView imageView_right;
        private LinearLayout linearLayout;
        private LinearLayout linearLayout3;
        private LinearLayout linearLayout1;
        private LinearLayout linearLayout2;
        private LinearLayout linearLayout_right;
        private ImageView image_voice;
        private ImageView image_voice_right;
        public boolean isComMsg = true;
    }
}
