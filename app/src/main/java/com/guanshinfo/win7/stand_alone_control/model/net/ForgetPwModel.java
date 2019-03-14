/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model.net;

import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.yuan.leopardkit.http.base.BaseEnetity;

/**
 * Created by guanshinfo-lizhunan on 2017/8/2.
 * 忘记密码model
 */

public class ForgetPwModel extends BaseEnetity {

    private String username;
    private String pwA;
    private String pwQ;

    public ForgetPwModel(String username, String pwA, String pwQ) {
        this.username = username;
        this.pwA = pwA;
        this.pwQ = pwQ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwA() {
        return pwA;
    }

    public void setPwA(String pwA) {
        this.pwA = pwA;
    }

    public String getPwQ() {
        return pwQ;
    }

    public void setPwQ(String pwQ) {
        this.pwQ = pwQ;
    }

    @Override
    public String getRuqestURL() {
        return Constant.FORGETPW_DO;
    }
}
