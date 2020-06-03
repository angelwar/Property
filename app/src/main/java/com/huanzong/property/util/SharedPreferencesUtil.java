package com.huanzong.property.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtil {
    protected static String FILE_NAME="huanzong_property";
    private static String FILE_NAME_USER = "property";

    //获取SharedPerences对象
    public static SharedPreferences getShared(Context context, String file_name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    //保存登录token
    public  static void addToken(Context context, String name){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("token",name).apply();
    }

    //查询token
    public static String queryToken(Context context){
        SharedPreferences shared = getShared(context,FILE_NAME_USER);
        return shared.getString("token","");
    }

    //保存uid
    public  static void addUid(Context context, int uid){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putInt("uid",uid).apply();
    }

    //查询uid
    public static int queryUid(Context context){
        SharedPreferences shared = getShared(context,FILE_NAME_USER);
        return shared.getInt("uid",-1);
    }
    //保存cid
    public  static void addCid(Context context, int uid){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putInt("cid",uid).apply();
    }

    //查询cid
    public static int queryCid(Context context){
        SharedPreferences shared = getShared(context,FILE_NAME_USER);
        return shared.getInt("cid",-1);
    }


    /**
     * 清除登录用户
     * @param context
     */
    public static void deleteUser(Context context){
        getShared(context,FILE_NAME_USER).edit().clear().apply();
    }

    //读取历史数据
    public static List<String> getHisData(Context context){
        SharedPreferences shared = getShared(context, FILE_NAME);
        int number = shared.getInt("number", 0);
        List<String> strings=new ArrayList<>();
        if(number!=0){
            for (int i = number-1; i >= 0; i--) {
                String string = shared.getString(FILE_NAME + i, null);
                strings.add(string);
            }
        }
        return strings;
    }
    //删除历史数据
    public static void deleHisData(Context context){
        SharedPreferences shared = getShared(context, FILE_NAME);
        shared.edit().clear().commit();
    }
    //添加历史数据
    public static void addHisData(Context context, String data){
        SharedPreferences shared = getShared(context, FILE_NAME);
        List<String> hisData = getHisData(context);
        boolean isExist=false;
        for (String str : hisData) {
            if(str.equals(data)){
                isExist=true;
            }
        }
        if(!isExist){
            SharedPreferences.Editor edit = shared.edit();
            int number = shared.getInt("number", 0);
            List<String> strings=new ArrayList<>();
            edit.putString(FILE_NAME+number,data);
            edit.putInt("number",number+1);
            edit.commit();
        }
    }

    public static String queryCidsName(Context context) {
        SharedPreferences shared = getShared(context,FILE_NAME_USER);
        return shared.getString("cidsName","环纵媒体科技");
    }

    //保存cid
    public  static void addCidsName(Context context, String cidsName){
        SharedPreferences sharedPreferences = getShared(context,FILE_NAME_USER);
        sharedPreferences.edit().putString("cidsName",cidsName).apply();
    }
}
