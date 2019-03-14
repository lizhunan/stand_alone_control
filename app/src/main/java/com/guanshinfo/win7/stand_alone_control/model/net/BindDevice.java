/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model.net;

import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.yuan.leopardkit.http.base.BaseEnetity;

/**
 * Created by guanshinfo-lizhunan on 2017/7/25.
 * 绑定设备model
 */

public class BindDevice extends BaseEnetity {

    private String userId;
    private String clientVer;
    private String text;

    public BindDevice(String userId, String clientVer, String text) {
        this.userId = userId;
        this.clientVer = clientVer;
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientVer() {
        return clientVer;
    }

    public void setClientVer(String clientVer) {
        this.clientVer = clientVer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getRuqestURL() {
        return Constant.BIND_DO;
    }
}
