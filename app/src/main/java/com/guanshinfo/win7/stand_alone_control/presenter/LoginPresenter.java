/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.presenter;

import android.content.Context;
import android.os.Handler;

import com.guanshinfo.win7.stand_alone_control.bean.LoginBean;
import com.guanshinfo.win7.stand_alone_control.model.LoginModel;
import com.guanshinfo.win7.stand_alone_control.model.OnLogin;
import com.guanshinfo.win7.stand_alone_control.view.ILogin;

/**
 * Created by guanshinfo-lizhunan on 2017/7/20.
 * 登录信息提供
 */

public class LoginPresenter {

    private LoginModel loginModel;
    private ILogin iLogin;
    private Handler handler = new Handler();

    public LoginPresenter(ILogin iLogin) {
        this.iLogin = iLogin;
        this.loginModel = new LoginModel();
    }

    public void login(Context context, String username, String passwrod) {
        loginModel.login(new OnLogin() {
            @Override
            public void onLoading() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLogin.onLoading();
                    }
                });
            }

            @Override
            public void onLoaded() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLogin.onLoaded();
                    }
                });
            }

            @Override
            public void onSuccess(final LoginBean loginBean) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLogin.onSuccess(loginBean);
                    }
                });
            }

            @Override
            public void onFailed(final String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLogin.onFiled(s);
                    }
                });
            }
        }, context, username, passwrod);
    }

    public void login(Context context) {
        loginModel.login(new OnLogin() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded() {

            }

            @Override
            public void onSuccess(final LoginBean loginBean) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLogin.onSuccess(loginBean);
                    }
                });
            }

            @Override
            public void onFailed(final String s) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iLogin.onFiled(s);
                    }
                });
            }
        }, context);
    }
}
