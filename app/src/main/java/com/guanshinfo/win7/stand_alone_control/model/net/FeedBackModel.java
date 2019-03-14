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
 * 用户反馈信息model
 */

public class FeedBackModel extends BaseEnetity {

    private String userId;
    private String clientVer;
    private String osVer;
    private String message;

    public FeedBackModel(String userId, String clientVer, String osVer, String message) {
        this.userId = userId;
        this.clientVer = clientVer;
        this.osVer = osVer;
        this.message = message;
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

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getRuqestURL() {
        return Constant.SUBMITFEEDBACK_DO;
    }
}
