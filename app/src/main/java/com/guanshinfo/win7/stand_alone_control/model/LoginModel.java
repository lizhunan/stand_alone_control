/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.bean.LoginBean;
import com.guanshinfo.win7.stand_alone_control.view.LoginActivity;
import com.guanshinfo.win7.stand_alone_control.view.MainActivity;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by guanshinfo-lizhunan on 2017/7/20.
 * 登录并且获取登录信息
 */

public class LoginModel {

    /**
     * 登录
     *
     * @param onLogin
     * @param context
     * @param username
     * @param password
     */
    public void login(final OnLogin onLogin, final Context context, String username, String password) {
        //SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // final String localTime = sDateFormat.format(new java.util.Date());
        SharedPreferences deviceSp = BaseApplication.getContext().getSharedPreferences(Constant.DEVICE_SP_TABLE, Context.MODE_PRIVATE);
        final SharedPreferences userSp = BaseApplication.getContext().getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor userEditor = userSp.edit();
        onLogin.onLoading();
        Log.d("content", deviceSp.getString(Constant.DEVICEID_SP_VALUSE, "error"));
        LeopardHttp.SEND(HttpMethod.POST, context, new com.guanshinfo.win7.stand_alone_control.model.net.LoginModel(username,
                password, deviceSp.getString(Constant.DEVICEID_SP_VALUSE, "error")), new HttpRespondResult() {
            @Override
            public void onSuccess(String content) {
                Log.d("content", content);
                try {
                    if (content.equals("error")) {
                        onLogin.onFailed(context.getResources().getString(R.string.login_failed));
                        onLogin.onLoaded();
                    } else if (content.equals("password_error")) {
                        onLogin.onFailed(context.getResources().getString(R.string.password_or_username_error));
                        onLogin.onLoaded();
                    } else {
                        LoginBean loginBean = new LoginBean();
                        JSONObject jsonObject = new JSONObject(content);
                        String timeLimit = jsonObject.getString("timeLimit");
                        String localTime = jsonObject.getString("localTime");
                        int useable = jsonObject.getInt("useable");
                        String userId = jsonObject.getString("userId");
                        boolean isRegistered = jsonObject.getBoolean("isRegistered");
                        String userType = jsonObject.getString("userType");
                        String phone = jsonObject.getString("phone");
                        String devicePw = jsonObject.getString("password");
                        String email = jsonObject.getString("email");
                        loginBean.setRegistered(isRegistered);
                        loginBean.setTimeLimit(timeLimit);
                        loginBean.setLocalTime(localTime);
                        loginBean.setUseable(useable);
                        loginBean.setUserId(userId);
                        loginBean.setUserType(userType);
                        Log.d("aaa","aa"+isRegistered);
                        userEditor.putString(Constant.USERID_SP_VALUSE, userId);
                        userEditor.putBoolean(Constant.ISREGISTERED_SP_VALUSE, isRegistered);
                        userEditor.putString(Constant.TIMELIMIT_SP_VALUSE, timeLimit);
                        userEditor.putString(Constant.LOCALTIME_SP_VALUSE, localTime);
                        userEditor.putInt(Constant.USEABLE_SP_VALUSE, useable);
                        userEditor.putString(Constant.USERTYPE_SP_VALUSE, userType);
                        userEditor.putString(Constant.USERPHONE_SP_VALUSE, phone);
                        userEditor.putString(Constant.EMAIL_SP_VALUSE, email);
                        userEditor.putString(Constant.DEVICEPASSWORD_SP_VALUSE,userSp.getString(Constant.DEVICEPASSWORD_SP_VALUSE,"111111"));
                        int limitDay = timeLimit(timeLimit, localTime);
                        userEditor.putInt(Constant.LIMITDAY_SP_VALUSE,limitDay);
                        userEditor.commit();
                        if ((limitDay >= 7) && userType.equals("1")) {
                            //距离欠费时间大于7天，正常使用
                            if (isRegistered) {
                                onLogin.onSuccess(loginBean);
                                onLogin.onLoaded();
                            } else {
                                onLogin.onFailed(context.getResources().getString(R.string.not_registered));
                                onLogin.onLoaded();
                            }
                        } else if ((limitDay < 7 && limitDay > 0) && userType.equals("1")) {
                            //距离欠费时间不足7天时提醒
                            loginBean.setLimitDay(String.valueOf(timeLimit(timeLimit, localTime)));
                            if (isRegistered) {
                                onLogin.onSuccess(loginBean);
                                onLogin.onLoaded();
                            } else {
                                onLogin.onFailed(context.getResources().getString(R.string.not_registered));
                                onLogin.onLoaded();
                            }
                        } else if (userType.equals("0")) {
                            //试用期提醒
                            if ((limitDay <= 0)) {
                                //欠费
                                onLogin.onFailed(context.getResources().getString(R.string.use_time_out));
                                onLogin.onLoaded();
                            } else {
                                if (isRegistered) {
                                    Log.d("l","l1");
                                    onLogin.onSuccess(loginBean);
                                    onLogin.onLoaded();
                                } else {
                                    Log.d("l","l2");
                                    onLogin.onFailed(context.getResources().getString(R.string.not_registered));
                                    onLogin.onLoaded();
                                }
                            }
                        } else if ((limitDay <= 0) && userType.equals("1")) {
                            //欠费
                            onLogin.onFailed(context.getResources().getString(R.string.use_time_out));
                            onLogin.onLoaded();
                        }
                    }
                } catch (Exception e) {
                    onLogin.onLoaded();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                onLogin.onFailed(context.getResources().getString(R.string.net_failed));
                onLogin.onLoaded();
            }
        });
    }

    /**
     * 自动登录
     *
     * @param onLogin
     * @param context
     */
    public void login(final OnLogin onLogin, final Context context) {
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String localTime = sDateFormat.format(new java.util.Date());
            SharedPreferences userSp = BaseApplication.getContext().getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
            SharedPreferences.Editor userEditor = userSp.edit();
            int limitDay = timeLimit(userSp.getString(Constant.TIMELIMIT_SP_VALUSE, "null"), localTime);
            String userType = userSp.getString(Constant.USERTYPE_SP_VALUSE, "0");
            LoginBean loginBean = new LoginBean();
            loginBean.setRegistered(userSp.getBoolean(Constant.ISREGISTERED_SP_VALUSE, false));
            loginBean.setTimeLimit(userSp.getString(Constant.TIMELIMIT_SP_VALUSE, "null"));
            loginBean.setLocalTime(localTime);
            userEditor.putString(Constant.LOCALTIME_SP_VALUSE, localTime);
            userEditor.putInt(Constant.LIMITDAY_SP_VALUSE,limitDay);
            loginBean.setUseable(userSp.getInt(Constant.USEABLE_SP_VALUSE, 0));
            loginBean.setUserId(userSp.getString(Constant.USERID_SP_VALUSE, "null"));
            loginBean.setUserType(userType);
            userEditor.apply();
            if ((limitDay >= 7) && userType.equals("1")) {
                Log.d("day", "1");
                //距离欠费时间大于7天，正常使用
                onLogin.onSuccess(loginBean);
            } else if ((limitDay < 7 && limitDay > 0) && userType.equals("1")) {
                Log.d("day", "2");
                //距离欠费时间不足7天时提醒
                loginBean.setLimitDay(String.valueOf(limitDay));
                onLogin.onSuccess(loginBean);
            } else if (userType.equals("0")) {
                Log.d("day", "3");
                //试用期提醒
                if ((limitDay <= 0)) {
                    //欠费
                    onLogin.onFailed(context.getResources().getString(R.string.use_time_out));
                } else {
                    onLogin.onSuccess(loginBean);
                }
            } else if ((limitDay <= 0) && userType.equals("1")) {
                Log.d("day", "4");
                //欠费
                onLogin.onFailed(context.getResources().getString(R.string.use_time_out));
            }else{
                Log.d("day", "5");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("day", "e");
        }
    }

    /**
     * 距离欠费时间
     *
     * @param limitTime 欠费时间
     * @param localTime 服务器时间
     * @return
     */
    public int timeLimit(String limitTime, String localTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = df.parse(limitTime);
        Date d2 = df.parse(localTime);
        //Date   d2 = new   Date(System.currentTimeMillis());//你也可以获取当前时间
        long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        System.out.println("" + days + "天" + hours + "小时" + minutes + "分");
        Log.d("aaa", "" + Long.valueOf(days).intValue());
        return Long.valueOf(days).intValue();
    }
}
