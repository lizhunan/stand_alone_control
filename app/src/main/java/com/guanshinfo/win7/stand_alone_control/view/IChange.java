/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view;

/**
 * Created by guanshinfo-lizhunan on 2017/7/26.
 * 更改密码逻辑接口
 */

public interface IChange {

    void onSuccess();

    void onFiled(String s);

    void onLoading();

    void onLoaded();

    void onChange2Cache();

}
