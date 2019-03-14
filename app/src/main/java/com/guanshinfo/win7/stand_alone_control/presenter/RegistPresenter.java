/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.presenter;

import android.content.Context;
import android.os.Handler;

import com.guanshinfo.win7.stand_alone_control.model.OnRegist;
import com.guanshinfo.win7.stand_alone_control.model.RegistModel;
import com.guanshinfo.win7.stand_alone_control.view.IRegist;

/**
 * Created by guanshinfo-liuzhunan on 2017/7/24.
 * 注册信息提供
 */

public class RegistPresenter {

    private RegistModel registModel;
    private IRegist iRegist;
    private Handler handler = new Handler();

    public RegistPresenter(IRegist iRegist) {
        this.iRegist = iRegist;
        this.registModel = new RegistModel();
    }

    public void regist(Context context, String username, String password, String phone, String passwordask, String passworda, String inviteCode, String email) {
        registModel.regist(new OnRegist() {
            @Override
            public void onLoading() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegist.onLoading();
                    }
                });
            }

            @Override
            public void onLoaded() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegist.onLoaded();
                    }
                });
            }

            @Override
            public void onSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegist.onSuccess();
                    }
                });
            }

            @Override
            public void onFailed(final String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iRegist.onFiled(s);
                    }
                });
            }
        }, context, username, password, phone, passwordask, passworda, inviteCode, email);
    }
}
