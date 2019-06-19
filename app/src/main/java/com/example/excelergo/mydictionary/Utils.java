package com.example.excelergo.mydictionary;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class Utils extends RegisterActivity{
    public static boolean saveUserInfo(Context context, String account,
                                       String psw) {
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", account);
        edit.putString("pwd",psw);
        edit.commit();
        return true;
    }
    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        String account = sp.getString("userName", null);
        String psw = sp.getString("pwd", null);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("account", account);
        userMap.put("psw", psw);
        return userMap;
    }
}

