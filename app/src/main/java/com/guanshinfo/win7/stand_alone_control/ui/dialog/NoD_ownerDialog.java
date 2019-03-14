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
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.guanshinfo.win7.stand_alone_control.R;

/**
 * Created by guanshinfo-lizhunan on 2017/7/20.
 * 如果没有系统权限，该Dialog负责禁止用户进入系统
 */

public class NoD_ownerDialog {

    private Context context;
    private Activity activity;

    public NoD_ownerDialog(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void showDialog(){
        try {
            final AlertDialog alertDialog;
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.entry_app));
            builder.setMessage(context.getResources().getString(R.string.has_not_device_owner));
            builder.setIcon(R.mipmap.logo_bee);
            builder.setCancelable(false);//使屏幕外点击监听失效
            builder.setNegativeButton(context.getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activity.finish();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
