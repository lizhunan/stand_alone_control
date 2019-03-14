/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.presenter;

import android.content.Context;
import android.os.Handler;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.model.ChangePwModel;
import com.guanshinfo.win7.stand_alone_control.model.OnChange;
import com.guanshinfo.win7.stand_alone_control.view.IChange;

/**
 * Created by guanshinfo-lizhunan on 2017/7/26.
 * 提供更改密码逻辑
 */

public class ChangePwPresenter {

    private ChangePwModel changePwModel;
    private IChange iChange;
    private Handler handler = new Handler();

    public ChangePwPresenter(IChange iChange) {
        this.iChange = iChange;
        this.changePwModel = new ChangePwModel();
    }

    public void change(final Context context, String userId, String deviceId, String newPw) {
        changePwModel.change(new OnChange() {
            @Override
            public void onLoading() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iChange.onLoading();
                    }
                });
            }

            @Override
            public void onLoaded() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iChange.onLoaded();
                    }
                });
            }

            @Override
            public void onSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iChange.onSuccess();
                    }
                });
            }

            @Override
            public void onFailed(String s) {
                if (s.equals("error")) {
                    iChange.onFiled(context.getResources().getString(R.string.server_error));
                } else if (s.equals(context.getResources().getString(R.string.net_failed))) {
                    iChange.onChange2Cache();
                }
            }
        }, context, userId, deviceId, newPw);
    }
}
