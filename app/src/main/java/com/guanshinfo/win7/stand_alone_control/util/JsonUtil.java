/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/14.
 * json 工具类
 */
@Deprecated
public class JsonUtil<T> {

    private SharedPreferences poSh = BaseApplication.getContext().getSharedPreferences(Constant.POLICY_SP_TABLE, Context.MODE_PRIVATE);
    private SharedPreferences.Editor poEd = poSh.edit();

    public void creJson(String str) {

    }

    public List<T> getListFromJson(String json) throws JSONException {
        List<T> list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add((T) jsonArray.getString(i));
        }
        return list;
    }

    /**
     * 添加策略
     * @param policy 策略名
     * @param pgms app包名
     */
    public void savePolicyList(String policy, List<String> pgms) throws JSONException {

        String po = poSh.getString("", "null");
        if (po.equals("null")) {
            JSONArray jsonArray = new JSONArray();
            JSONArray creArray = new JSONArray();
            JSONObject creObject = new JSONObject();
            JSONArray pkgArray = new JSONArray();
            creObject.put("policy", policy);
            for (int i = 0; i < pgms.size(); i++) {
                pkgArray.put(pgms.get(i));
            }
            creArray.put(0, creObject);
            creArray.put(1, pkgArray);
            jsonArray.put(0, creArray);
            poEd.putString(Constant.POLICY_SP_VALUSE, jsonArray.toString());
        } else {
            JSONArray jsonArray = new JSONArray(po);
            JSONArray creArray = new JSONArray();
            JSONObject creObject = new JSONObject();
            JSONArray pkgArray = new JSONArray();
            creObject.put("policy", policy);
            for (int i = 0; i < pgms.size(); i++) {
                pkgArray.put(pgms.get(i));
            }
            creArray.put(0, creObject);
            creArray.put(1, pkgArray);
            jsonArray.put(jsonArray.length(), creArray);
            poEd.putString(Constant.POLICY_SP_VALUSE,jsonArray.toString());
        }
    }

}
