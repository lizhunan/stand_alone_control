/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.base;

import android.os.Environment;

/**
 * Created by guanshinfo-lizhunan on 2017/7/10.
 * 常量仓库
 */

public class Constant {

    /**
     * 用户信息SharedPreferences表信息
     */
    public static final String USER_SP_TABLE = "USER_SP_TABLE";
    /**
     * 策略信息SharedPreferences表信息
     */
    public static final String POLICY_SP_TABLE = "POLICY_SP_TABLE";
    /**
     * 设备信息SharedPreferences表信息
     */
    public static final String DEVICE_SP_TABLE = "DEVICE_SP_TABLE";
    /**
     * 设备是否联网SharedPreferences表信息
     */
    public static final String ISNET_SP_VALUSE = "ISNET_SP_VALUSE";
    /**
     * 用户是否第一次使用SharedPreferences字段信息
     */
    public static final String ISFIRST_SP_VALUSE = "ISFIRST_SP_VALUSE";
    /**
     * 用户是否第一次使用设备密码（提示是否需要更改默认密码）SharedPreferences字段信息
     */
    public static final String ISFIRSTDEVICEPASSWORD_SP_VALUSE = "ISFIRSTDEVICEPASSWORD_SP_VALUSE";
    /**
     * 用户是否自动登录SharedPreferences字段信息
     */
    public static final String ISAOTULOGIN_SP_VALUSE = "ISAOTULOGIN_SP_VALUSE";
    /**
     * 策略信息SharedPreferences字段信息
     */
    public static final String POLICY_SP_VALUSE = "POLICY_SP_VALUSE";
    /**
     * 设备idSharedPreferences字段信息
     */
    public static final String DEVICEID_SP_VALUSE = "DEVICEID_SP_VALUSE";
    /**
     * 设备名称SharedPreferences字段信息
     */
    public static final String DEVICENAME_SP_VALUSE = "DEVICENAME_SP_VALUSE";
    /**
     * 设备版本号SharedPreferences字段信息
     */
    public static final String DEVICEVER_SP_VALUSE = "DEVICEVER_SP_VALUSE";
    /**
     * 用户中心壁纸地址SharedPreferences字段信息
     */
    public static final String USERBACKGROUND_SP_VALUSE = "USERBACKGROUND_SP_VALUSE";
    /**
     * 剩余可绑定设备量SharedPreferences字段信息
     */
    public static final String USEABLE_SP_VALUSE = "USEABLE_SP_VALUSE";
    /**
     * 到期时间SharedPreferences字段信息
     */
    public static final String TIMELIMIT_SP_VALUSE = "TIMELIMIT_SP_VALUSE";
    /**
     * 服务器时间SharedPreferences字段信息
     */
    public static final String LOCALTIME_SP_VALUSE = "LOCALTIME_SP_VALUSE";
    /**
     * 是否绑定设备SharedPreferences字段信息
     */
    public static final String ISREGISTERED_SP_VALUSE = "ISREGISTERED_SP_VALUSE";
    /**
     * 是否为试用期用户SharedPreferences字段信息
     */
    public static final String USERTYPE_SP_VALUSE = "USERTYPE_SP_VALUSE";
    /**
     * 用户IDSharedPreferences字段信息
     */
    public static final String USERID_SP_VALUSE = "USERID_SP_VALUSE";
    /**
     * 用户账号SharedPreferences字段信息
     */
    public static final String USERNAME_SP_VALUSE = "USERNAME_SP_VALUSE";
    /**
     * 用户密码SharedPreferences字段信息
     */
    public static final String PASSWORD_SP_VALUSE = "PASSWORD_SP_VALUSE";
    /**
     * 用户手机号码SharedPreferences字段信息
     */
    public static final String USERPHONE_SP_VALUSE = "USERPHONE_SP_VALUSE";
    /**
     * 用户剩余天数SharedPreferences字段信息
     */
    public static final String LIMITDAY_SP_VALUSE = "LIMITDAY_SP_VALUSE";
    /**
     * 操作密码SharedPreferences字段信息
     */
    public static final String DEVICEPASSWORD_SP_VALUSE = "DEVICEPASSWORD_SP_VALUSE";
    /**
     * 用户邮箱
     */
    public static final String EMAIL_SP_VALUSE = "EMAIL_SP_VALUSE";
    /**
     * 是否修改操作密码操作密码SharedPreferences字段信息
     */
    public static final String ISCHANGEDEVICEPW_SP_VALUSE = "ISCHANGEDEVICEPW_SP_VALUSE";
    /**
     * 激活码SharedPreferences字段信息
     */
    public static final String REGISTEDCODE_SP_VALUSE = "REGISTEDCODE_SP_VALUSE";


    /**
     * 默认是否多选
     */
    public static boolean isMuCheck = false;

    /**
     * 更新app名称
     */
    public static final String UPDATE_APP_NAME = "gs.apk";

    /**
     * 本地存储路径
     */
    public static final String SD_PATH = Environment.getExternalStorageDirectory() + "/Safetycontrol/";

    /**
     * AES密钥
     */
    public static final String AES_KEY = "guanshinfo.com";

    /**
     * 主机地址
     */
    public static final String TOMCAT_HOST = "http://guanshinfo.com:8080/control/app/";
    //public static final String TOMCAT_HOST = "http://192.168.1.50:8080/control/app/";
    /**
     * 登录接口
     */
    public static final String LOGIN_DO = "login.do";

    /**
     * 注册接口
     */
    public static final String REGIST_DO = "regist.do";

    /**
     * 绑定设备接口
     */
    public static final String BIND_DO = "bind.do";

    /**
     * 更改设备信息接口
     */
    public static final String CHANGEPW_DO = "changePW.do";

    /**
     * 找回密码接口
     */
    public static final String CHECKFLAG_DO = "checkFlag.do";

    /**
     * 用户反馈信息接口
     */
    public static final String SUBMITFEEDBACK_DO = "submitFeedback.do";

    /**
     * 获取激活码接口
     */
    public static final String ACTIVATIONCODE_DO = "activationCode.do";

    /**
     * 忘记密码接口
     */
    public static final String FORGETPW_DO = "forgetpw.do";

}
