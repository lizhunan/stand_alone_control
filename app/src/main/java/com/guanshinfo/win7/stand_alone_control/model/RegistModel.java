/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import android.content.Context;
import android.util.Log;

import com.guanshinfo.win7.stand_alone_control.R;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by guanshinfo-lizhunan on 2017/7/24.
 * 获取注册内容
 */

public class RegistModel {

    public void regist(final OnRegist onRegist, final Context context, String username, String password, String phone, String passwordask, String passworda, String inviteCode, String email) {
        onRegist.onLoading();
        LeopardHttp.SEND(HttpMethod.POST, context, new com.guanshinfo.win7.stand_alone_control.model.net.RegistModel(username, password, phone, passworda, passwordask, inviteCode, email), new HttpRespondResult() {
            @Override
            public void onSuccess(String content) {
                Log.d("content", content);
                if (content.equals("error")) {
                    onRegist.onFailed(context.getResources().getString(R.string.server_error));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        boolean regist = jsonObject.getBoolean("regist");
                        boolean username = jsonObject.getBoolean("username");
                        boolean phone = jsonObject.getBoolean("phone");
                        if (regist) {
                            onRegist.onSuccess();
                        } else {
                            if (!username) {
                                onRegist.onFailed(context.getResources().getString(R.string.username_repeat));
                            } else if (!phone) {
                                onRegist.onFailed(context.getResources().getString(R.string.phone_repeat));
                            } else {
                                onRegist.onFailed(context.getResources().getString(R.string.regist_failed));
                            }
                        }
                    } catch (JSONException e) {
                        onRegist.onFailed(context.getResources().getString(R.string.regist_json_failed));
                        e.printStackTrace();
                    }
                }
                onRegist.onLoaded();
            }

            @Override
            public void onFailure(Throwable error, String content) {
                onRegist.onFailed(context.getResources().getString(R.string.net_failed));
                onRegist.onLoaded();
            }
        });
    }
}
