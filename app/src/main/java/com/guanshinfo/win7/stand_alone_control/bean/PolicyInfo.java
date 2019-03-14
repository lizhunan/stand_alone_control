/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.bean;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * 策略信息bean
 */

public class PolicyInfo {

    private String policy;

    private List<AppInfo> appInfos;

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }
}
