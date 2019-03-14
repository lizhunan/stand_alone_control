/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;

public class NetworkReceiver extends BroadcastReceiver {

    private SharedPreferences deviceSp;
    private SharedPreferences.Editor deviceEditor;

    @Override
    public void onReceive(Context context, Intent intent) {
        deviceSp = BaseApplication.getContext().getSharedPreferences(Constant.DEVICE_SP_TABLE,Context.MODE_PRIVATE);
        deviceEditor = deviceSp.edit();
        // 监听wifi是否连接一个有效路由
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Log.d("NetworkReceiver", "NETWORK_STATE_CHANGED_ACTION:");
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;
                Log.e("WIFI_IS", "isConnected" + isConnected);
                if (isConnected) {
                    deviceEditor.putBoolean(Constant.ISNET_SP_VALUSE,true);
                } else {
                    deviceEditor.putBoolean(Constant.ISNET_SP_VALUSE,false);
                    Log.d("断开", "断开");
                }
                deviceEditor.commit();
            }
        }
    }
}
