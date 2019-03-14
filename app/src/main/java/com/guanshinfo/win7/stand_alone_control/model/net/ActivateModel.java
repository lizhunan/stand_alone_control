/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model.net;

import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.yuan.leopardkit.http.base.BaseEnetity;

/**
 * Created by guanshinfo-lizhunan on 2017/7/27.
 * 激活码model
 */

public class ActivateModel extends BaseEnetity {

    private String userId;
    private String username;
    private String text;
    private String code;

    public ActivateModel(String userId, String username, String text, String code) {
        this.userId = userId;
        this.username = username;
        this.text = text;
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getRuqestURL() {
        return Constant.ACTIVATIONCODE_DO;
    }
}
