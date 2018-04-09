package com.yuyang.boxuegu.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by YuYang on 2018/4/9 0009.
 */

public class AnalysisUtils {
    /**
     *
     * 从SharedPreference中读取用户名
     *
     * @param context
     * @return
     */
    public static String readLoginUsername(Context context) {

        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
         String userName = sp.getString("loginUserName", "");  //获取登入时的用户名
        return  userName;


    }


}
