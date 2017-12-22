package com.guet.jason.sportsfriend.MyView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andream on 2017/9/21.
 * 工具类
 */

public class Tool {
   public static List<Activity> activities= new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeAllActivity(){
        for (Activity activity : activities){
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
    public static  void showData(Context context, String data){
        Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
    }
    public static void launchActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }
}
