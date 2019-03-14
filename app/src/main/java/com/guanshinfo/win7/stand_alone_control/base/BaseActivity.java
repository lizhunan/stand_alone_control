/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.githang.statusbar.StatusBarCompat;

/**
 * Created by guanshinfo-lizhunan on 2017/7/10.
 * activity基础类
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected abstract void themeColor(int color);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, android.R.color.holo_blue_dark));
        themeColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));

    }

}
