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
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.model.net.ActivateModel;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by guanshinfo-lizhunan on 2017/7/26.
 * 激活Dialog
 */

public class ActivateDialog {

    private Context context;
    private Activity activity;
    private SharedPreferences userSp;
    private SharedPreferences deviceSp;
    private SharedPreferences.Editor userEditor;

    public ActivateDialog(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void showDialog() {
        userSp = BaseApplication.getContext().getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
        deviceSp = BaseApplication.getContext().getSharedPreferences(Constant.DEVICE_SP_TABLE, Context.MODE_PRIVATE);
        userEditor = userSp.edit();
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.recharge));
        builder.setMessage(context.getResources().getString(R.string.please_input_regist_code));
        builder.setIcon(R.mipmap.logo_bee);
        builder.setView(editText);
        builder.setPositiveButton(context.getResources().getString(R.string.entry), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.check_msg), Toast.LENGTH_SHORT).show();
                } else {
                    LeopardHttp.SEND(HttpMethod.POST, context, new ActivateModel(userSp.getString(Constant.USERID_SP_VALUSE, ""), userSp.getString(Constant.USERNAME_SP_VALUSE, ""),
                            deviceSp.getString(Constant.DEVICEID_SP_VALUSE, ""), editText.getText().toString()), new HttpRespondResult() {
                        @Override
                        public void onSuccess(String content) {
                            try {
                                if (content.equals("codeerror")) {
                                    Toast.makeText(context, context.getResources().getString(R.string.registcode_error), Toast.LENGTH_SHORT).show();
                                } else if (content.equals("error")) {
                                    Toast.makeText(context, context.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                                } else {
                                    int day = userSp.getInt(Constant.LIMITDAY_SP_VALUSE, 0);
                                    Log.d("day", "" + day);
                                    Log.d("day", "" + content);
                                    String limitTime = time(Integer.valueOf(content), userSp.getString(Constant.TIMELIMIT_SP_VALUSE, ""));
                                    Log.d("day", "" + limitTime);
                                    userEditor.putString(Constant.USERTYPE_SP_VALUSE, "1");
                                    userEditor.putString(Constant.TIMELIMIT_SP_VALUSE, limitTime);
                                    userEditor.putString(Constant.REGISTEDCODE_SP_VALUSE, editText.getText().toString());
                                    userEditor.apply();
                                    Toast.makeText(context, context.getResources().getString(R.string.recharge_success), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(context, context.getResources().getString(R.string.recharge_failed), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
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
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 充值后叠加日期
     *
     * @param dayAddNum
     * @param day
     * @return
     */
    private String time(long dayAddNum, String day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowDate = null;
        try {
            nowDate = df.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate2 = new Date(nowDate.getTime() + dayAddNum * 24 * 60 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateOk = simpleDateFormat.format(newDate2);
        return dateOk;
    }

}
