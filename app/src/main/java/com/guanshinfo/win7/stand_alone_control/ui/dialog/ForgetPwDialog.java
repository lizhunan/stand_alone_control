/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.model.net.ForgetPwModel;
import com.guanshinfo.win7.stand_alone_control.view.LoginActivity;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/8/2.
 * 找回密码Dialog
 */

public class ForgetPwDialog {

    private Context context;
    private Spinner spinner;
    private EditText username, passwordQ;
    private List<String> list;
    private LoginActivity.MyHandler myHandler;

    public ForgetPwDialog(Context context) {
        this.context = context;
        myHandler = new LoginActivity.MyHandler(context);
    }

    public void showDialog() {
        list = new ArrayList<>();
        list.add("您的母校");
        list.add("您父亲的生日");
        list.add("您母亲的生日");
        list.add("您父亲的姓名");
        list.add("您母亲的姓名");
        final View view = LayoutInflater.from(context).inflate(R.layout.forgetpw_dialog, null);
        username = (EditText) view.findViewById(R.id.username_et);
        passwordQ = (EditText) view.findViewById(R.id.pwQ_et);
        spinner = (Spinner) view.findViewById(R.id.pwA_sp);
        spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, list));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.forget_pswd));
        builder.setMessage(context.getResources().getString(R.string.changePwInfo));
        builder.setIcon(R.mipmap.logo_bee);
        builder.setView(view);
        builder.setPositiveButton(context.getResources().getString(R.string.entry), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LeopardHttp.SEND(HttpMethod.POST, context, new ForgetPwModel(username.getText().toString(), passwordQ.getText().toString(), spinner.getSelectedItem().toString()), new HttpRespondResult() {
                    @Override
                    public void onSuccess(String content) {
                        try {
                            if (content.equals("error")) {
                                Toast.makeText(context, context.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                            } else if (content.equals("username_error")) {
                                Toast.makeText(context, context.getResources().getString(R.string.username_error), Toast.LENGTH_SHORT).show();
                            } else if (content.equals("password_error")) {
                                Toast.makeText(context, context.getResources().getString(R.string.aq_error), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.pwback_success), Toast.LENGTH_SHORT).show();
                                Message message = Message.obtain();
                                message.what = 1;
                                message.obj = content;
                                myHandler.sendMessage(message);
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, context.getResources().getString(R.string.regist_json_failed), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        Toast.makeText(context, context.getResources().getString(R.string.net_failed), Toast.LENGTH_SHORT).show();
                    }
                });
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
}
