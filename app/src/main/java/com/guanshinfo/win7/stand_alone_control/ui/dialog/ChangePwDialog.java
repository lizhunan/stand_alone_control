/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.app.Activity;
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
import com.guanshinfo.win7.stand_alone_control.model.net.ChangePwModel;
import com.guanshinfo.win7.stand_alone_control.presenter.ChangePwPresenter;
import com.guanshinfo.win7.stand_alone_control.view.UserActivity;
import com.hp.hpl.sparta.Text;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

/**
 * Created by guanshinfo-lizhunan on 2017/7/26.
 * 更改密码dialog
 */

public class ChangePwDialog {


    private Context context;
    private UserActivity activity;

    public ChangePwDialog(Context context, UserActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void showDialog() {
        try {
            final ChangePwPresenter changePwPresenter = new ChangePwPresenter(activity);
            final String userid;
            final String deviceid;
            final SharedPreferences userSp = BaseApplication.getContext().getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
            SharedPreferences deviceSp = BaseApplication.getContext().getSharedPreferences(Constant.DEVICE_SP_TABLE, Context.MODE_PRIVATE);
            final SharedPreferences.Editor userEditor = userSp.edit();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.change_password_layout, null);
            TextView userIdTv = (TextView) view.findViewById(R.id.userid_tv);
            TextView deviceIdTv = (TextView) view.findViewById(R.id.deviceid_tv);
            final EditText oldPwEt = (EditText) view.findViewById(R.id.oldpw_et);
            final EditText newPwEt = (EditText) view.findViewById(R.id.newpw_et);
            userid = userSp.getString(Constant.USERID_SP_VALUSE, "");
            deviceid = deviceSp.getString(Constant.DEVICEID_SP_VALUSE, "");
            userIdTv.setText(userid);
            deviceIdTv.setText(deviceid);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.changePw));
            builder.setMessage(context.getResources().getString(R.string.changePwMsg));
            builder.setIcon(R.mipmap.logo_bee);
            builder.setView(view);
            builder.setPositiveButton(context.getResources().getString(R.string.entry), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (userid.equals(context.getResources().getString(R.string.error)) || deviceid.equals("")) {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    } else {
                        if (newPwEt.getText().toString().equals("") || oldPwEt.getText().toString().equals("")) {
                            Toast.makeText(context, context.getResources().getString(R.string.check_msg), Toast.LENGTH_SHORT).show();
                        } else if (newPwEt.getText().toString().length() != 6) {
                            Toast.makeText(context, context.getResources().getString(R.string.d_password_tooshort), Toast.LENGTH_SHORT).show();
                        } else {
                            if (userSp.getString(Constant.DEVICEPASSWORD_SP_VALUSE, "").equals(oldPwEt.getText().toString())) {
                                userEditor.putString(Constant.DEVICEPASSWORD_SP_VALUSE, newPwEt.getText().toString());
                                userEditor.putBoolean(Constant.ISCHANGEDEVICEPW_SP_VALUSE, true);
                                userEditor.apply();
                                changePwPresenter.change(context, userid, deviceid, newPwEt.getText().toString());
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.old_password_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
            builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
