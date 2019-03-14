/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseHelper;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseUtils;
import com.guanshinfo.win7.stand_alone_control.ui.indexui.PinYinUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/12.
 * 获取app信息（第三方app，系统app,被管控app）
 */

public class AppModel {

    private PackageManager pm;
    private DatabaseHelper db = new DatabaseHelper(BaseApplication.getContext());

    /**
     * 构造一个appinfo对象
     *
     * @param packageInfo 原始app信息
     * @return Appinfo对象
     */
    private AppInfo getAppInfo(PackageInfo packageInfo) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppLabel((String) packageInfo.applicationInfo.loadLabel(pm));
        appInfo.setPkgName(packageInfo.applicationInfo.packageName);
        appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
        appInfo.setDes((String) packageInfo.applicationInfo.loadDescription(pm));
        appInfo.setVersionName(packageInfo.versionName);
        appInfo.setPinyin(PinYinUtils.getPinYin((String) packageInfo.applicationInfo.loadLabel(pm)));
        return appInfo;
    }

    public void data(final int i, final OnGetAppInfo onGetAppInfo) {
        final List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                onGetAppInfo.onLoading();
                pm = BaseApplication.getContext().getPackageManager();
                List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
                Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序
                switch (i) {
                    case 0:
                        appInfos.clear();
                        for (PackageInfo pkg : packageInfos) {
                            if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                                appInfos.add(getAppInfo(pkg));
                            }
                        }
                        onGetAppInfo.onLoaded();
                        onGetAppInfo.onSuccess(appInfos);
                        break;
                    case 1:
                        appInfos.clear();
                        for (PackageInfo pkg : packageInfos) {
                            //非系统程序
                            if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                                if (pkg.packageName.equals("com.guanshinfo.win7.stand_alone_control")) {
                                    continue;
                                }
                                appInfos.add(getAppInfo(pkg));
                            } else if ((pkg.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {//本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
                                appInfos.add(getAppInfo(pkg));
                            } else {

                            }
                        }
                        onGetAppInfo.onLoaded();
                        onGetAppInfo.onSuccess(appInfos);
                        break;
                    case 2:
                        List<PackageInfo> hiddenApp = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);//查询所有app
                        appInfos.clear();
                        for (int j = 0; j < hiddenApp.size(); j++) {
                            if (BaseApplication.mDPM.isApplicationHidden(BaseApplication.mComponentName, hiddenApp.get(j).packageName)) {
                                appInfos.add(getAppInfo(hiddenApp.get(j)));
                            }
                        }
                        onGetAppInfo.onLoaded();
                        onGetAppInfo.onSuccess(appInfos);
                        break;
                }
            }
        }).start();
    }

}
