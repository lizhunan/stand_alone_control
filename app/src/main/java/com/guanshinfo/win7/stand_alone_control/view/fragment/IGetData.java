/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view.fragment;

import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/12.
 * 获取数据接口
 */

public interface IGetData {

    /**
     * 显示数据
     * @param appInfos app数据
     */
    void showSuccess(List<AppInfo> appInfos);
    void showFaild();

    /**
     * 显示进度
     */
    void showProgress();

    /**
     * 关闭进度
     */
    void hintProgress();

    void refreshUI();
}
