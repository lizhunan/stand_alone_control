/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.model.net.FeedBackModel;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guanshinfo-lizhunan on 2017/7/21.
 * 用户反馈信息dialog
 */

public class UserIdeaDialog {

    private TextView usernameTv, deviceNameTv, deviceIdTv, deviceVerTv, subTimeTv;
    private EditText ideaEt;

    public void showDialog(final Context context) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            final SharedPreferences deviceSp = BaseApplication.getContext().getSharedPreferences(Constant.DEVICE_SP_TABLE, Context.MODE_PRIVATE);
            final SharedPreferences userSp = BaseApplication.getContext().getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.user_idea_layout, null);
            usernameTv = (TextView) view.findViewById(R.id.username_idea_tv);
            deviceIdTv = (TextView) view.findViewById(R.id.device_id_tv);
            deviceNameTv = (TextView) view.findViewById(R.id.devicename_idea_tv);
            deviceVerTv = (TextView) view.findViewById(R.id.deviceapi_idea_tv);
            subTimeTv = (TextView) view.findViewById(R.id.time_idea_tv);
            ideaEt = (EditText) view.findViewById(R.id.idea_et);
            deviceIdTv.setText(deviceSp.getString(Constant.DEVICEID_SP_VALUSE, "null"));
            deviceNameTv.setText(deviceSp.getString(Constant.DEVICENAME_SP_VALUSE, "null"));
            deviceVerTv.setText("Android" + deviceSp.getString(Constant.DEVICEVER_SP_VALUSE, "null"));
            subTimeTv.setText(formatter.format(curDate));
            usernameTv.setText(userSp.getString(Constant.USERNAME_SP_VALUSE, ""));
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.useridea));
            builder.setView(view);
            builder.setPositiveButton(context.getResources().getString(R.string.entry), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (ideaEt.getText().toString().equals("")) {
                        Toast.makeText(context, context.getResources().getString(R.string.check_msg), Toast.LENGTH_SHORT).show();
                    } else {
                        LeopardHttp.SEND(HttpMethod.POST, context, new FeedBackModel(userSp.getString(Constant.USERID_SP_VALUSE, ""), deviceSp.getString(Constant.DEVICENAME_SP_VALUSE, ""),
                                deviceSp.getString(Constant.DEVICEVER_SP_VALUSE, ""), ideaEt.getText().toString()), new HttpRespondResult() {
                            @Override
                            public void onSuccess(String content) {
                                if (content.equals("success")) {
                                    Toast.makeText(context, context.getResources().getString(R.string.submit_success), Toast.LENGTH_SHORT).show();
                                } else if (content.equals("error")) {
                                    Toast.makeText(context, context.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable error, String content) {
                                Toast.makeText(context, context.getResources().getString(R.string.net_failed), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
