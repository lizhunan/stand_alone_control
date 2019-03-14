/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model.net;

import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.yuan.leopardkit.http.base.BaseEnetity;

/**
 * Created by guanshinfo-lizhunan on 2017/7/24.
 * 用户注册model
 */

public class RegistModel extends BaseEnetity {

    private String username;
    private String password;
    private String phone;
    private String pwA;
    private String pwQ;
    private String inviteCode;
    private String email;

    public RegistModel(String username, String password, String phone, String pwA, String pwQ, String inviteCode, String email) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.pwA = pwA;
        this.pwQ = pwQ;
        this.inviteCode = inviteCode;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getRuqestURL() {
        return Constant.REGIST_DO;
    }
}
