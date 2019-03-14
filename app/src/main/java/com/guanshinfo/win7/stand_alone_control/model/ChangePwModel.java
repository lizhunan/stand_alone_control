/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import android.content.Context;

import com.guanshinfo.win7.stand_alone_control.R;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

/**
 * Created by guanshinfo-lizhunan on 2017/7/26.
 * 获取更改密码返回的数据
 */

public class ChangePwModel {

    public void change(final OnChange onChange, final Context context, String userId, String deviceId, String newPw) {
        onChange.onLoading();
        LeopardHttp.SEND(HttpMethod.POST, context, new com.guanshinfo.win7.stand_alone_control.model.net.ChangePwModel(userId, deviceId, newPw), new HttpRespondResult() {
            @Override
            public void onSuccess(String content) {
                if (content.equals("success")) {
                    onChange.onSuccess();
                } else if (content.equals("error")) {
                    onChange.onFailed(content);
                }
                onChange.onLoaded();
            }

            @Override
            public void onFailure(Throwable error, String content) {
                onChange.onFailed(context.getResources().getString(R.string.net_failed));
                onChange.onLoaded();
            }
        });
    }
}
