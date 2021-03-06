/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.bean.PolicyInfo;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * 监听是否有策略信息
 */

public interface OnGetPolicyInfo {

    /**
     * 获取数据成功
     * @param policyInfos policy信息
     */
    void onSuccess(List<PolicyInfo> policyInfos);

    /**
     * 获取数据失败
     */
    void onFailed();

}
