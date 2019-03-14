/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

/**
 * Created by guanshinfo-lizhunan on 2017/7/26.
 * 更改密码接口
 */

public interface OnChange {
    void onLoading();
    void onLoaded();
    void onSuccess();
    void onFailed(String s);
}
