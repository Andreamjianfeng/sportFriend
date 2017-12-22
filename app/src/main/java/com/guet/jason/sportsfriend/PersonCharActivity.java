package com.guet.jason.sportsfriend;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import com.guet.jason.sportsfriend.Adapter.chatdailerAdapter;
import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.Chateachother;
import com.guet.jason.sportsfriend.MyView.Dailerlist;
import com.guet.jason.sportsfriend.MyView.Happyother;
import com.guet.jason.sportsfriend.MyView.Model_User;
import com.guet.jason.sportsfriend.MyView.SoundMeter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class PersonCharActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView person_char_list;
    private ImageView sound_img, send_img;
    private EditText send_dailer;
    private Boolean ok_chat = false, reply = true, btn_vocie = false, isShosrt = false;
    private int sign;
    private Button send_vocie;
    private String dailer, my_name, my_id, urlB;
    private BmobFile temp_file;
    private List<Happyother> order = new ArrayList<>();
    private chatdailerAdapter adpter;
    private ArrayList<Dailerlist> dailerlists = new ArrayList<>();
    private LinearLayout voice_rcd_hint_loading, voice_rcd_hint_rcding,
            voice_rcd_hint_tooshort;
    private String FileName = null;
    private MediaRecorder mRecorder = null;
    private ImageView img1, sc_img1;
    private SoundMeter mSensor;
    private View rcChat_popup;
    private LinearLayout del_re;
    private int flag = 1;
    private Handler mHandler = new Handler();
    private long startVoiceT, endVoiceT;
    private String voiceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_char);
        Bmob.initialize(this, "60b5cef4dc8267246e1cbbc464387755");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.person_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        my_name = ((App) getApplication()).model_User.getUsername();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        sign = data.getInt("sign");
        inti_view();
        switch (sign) {
            case 1:
                send_vocie.setEnabled(false);
                  is_ok_chat();
                break;
            case 2:
                is_ok_chat2();
                break;
            case 3:
                is_ok_chat3("happy_userB", "happy_userA");
                is_ok_chat3("happy_userA", "happy_userB");
                break;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void is_ok_chat2() {
        Happyother happyother = new Happyother();
        BmobFile temp_file = BmobFile.createEmptyFile();
        temp_file.setUrl(((App) getApplication()).chateachother.getChat_icon().getFileUrl());
        BmobFile temp_file2 = BmobFile.createEmptyFile();
        temp_file2.setUrl(((App) getApplication()).chateachother.getChat_iconB().getFileUrl());
        happyother.setHappy_icon(temp_file);
        happyother.setHappy_iconB(temp_file2);
        happyother.setHappy_userA(((App) getApplication()).chateachother.getChat_userA());
        happyother.setHappy_conten(((App) getApplication()).chateachother.getChat_conten());
        happyother.setWho_con(((App) getApplication()).chateachother.getChat_userA());
        order.add(happyother);
        adpter.addorder(order);
        adpter.notifyDataSetChanged();
        person_char_list.setSelection(person_char_list.getCount() - 1);
    }

    private void is_ok_chat3(String uerA, String UerB) {
        BmobQuery<Happyother> query = new BmobQuery<Happyother>();
        query.addWhereEqualTo(uerA, ((App) getApplication()).chateachother.getChat_userA());
        BmobQuery<Happyother> query2 = new BmobQuery<Happyother>();
        query2.addWhereEqualTo(UerB, ((App) getApplication()).chateachother.getChat_userB());
        List<BmobQuery<Happyother>> queries = new ArrayList<BmobQuery<Happyother>>();
        queries.add(query);
        queries.add(query2);
        BmobQuery<Happyother> mainQuery = new BmobQuery<Happyother>();
        mainQuery.and(queries);
        mainQuery.findObjects(new FindListener<Happyother>() {
            @Override
            public void done(List<Happyother> list, BmobException e) {
                if (list.size() != 0) {
                    order.clear();
                    order = list;
                    adpter.addorder(order);
                    adpter.notifyDataSetChanged();
                    person_char_list.setSelection(person_char_list.getCount() - 1);
                }
            }
        });


    }

    private void is_ok_chat() {
        BmobQuery<Chateachother> query = new BmobQuery<Chateachother>();
        query.addWhereEqualTo("chat_userA", ((App) getApplication()).model_User.getUsername());
        BmobQuery<Chateachother> query2 = new BmobQuery<Chateachother>();
        query2.addWhereEqualTo("chat_userB", ((App) getApplication()).personsport.getUserfrom());
        List<BmobQuery<Chateachother>> queries = new ArrayList<BmobQuery<Chateachother>>();
        queries.add(query);
        queries.add(query2);
        BmobQuery<Chateachother> mainQuery = new BmobQuery<Chateachother>();
        mainQuery.and(queries);
        mainQuery.findObjects(new FindListener<Chateachother>() {
            @Override
            public void done(List<Chateachother> list, BmobException e) {
                if (list.size() != 0) {
                    ok_chat = list.get(0).isChat_OK();
                    if (!ok_chat) {
                        temp_file = BmobFile.createEmptyFile();
                        temp_file.setUrl(((App) getApplication()).model_User.getIcon().getFileUrl());
                        prompt(list.get(0).getChat_conten());
                    } else {
                        sign = 3;
                    }
                    return;
                }
            }
        });
        BmobQuery<Chateachother> query3 = new BmobQuery<Chateachother>();
        query3.addWhereEqualTo("chat_userB", ((App) getApplication()).model_User.getUsername());
        BmobQuery<Chateachother> query4 = new BmobQuery<Chateachother>();
        query4.addWhereEqualTo("chat_userA", ((App) getApplication()).personsport.getUserfrom());
        List<BmobQuery<Chateachother>> queries2 = new ArrayList<BmobQuery<Chateachother>>();
        queries2.add(query3);
        queries2.add(query4);
        BmobQuery<Chateachother> mainQuery2 = new BmobQuery<Chateachother>();
        mainQuery2.and(queries2);
        mainQuery2.findObjects(new FindListener<Chateachother>() {
            @Override
            public void done(List<Chateachother> list, BmobException e) {
                if (list.size() != 0) {
                    sign = 2;
                    ((App) getApplication()).chateachother = list.get(0);
                    is_ok_chat2();
                }
            }
        });
    }

    private void inti_view() {
        person_char_list = (ListView) findViewById(R.id.person_list);
        sound_img = (ImageView) findViewById(R.id.sound_img);
        send_img = (ImageView) findViewById(R.id.send_this);
        send_dailer = (EditText) findViewById(R.id.send_dailer);
        send_vocie = (Button) findViewById(R.id.send_bty);
        sc_img1 = (ImageView) this.findViewById(R.id.sc_img1);
        del_re = (LinearLayout) this.findViewById(R.id.del_re);
        voice_rcd_hint_rcding = (LinearLayout) this
                .findViewById(R.id.voice_rcd_hint_rcding);
        voice_rcd_hint_loading = (LinearLayout) this
                .findViewById(R.id.voice_rcd_hint_loading);
        rcChat_popup = this.findViewById(R.id.rcChat_popup);
        voice_rcd_hint_tooshort = (LinearLayout) this
                .findViewById(R.id.voice_rcd_hint_tooshort);
        send_img.setOnClickListener(this);
        sound_img.setOnClickListener(this);
        send_dailer.setOnClickListener(this);
        send_vocie.setFocusable(true);
        send_vocie.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                //按下语音录制按钮时返回false执行父类OnTouch
                if (event.getAction() == MotionEvent.ACTION_DOWN && flag == 1)
                    System.out.println("123");
                if (!Environment.getExternalStorageDirectory().exists()) {
                    Toast.makeText(PersonCharActivity.this, "No SDCard", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (btn_vocie) {
                    int[] location = new int[2];
                    send_vocie.getLocationInWindow(location); // 获取在当前窗口内的绝对坐标
                    int btn_rc_Y = location[1];
                    int btn_rc_X = location[0];
                    int[] del_location = new int[2];
                    del_re.getLocationInWindow(del_location);
                    int del_Y = del_location[1];
                    int del_x = del_location[0];
                    if (event.getAction() == MotionEvent.ACTION_MOVE && flag == 1) {
                        if (!Environment.getExternalStorageDirectory().exists()) {
                            Toast.makeText(PersonCharActivity.this, "No SDCard", Toast.LENGTH_LONG).show();
                            return false;
                        }
                        if (event.getY() > 0) {//判断手势按下的位置是否是语音录制按钮的范围内
                            System.out.println("3");
                            // send_vocie.setBackgroundResource(R.drawable.voice_rcd_btn_pressed);
                            rcChat_popup.setVisibility(View.VISIBLE);
                            voice_rcd_hint_loading.setVisibility(View.VISIBLE);
                            voice_rcd_hint_rcding.setVisibility(View.GONE);
                            voice_rcd_hint_tooshort.setVisibility(View.GONE);
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    if (!isShosrt) {
                                        voice_rcd_hint_loading.setVisibility(View.GONE);
                                        voice_rcd_hint_rcding
                                                .setVisibility(View.VISIBLE);
                                    }
                                }
                            }, 300);
                            del_re.setVisibility(View.GONE);
                            startVoiceT = SystemClock.currentThreadTimeMillis();
                            System.out.println(startVoiceT);
                            voiceName = startVoiceT + ".amr";
                            System.out.println(voiceName + "hahha");
                            //  start(voiceName);
                            flag = 2;
                            if (reply) {
                                FileName = android.os.Environment.getExternalStorageDirectory() + "/" + voiceName;
                                if (!Environment.getExternalStorageState().equals(
                                        android.os.Environment.MEDIA_MOUNTED)) {
                                    return false;
                                }
                                if (mRecorder == null) {
                                    mRecorder = new MediaRecorder();
                                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                                    mRecorder.setOutputFile(FileName);
                                    try {
                                        mRecorder.prepare();
                                        mRecorder.start();

                                    } catch (IllegalStateException e) {
                                        System.out.print(e.getMessage());
                                    } catch (IOException e) {
                                        System.out.print(e.getMessage());
                                    }

                                }
                                reply = false;
                            }
                        }
                    } else if (event.getAction() == MotionEvent.ACTION_UP && flag == 2) {//松开手势时执行录制完成
                        System.out.println("4");
                        if (mRecorder != null&&event.getY() >0) {
                            mRecorder.stop();
                            mRecorder.release();
                            mRecorder = null;
                            reply = true;
                            final BmobFile icon = new BmobFile(new File(FileName));
                            icon.uploadblock(new UploadFileListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        temp_file = BmobFile.createEmptyFile();
                                        temp_file.setUrl(((App) getApplication()).chateachother.getChat_iconB().getFileUrl());
                                        Chateachother chateachother_equals = new Chateachother();
                                        String id = ((App) getApplication()).chateachother.getObjectId();
                                        final Happyother happyother = new Happyother();
                                        happyother.setHappy_conten("           ");
                                        happyother.setHappy_icon(temp_file);
                                        BmobFile temp_file2 = BmobFile.createEmptyFile();
                                        temp_file2.setUrl(((App) getApplication()).chateachother.getChat_icon().getFileUrl());
                                        happyother.setHappy_iconB(temp_file2);
                                        BmobFile temp_file3 = BmobFile.createEmptyFile();
                                        temp_file3.setUrl(icon.getFileUrl());
                                        happyother.setChat_voice(temp_file3);
                                        happyother.setHappy_userA(((App) getApplication()).chateachother.getChat_userA());
                                        happyother.setWho_con(my_name);
                                        happyother.setHappy_userB(((App) getApplication()).chateachother.getChat_userB());
                                        happyother.setHappy_OK(true);
                                        order.add(happyother);
                                        happyother.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                            }
                                        });
                                        chateachother_equals.setChat_OK(true);
                                        chateachother_equals.setChat_conten("语言消息");
                                        chateachother_equals.update(id, new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                            }
                                        });
                                        adpter.addorder(order);
                                        adpter.notifyDataSetChanged();
                                        person_char_list.setSelection(person_char_list.getCount() - 1);

                                    } else {
                                    }
                                }

                                @Override
                                public void onProgress(Integer value) {
                                    super.onProgress(value);
                                }
                            });

                        }
                        //   send_vocie.setBackgroundResource(R.drawable.voice_rcd_btn_nor);
                        if (event.getY() >= del_Y
                                && event.getY() <= del_Y + del_re.getHeight()
                                && event.getX() >= del_x
                                && event.getX() <= del_x + del_re.getWidth()) {
                            rcChat_popup.setVisibility(View.GONE);
                            del_re.setVisibility(View.GONE);
                            //top();
                            flag = 1;
                            File file = new File(android.os.Environment.getExternalStorageDirectory() + "/"
                                    + voiceName);
                            if (file.exists()) {
                                file.delete();
                            }
                        } else {

                            voice_rcd_hint_rcding.setVisibility(View.GONE);
                            stop();
                            endVoiceT = SystemClock.currentThreadTimeMillis();
                            System.out.println(endVoiceT);
                            System.out.println(voiceName + "hahha");
                            flag = 1;
                            int time = (int) ((endVoiceT - startVoiceT) / 100);
                            if (time < 1) {
                                isShosrt = true;
                                voice_rcd_hint_loading.setVisibility(View.GONE);
                                voice_rcd_hint_rcding.setVisibility(View.GONE);
                                voice_rcd_hint_tooshort.setVisibility(View.VISIBLE);
                                mHandler.postDelayed(new Runnable() {
                                    public void run() {
                                        voice_rcd_hint_tooshort
                                                .setVisibility(View.GONE);
                                        rcChat_popup.setVisibility(View.GONE);
                                        isShosrt = false;
                                    }
                                }, 500);
                                return false;
                            }
                            rcChat_popup.setVisibility(View.GONE);
                        }
                    }
                    if (event.getY() < 0) {//手势按下的位置不在语音录制按钮的范围内
                        //  System.out.println("5");
                        Animation mLitteAnimation = AnimationUtils.loadAnimation(PersonCharActivity.this,
                                R.anim.cancel_rc);
                        Animation mBigAnimation = AnimationUtils.loadAnimation(PersonCharActivity.this,
                                R.anim.cancel_rc2);
                        voice_rcd_hint_rcding.setVisibility(View.GONE);
                        del_re.setVisibility(View.VISIBLE);
                        del_re.setBackgroundResource(R.drawable.use_voice_backgrund);
                        if (event.getY() >= del_Y
                                && event.getY() <= del_Y + del_re.getHeight()
                                && event.getX() >= del_x
                                && event.getX() <= del_x + del_re.getWidth()) {
                            //     del_re.setBackgroundResource(R.drawable.voice_rcd_cancel_bg_focused);
                            sc_img1.startAnimation(mLitteAnimation);
                            sc_img1.startAnimation(mBigAnimation);
                        }
                    } else {

                        del_re.setVisibility(View.GONE);
                        del_re.setBackgroundResource(0);
                    }
                }
                return true;
            }
        });

        adpter = new chatdailerAdapter(PersonCharActivity.this, order, ((App) getApplication()).model_User.getUsername());
        person_char_list.setAdapter(adpter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_this:
                dailer = send_dailer.getText().toString();
                send_dailer.setText("");
                send();
                break;
            case R.id.sound_img:
                if (btn_vocie) {
                    btn_vocie = false;
                    send_dailer.setVisibility(View.VISIBLE);
                    send_vocie.setVisibility(View.GONE);
                } else {
                    btn_vocie = true;
                    send_dailer.setVisibility(View.GONE);
                    send_vocie.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    private static final int POLL_INTERVAL = 300;

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            stop();
        }
    };
    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            updateDisplay(amp);
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };
    private void stop() {
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        //   mSensor.stop();
    }

    private void updateDisplay(double signalEMA) {

        switch ((int) signalEMA) {
            /*case 0:
            case 1:
                volume.setImageResource(R.drawable.ampone);
                break;
            case 2:
            case 3:
                volume.setImageResource(R.drawable.amptwo);

                break;
            case 4:
            case 5:
                volume.setImageResource(R.drawable.ampthree);
                break;
            case 6:
            case 7:
                volume.setImageResource(R.drawable.ampfive);
                break;
            case 8:
            case 9:
                volume.setImageResource(R.drawable.ampfour);
                break;
            case 10:
            case 11:
                volume.setImageResource(R.drawable.ampsix);
                break;
            default:
                volume.setImageResource(R.drawable.ampqi);
                break;*/
        }
    }

    private void send() {
        switch (sign) {
            case 1:
                send_one();

                break;
            case 2:
                send_two();
                break;
            case 3:
                send_three();
                break;
        }

    }

    private void send_one() {
        Happyother happy = new Happyother();
        temp_file = BmobFile.createEmptyFile();
        temp_file.setUrl(((App) getApplication()).model_User.getIcon().getFileUrl());
        if (ok_chat) {
        } else {
            BmobQuery<Model_User> query = new BmobQuery<Model_User>();
            query.addWhereEqualTo("username", ((App) getApplication()).personsport.getUserfrom());
            query.findObjects(new FindListener<Model_User>() {
                @Override
                public void done(List<Model_User> list, BmobException e) {
                    if (list.size() == 1) {
                        urlB = list.get(0).getIcon().getFileUrl();
                        Chateachother chateachother = new Chateachother();
                        chateachother.setChat_conten(dailer);
                        chateachother.setChat_OK(false);
                        chateachother.setChat_icon(temp_file);
                        BmobFile tempbmobfile = BmobFile.createEmptyFile();
                        tempbmobfile.setUrl(urlB);
                        chateachother.setChat_iconB(tempbmobfile);
                        chateachother.setChat_userA(my_name);
                        chateachother.setChat_userB(((App) getApplication()).personsport.getUserfrom());
                        chateachother.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                }
                            }
                        });
                    }
                }
            });
            prompt(dailer);
        }
    }

    private void prompt(String dai) {
        order.clear();
        temp_file = BmobFile.createEmptyFile();
        temp_file.setUrl(((App) getApplication()).model_User.getIcon().getFileUrl());
        BmobFile temp_file2 = BmobFile.createEmptyFile();
        temp_file2.setUrl("http://oekt4jwrq.bkt.clouddn.com/%E6%B6%88%E6%81%AF-06-13.png");
        Happyother other2 = new Happyother();
        other2.setHappy_conten(dai);
        other2.setHappy_icon(temp_file);
        other2.setWho_con(my_name);
        other2.setHappy_iconB(temp_file2);
        other2.setHappy_OK(false);
        order.add(other2);
        Happyother other3 = new Happyother();
        other3.setHappy_iconB(temp_file2);
        other3.setHappy_OK(false);
        other3.setHappy_icon(temp_file);
        other3.setHappy_conten("亲，请等待主人回复哦");
        other3.setWho_con("sss");
        order.add(other3);
        adpter.addorder(order);
        send_dailer.setEnabled(false);
        send_img.setEnabled(false);
        adpter.notifyDataSetChanged();
        person_char_list.setSelection(person_char_list.getCount() - 1);
    }

    private void send_two() {
        temp_file = BmobFile.createEmptyFile();
        temp_file.setUrl(((App) getApplication()).chateachother.getChat_iconB().getFileUrl());
        Chateachother chateachother_equals = new Chateachother();
        String id = ((App) getApplication()).chateachother.getObjectId();
        final Happyother happyother = new Happyother();
        happyother.setHappy_conten(dailer);
        happyother.setHappy_icon(temp_file);
        BmobFile temp_file2 = BmobFile.createEmptyFile();
        temp_file2.setUrl(((App) getApplication()).chateachother.getChat_icon().getFileUrl());
        happyother.setHappy_iconB(temp_file2);
        happyother.setHappy_userA(((App) getApplication()).chateachother.getChat_userA());
        happyother.setWho_con(my_name);
        happyother.setHappy_userB(((App) getApplication()).chateachother.getChat_userB());
        order.add(happyother);
        happyother.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
            }
        });
        chateachother_equals.setChat_OK(true);
        chateachother_equals.setChat_conten(dailer);
        chateachother_equals.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });
        adpter.addorder(order);
        adpter.notifyDataSetChanged();
        person_char_list.setSelection(person_char_list.getCount() - 1);
        sign = 3;
    }

    private void send_three() {
        temp_file = BmobFile.createEmptyFile();
        temp_file.setUrl(((App) getApplication()).chateachother.getChat_iconB().getFileUrl());
        Chateachother chateachother_equals = new Chateachother();
        String id = ((App) getApplication()).chateachother.getObjectId();
        final Happyother happyother = new Happyother();
        happyother.setHappy_conten(dailer);
        happyother.setHappy_icon(temp_file);
        BmobFile temp_file2 = BmobFile.createEmptyFile();
        temp_file2.setUrl(((App) getApplication()).chateachother.getChat_icon().getFileUrl());
        happyother.setHappy_iconB(temp_file2);
        happyother.setHappy_userA(((App) getApplication()).chateachother.getChat_userA());
        happyother.setWho_con(my_name);
        happyother.setHappy_userB(((App) getApplication()).chateachother.getChat_userB());
        happyother.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
            }
        });
        chateachother_equals.setChat_OK(true);
        chateachother_equals.setChat_conten(dailer);
        chateachother_equals.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });
        order.add(happyother);
        adpter.addorder(order);
        adpter.notifyDataSetChanged();
        person_char_list.setSelection(person_char_list.getCount() - 1);
    }
}
