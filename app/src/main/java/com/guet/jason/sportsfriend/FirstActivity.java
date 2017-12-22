package com.guet.jason.sportsfriend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.guet.jason.sportsfriend.MyView.App;
import com.guet.jason.sportsfriend.MyView.CircleTransform;
import com.guet.jason.sportsfriend.MyView.Personsport;
import com.guet.jason.sportsfriend.MyView.Tool;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 *
 */
public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    MapView mMapView = null;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private static final double EARTH_RADIUS = 6378137;//赤道半径(单位m)
    private final static int CHANGE = 1;
    private Marker mMarkerA;
    private Button release, recommended, participation;
    //右边按钮
    private MyLocationConfiguration.LocationMode currentMode;
    private ImageButton messageButton, cycleBtn, personBtn, infobty;
    private static TextView first_top_text;
    private ImageView first_positioning;
    private Boolean mar_list = true, add_mark = true;
    boolean isFirstLoc = true;// 是否首次定位
    private boolean backFlag = false;
    private Toolbar toolbar;
    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGE:
                    first_top_text.setText(msg.obj + "");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "60b5cef4dc8267246e1cbbc464387755");
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_first);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init_view();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mMapView = (MapView) findViewById(R.id.bmapView);
        my_address();
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        mMapView.removeViewAt(2);//移除缩放控件

    }

    /**
     * 定位函数
     */
    private void my_address() {
        baiduMap = mMapView.getMap();
        mMapView.setLogoPosition(LogoPosition.logoPostionleftBottom);
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(myListener); // 注册监听函数
        this.setLocationOption();    //设置定位参数

//        MyLocationConfiguration configuration =new MyLocationConfiguration(BitmapDescriptorFactory.fromResource(R.mipmap.my_mark_this),true,)
        locationClient.start(); // 开始定位

    }

    private void init_view() {

        release = (Button) findViewById(R.id.to_release);
        recommended = (Button) findViewById(R.id.to_recommended);
        participation = (Button) findViewById(R.id.to_participation);
        messageButton = (ImageButton) findViewById(R.id.message_button);
        first_positioning = (ImageView) findViewById(R.id.first_positioning);
        first_top_text = (TextView) findViewById(R.id.first_top_text);
        cycleBtn = (ImageButton) findViewById(R.id.cycleBtn);
        personBtn = (ImageButton) findViewById(R.id.personBtn);
        infobty = (ImageButton) findViewById(R.id.infobty);
        personBtn.setOnClickListener(this);
        first_positioning.setOnClickListener(this);
        infobty.setOnClickListener(this);
        cycleBtn.setOnClickListener(this);
        release.setOnClickListener(this);
        recommended.setOnClickListener(this);
        participation.setOnClickListener(this);
        first_top_text.setOnClickListener(this);
    }

    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);
    }

    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            double Latitude = location.getLatitude();
            double Longitude = location.getLongitude();
            ((App) getApplication()).User_Latitude = Latitude;
            ((App) getApplication()).User_Longitude = Longitude;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);    //设置定位数据
            App.location_address = location.getAddrStr(); //保存定位地址
            Message msg = new Message();
            msg.what = FirstActivity.CHANGE;
            msg.obj = location.getAddrStr();
            FirstActivity.handler.sendMessage(msg);
