/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model.net;

import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.yuan.leopardkit.http.base.BaseEnetity;

/**
 * Created by guanshinfo-lizhunan on 2017/7/26.
 * 找回密码model
 */

public class CheckFlagModel extends BaseEnetity {

    private String userId;
    private String text;

    public CheckFlagModel(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getRuqestURL() {
        return Constant.CHECKFLAG_DO;
    }
}
