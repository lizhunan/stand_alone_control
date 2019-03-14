/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.adapter.PolicyListAdapter;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseHelper;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseUtils;
import com.guanshinfo.win7.stand_alone_control.view.fragment.PolicyFragment;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/18.
 * 删除策略dialog
 */

public class DeletePolicyDialog {

    private Context context;
    private DatabaseHelper db = new DatabaseHelper(BaseApplication.getContext());

    public DeletePolicyDialog(Context context) {
        this.context = context;
    }

    /**
     * 显示删除信息dialog
     *
     * @param poName   当前策略
     * @param appInfos 当前应用组
     */
    public void showDialog(final String poName, final List<AppInfo> appInfos, final PolicyFragment policyFragment) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.delete_policy));
            builder.setMessage(context.getResources().getString(R.string.is_delete)+poName);
            builder.setIcon(R.mipmap.logo_bee);
            builder.setPositiveButton(context.getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //判断策略下是否有信息，如果有先删除策略下信息
                    if (appInfos.size() != 0) {

                        DatabaseUtils.deletePkgs(db, appInfos);
                        for (int i = 0; i < appInfos.size(); i++) {
                            BaseApplication.mDPM.setApplicationHidden(BaseApplication.mComponentName, appInfos.get(i).getPkgName(), false);
                        }
                    }
                    DatabaseUtils.deletePolicy(db, poName);
                    policyFragment.refreshUI();
                }
            });
            builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
