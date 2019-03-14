/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.model.net.CheckFlagModel;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

/**
 * Created by guanshinfo-lizhunan on 2017/7/13.
 * 准入程序密码dialog
 */

public class EntryDialog {

    private Context context;
    private Activity activity;

    public EntryDialog(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void showDialog() {
        try {
            final SharedPreferences userSp = BaseApplication.getContext().getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
            final SharedPreferences deviceSp = BaseApplication.getContext().getSharedPreferences(Constant.DEVICE_SP_TABLE, Context.MODE_PRIVATE);
            final SharedPreferences.Editor userEditor = userSp.edit();
            final AlertDialog alertDialog;
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.entry_app));
            if (userSp.getBoolean(Constant.ISFIRSTDEVICEPASSWORD_SP_VALUSE, true)) {
                builder.setMessage(context.getResources().getString(R.string.d_first_password_hint));
            } else {
                builder.setMessage(context.getResources().getString(R.string.d_password_hint));
            }
            userEditor.putBoolean(Constant.ISFIRSTDEVICEPASSWORD_SP_VALUSE, false);
            userEditor.apply();
            builder.setIcon(R.mipmap.logo_bee);
            final EditText editText = new EditText(context);
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            builder.setView(editText);
            builder.setCancelable(false);//使屏幕外点击监听失效
            //将确定按钮监听设为空，在之后取出button的监听onclick事件拦截后面的dialog
            builder.setPositiveButton(context.getResources().getString(R.string.entry), null);
            //builder.setNeutralButton(context.getResources().getString(R.string.forget_pw), null);
            builder.setNegativeButton(context.getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BaseApplication.isFirstDevicePw = false;
                    activity.finish();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText.getText().toString().equals(userSp.getString(Constant.DEVICEPASSWORD_SP_VALUSE, "111111"))) {
                        BaseApplication.isFirstDevicePw = false;
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.passowrd_error), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
           /* alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LeopardHttp.SEND(HttpMethod.POST, context, new CheckFlagModel(userSp.getString(Constant.USERID_SP_VALUSE, ""), deviceSp.getString(Constant.DEVICEID_SP_VALUSE, "")), new HttpRespondResult() {
                        @Override
                        public void onSuccess(String content) {
                            if (content.equals("true")) {
                                Toast.makeText(context, context.getResources().getString(R.string.forget_password_success), Toast.LENGTH_SHORT).show();
                                userEditor.putString(Constant.DEVICEPASSWORD_SP_VALUSE, "111111");
                                userEditor.commit();
                            } else if (content.equals("false")) {
                                Toast.makeText(context, context.getResources().getString(R.string.forget_password_failed), Toast.LENGTH_SHORT).show();
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
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
