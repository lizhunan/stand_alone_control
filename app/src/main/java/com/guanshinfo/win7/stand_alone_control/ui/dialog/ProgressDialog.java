/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.guanshinfo.win7.stand_alone_control.R;

/**
 * Created by guanshinfo-lizhunan on 2017/7/18.
 * 加载控件dialog
 */

public class ProgressDialog {

    private android.app.ProgressDialog dialog;

    public void showDialog(Context context,String title,String msg) {
        dialog = android.app.ProgressDialog.show(context, title, msg);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
    }


    public void hideDialog(){
        dialog.dismiss();
    }
}
