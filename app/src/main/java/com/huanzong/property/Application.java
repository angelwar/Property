package com.huanzong.property;

import com.youth.xframe.base.XApplication;

import cn.jpush.android.api.JPushInterface;

public class Application extends XApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);//调试完可以关闭
        JPushInterface.init(this);
    }
}
