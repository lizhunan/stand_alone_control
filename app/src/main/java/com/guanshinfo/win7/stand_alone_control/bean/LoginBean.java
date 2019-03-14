/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by guanshinfo-lizhunan on 2017/7/20.
 * 登录接口bean
 */

public class LoginBean implements Serializable{


    /**
     * timeLimit : 2017-07-26 10:49:48
     * useable : 1 //剩余注册数量
     * userId : 90320063a1684bd583482a61c1371fc0
     * isRegistered : false //是否注册
     * userType : 0 //是否为试用期 0为试用期
     * localTime://网络时间
     */

    private String timeLimit;
    private int useable;
    private String userId;
    private boolean isRegistered;
    private String userType;
    private String localTime;

    private String limitDay;

    public String getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(String limitDay) {
        this.limitDay = limitDay;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getUseable() {
        return useable;
    }

    public void setUseable(int useable) {
        this.useable = useable;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

}
