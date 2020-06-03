package com.huanzong.property;

import com.huanzong.property.util.SharedPreferencesUtil;
import com.youth.xframe.base.XApplication;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends XApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);//调试完可以关闭
        JPushInterface.init(this);
    }


    public static String getToken(){
        return SharedPreferencesUtil.queryToken(getInstance());
    }
}