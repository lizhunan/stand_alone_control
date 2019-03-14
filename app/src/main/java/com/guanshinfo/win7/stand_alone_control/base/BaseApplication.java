/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.base;

import android.Manifest;
import android.app.Application;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.guanshinfo.win7.stand_alone_control.receiver.MyDeviceAdminReceiver;
import com.guanshinfo.win7.stand_alone_control.receiver.NetworkReceiver;
import com.yuan.leopardkit.LeopardHttp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * Created by guanshinfo-lizhunan on 2017/7/10.
 * application类
 */

public class BaseApplication extends Application {

    private static BaseApplication application;
    public static DevicePolicyManager mDPM;
    public static ComponentName mComponentName;
    private SharedPreferences userSp;
    private SharedPreferences.Editor editor;
    public static boolean isFirstDevicePw = false;
    private NetworkReceiver networkReceiver;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, MyDeviceAdminReceiver.class);
        LeopardHttp.bindServer(Constant.TOMCAT_HOST);

        networkReceiver = new NetworkReceiver();
        IntentFilter intentNetFilter = new IntentFilter();
        intentNetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentNetFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentNetFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(networkReceiver, intentNetFilter);
    }

    public static Context getContext() {
        return application;
    }

}
