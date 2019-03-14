/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.model.net.BindDevice;
import com.guanshinfo.win7.stand_alone_control.view.MainActivity;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

/**
 * Created by guanshinfo-lizhunan on 2017/7/25.
 * 绑定机器dialog
 */

public class BindDeviceDialog {

    private Context context;
    private Activity activity;

    public BindDeviceDialog(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void showDialog(final String userId, final String deviceName, final String deviceId, int m) {
        try {
            final AlertDialog alertDialog;
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.bind_dervice));
            builder.setMessage(context.getResources().getString(R.string.bind_dervice_m) + m);
            builder.setIcon(R.mipmap.logo_bee);
            builder.setCancelable(false);//使屏幕外点击监听失效
            //将确定按钮监听设为空，在之后取出button的监听onclick事件拦截后面的dialog
            builder.setPositiveButton(context.getResources().getString(R.string.bind_dervice), null);
            builder.setNegativeButton(context.getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LeopardHttp.SEND(HttpMethod.GET, context, new BindDevice(userId, deviceName, deviceId), new HttpRespondResult() {
                        @Override
                        public void onSuccess(String content) {
                            switch (content) {
                                case "registered":
                                    Toast.makeText(activity, BaseApplication.getContext().getResources().getString(R.string.binded), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    break;
                                case "limit":
                                    Toast.makeText(activity, BaseApplication.getContext().getResources().getString(R.string.bind_limit), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    break;
                                case "success":
                                    Toast.makeText(activity, BaseApplication.getContext().getResources().getString(R.string.bind_success), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    break;
                                case "error":
                                    Toast.makeText(activity, BaseApplication.getContext().getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            Toast.makeText(activity, BaseApplication.getContext().getResources().getString(R.string.net_failed), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
