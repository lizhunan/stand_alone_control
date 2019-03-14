/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.util.Log;

import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.bean.PolicyInfo;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseHelper;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * 策略Model（该处可以不用该架构）
 */

public class PolicyModel {

    private DatabaseHelper db = new DatabaseHelper(BaseApplication.getContext());
    private PackageManager pm = BaseApplication.getContext().getPackageManager();
    private ApplicationInfo applicationInfo;

    public void data(final OnGetPolicyInfo onGetPolicyInfo) throws Exception {
        Log.d("PolicyModel", "onstart");
        List<String> poStr = new ArrayList<>();
        List<AppInfo> appInfos = new ArrayList<>();
        List<PolicyInfo> policyInfo = new ArrayList<>();
        Cursor policyC = DatabaseUtils.getPolicy(db);
        if (policyC != null && policyC.moveToFirst()) {
            do {
                poStr.add(policyC.getString(policyC.getColumnIndex("policyName")));
            } while (policyC.moveToNext());
        }
        for (int i = 0; i < poStr.size(); i++) {
            Log.d("PolicyModel", poStr.get(i));
            PolicyInfo p = new PolicyInfo();
            Cursor pkgC = DatabaseUtils.getPkg(db, poStr.get(i));
            p.setPolicy(poStr.get(i));
            if (pkgC != null && pkgC.moveToFirst()) {
                do {
                    AppInfo appinfo = new AppInfo();
                    Log.d("PolicyModel", i + "::" + pkgC.getString(pkgC.getColumnIndex("pkgName")));
                    appinfo.setPkgName(pkgC.getString(pkgC.getColumnIndex("pkgName")));
                    appInfos.add(appinfo);

                } while (pkgC.moveToNext());
            }
            for (int j = 0; j < appInfos.size(); j++) {
                Log.d("PolicyModel", "pppkg" + appInfos.size());
                applicationInfo = pm.getApplicationInfo(appInfos.get(j).getPkgName(), PackageManager.GET_UNINSTALLED_PACKAGES);
                appInfos.get(j).setAppLabel((String) pm.getApplicationLabel(applicationInfo));
            }
            p.setAppInfos(appInfos);
            policyInfo.add(i, p);
            Log.d("PolicyModel", "pkg" + policyInfo.get(i).getAppInfos());
            appInfos = new ArrayList<>();
        }
        onGetPolicyInfo.onSuccess(policyInfo);
    }
}
