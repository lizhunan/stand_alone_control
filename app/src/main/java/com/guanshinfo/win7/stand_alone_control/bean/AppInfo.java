/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.bean;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.guanshinfo.win7.stand_alone_control.ui.indexui.PinYinUtils;

/**
 * Created by guanshinfo-lizhunan on 2017/7/12.
 * app信息bean类
 */

public class AppInfo implements Comparable<AppInfo> {

    private int ID;
    private String appLabel;
    private String versionName;
    private String des;
    private String size;
    private Drawable appIcon;
    private String pkgName;    //应用程序所对应的包名
    private String policy;

    private String pinyin;//排序字段

    public AppInfo() {
    }

    public AppInfo(String appLabel, String versionName, String des, String size, Drawable appIcon, String pkgName) {
        this.appLabel = appLabel;
        this.versionName = versionName;
        this.des = des;
        this.size = size;
        this.appIcon = appIcon;
        this.pkgName = pkgName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    @Override
    public int compareTo(AppInfo o) {
        return this.pinyin.compareTo(o.getPinyin());
    }
}
