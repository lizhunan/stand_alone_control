/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import com.guanshinfo.win7.stand_alone_control.bean.LoginBean;

/**
 * Created by guanshinfo-lizhunan on 2017/7/20.
 * 登录接口
 */

public interface OnLogin {

    void onLoading();
    void onLoaded();
    void onSuccess(LoginBean loginBean);
    void onFailed(String s);
}
