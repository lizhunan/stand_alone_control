/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import com.guanshinfo.win7.stand_alone_control.bean.LoginBean;
import com.guanshinfo.win7.stand_alone_control.bean.RegistBean;

/**
 * Created by guanshinfo-lizhunan on 2017/7/24.
 * 注册接口
 */

public interface OnRegist {

    void onLoading();
    void onLoaded();
    void onSuccess();
    void onFailed(String s);
}
