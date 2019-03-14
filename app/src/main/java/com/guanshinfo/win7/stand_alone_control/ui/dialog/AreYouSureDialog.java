/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Process;
import android.support.annotation.RequiresApi;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseHelper;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/21.
 * 提示是否继续进行的dialog
 */

public class AreYouSureDialog {

    private DatabaseHelper db;

    public void showDialog(final Activity activity, final Context context, final String title, String msg) {
        db = new DatabaseHelper(context);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.mipmap.logo_bee);
        builder.setPositiveButton(context.getResources().getString(R.string.entry), new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (title.equals(context.getResources().getString(R.string.cancel_activate))) {
                    //取消激活时一定要把管控的app放开
                    List<String> poStr = new ArrayList<>();
                    Cursor c = DatabaseUtils.getPolicy(db);
                    if (c != null && c.moveToFirst()) {
                        do {
                            poStr.add(c.getString(c.getColumnIndex("policyName")));
                        } while (c.moveToNext());
                    }
                    for (int i = 0; i < poStr.size(); i++) {
                        Cursor cursor = DatabaseUtils.getPkg(db, poStr.get(i));
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                String s = cursor.getString(cursor.getColumnIndex("pkgName"));
                                BaseApplication.mDPM.setApplicationHidden(BaseApplication.mComponentName, s, false);
                                DatabaseUtils.deletePkg(db, s);
                            } while (cursor.moveToNext());
                        }
                        DatabaseUtils.deletePolicy(db, poStr.get(i));
                    }
                    BaseApplication.mDPM.clearDeviceOwnerApp("com.guanshinfo.win7.stand_alone_control");
                    Process.killProcess(Process.myPid());
                }
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
