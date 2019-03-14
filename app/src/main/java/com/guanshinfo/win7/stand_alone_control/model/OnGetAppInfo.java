/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/12.
 * 监听是否获取app信息
 */

public interface OnGetAppInfo {

    /**
     * 获取数据成功
     * @param appInfos app信息
     */
    void onSuccess(List<AppInfo> appInfos);

    /**
     * 获取数据失败
     */
    void onFailed();

    /**
     * 正在加载数据
     */
    void onLoading();

    /**
     * 数据加载完成
     */
    void onLoaded();
}
