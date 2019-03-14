/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view.fragment;

import com.guanshinfo.win7.stand_alone_control.bean.PolicyInfo;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * 获取数据接口
 */

public interface IGetPolicy {

    /**
     * 显示数据
     * @param policyInfos policy数据
     */
    void showSuccess(List<PolicyInfo> policyInfos);
    void showFaild();

}