//            first_top_text.setText(location.getAddrStr());
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 18);    //设置地图中心点以及缩放级别
//				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                baiduMap.animateMapStatus(u);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        add_mark = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mar_list = true;
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                BmobQuery<Personsport> marker_query1 = new BmobQuery<Personsport>();
                BmobQuery<Personsport> marker_query2 = new BmobQuery<Personsport>();
                marker_query1.addWhereEqualTo("Latitude", marker.getPosition().latitude);
                marker_query2.addWhereEqualTo("Longitude", marker.getPosition().longitude);
                List<BmobQuery<Personsport>> queries = new ArrayList<BmobQuery<Personsport>>();
                queries.add(marker_query1);
                queries.add(marker_query2);
                BmobQuery<Personsport> mainQuery = new BmobQuery<Personsport>();
                mainQuery.and(queries);
                mainQuery.findObjects(new FindListener<Personsport>() {
                    @Override
                    public void done(List<Personsport> list, BmobException e) {
                        if (e == null)
                            if (list.size() != 0)
                                if (mar_list) {
                                    Intent intent = new Intent(FirstActivity.this, ReleaseActivity.class);
                                    ((App) getApplication()).list = list;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("release", "加入");
                                    bundle.putInt("sign", 1);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    mar_list = false;
                                }
                    }
                });
                return true;
            }
        });
    }

    private void find_friend() {
        BmobQuery<Personsport> query1 = new BmobQuery<>();
        query1.addWhereNear("gpsAdd", new BmobGeoPoint(((App) getApplication()).User_Longitude, ((App) getApplication()).User_Latitude));
        query1.addWhereNotEqualTo("userform", ((App) getApplication()).model_User.getUsername());
        query1.setLimit(10);
        query1.findObjects(new FindListener<Personsport>() {
            @Override
            public void done(List<Personsport> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (!list.get(i).getUserfrom().equals(((App) getApplication()).model_User.getUsername())) {
                            String imgurl = list.get(i).getUser().getFileUrl();
                            LatLng latLng = new LatLng(list.get(i).Latitude, list.get(i).Longitude);
                            View convertView = LayoutInflater.from(FirstActivity.this).inflate(R.layout.marker_layout, null);
                            ImageView img = convertView.findViewById(R.id.Marker_img);
                            Picasso.with(FirstActivity.this).load(imgurl).transform(new CircleTransform()).into(img);
                            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                                    .fromBitmap(getViewBitmap(convertView));
                            MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bitmapDescriptor)
                                    .zIndex(9).draggable(false).alpha(0.6f);
                            ooA.animateType(MarkerOptions.MarkerAnimateType.grow);
                            mMarkerA = (Marker) (baiduMap.addOverlay(ooA));
                        }
                    }
                }
            }
        });
    }

    private Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return bitmap;
    }

    private void go_about(String s) {
        Intent intent = new Intent(FirstActivity.this, AboutActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("about", s);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cycleBtn:
                Intent intent = new Intent(FirstActivity.this, CircleActivity.class);
                startActivity(intent);
                break;
            case R.id.personBtn:
                Intent intent1 = new Intent(FirstActivity.this, PeopleActivity.class);
                Tool.addActivity(this);
                startActivity(intent1);
                break;
            case R.id.first_positioning:
                isFirstLoc = true;
                my_address();
                break;
            case R.id.first_top_text:
                Intent intent3 = new Intent(FirstActivity.this, PositioningActivity.class);
                startActivityForResult(intent3, 0);
                break;
            case R.id.infobty:
                Intent intent2 = new Intent(FirstActivity.this, InformationActivity.class);
                startActivity(intent2);
                break;
            case R.id.to_participation: //参与
                go_about("participation");
                break;
            case R.id.to_recommended:   //推荐
                find_friend();
                break;
            case R.id.to_release: //发布
                go_about("release");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                String address = data.getStringExtra("data");
                if (address.length() != 0)
                    position(address);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (backFlag) {
            //退出
            super.onBackPressed();
        } else {
            //单击一次提示信息
            Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show();
            backFlag = true;
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //3秒之后，修改flag的状态
                    backFlag = false;
                }

                ;
            }.start();
        }
    }

    /**
     * @param address 百度地图检索函数
     */
    private void position(String address) {
        GeoCoder mGeoCoder = GeoCoder.newInstance();
        // 得到GenCodeOption对象
        GeoCodeOption mGeoCodeOption = new GeoCodeOption();
        mGeoCodeOption.address(address);
        mGeoCodeOption.city("桂林");
        mGeoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(FirstActivity.this, "输入地点错误",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 得到具体地址的坐标
                    LatLng pos = geoCodeResult.getLocation();
                    // 得到一个标记的控制器
                    MarkerOptions mMarkerOptions = new MarkerOptions();
                    // 我们设置标记的时候需要传入的参数
                    BitmapDescriptor mbitmapDescriptor = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_gcoding);
                    // 设置标记的图标
                    mMarkerOptions.icon(mbitmapDescriptor);
                    // 设置标记的坐标
                    mMarkerOptions.position(pos);
                    // 添加标记
                    baiduMap.addOverlay(mMarkerOptions);
                    // 设置地图跳转的参数
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                            .newLatLngZoom(pos, 15);
                    // 设置进行地图跳转
                    baiduMap.setMapStatus(mMapStatusUpdate);
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            }
        });
        // 这句话必须写，否则监听事件里面的都不会执行
        mGeoCoder.geocode(mGeoCodeOption);
    }
}
