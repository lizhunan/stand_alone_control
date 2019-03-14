/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseActivity;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.bean.LoginBean;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.EntryDialog;
import com.guanshinfo.win7.stand_alone_control.view.fragment.AppFragment;
import com.guanshinfo.win7.stand_alone_control.view.fragment.ControlAppFragment;
import com.guanshinfo.win7.stand_alone_control.view.fragment.PolicyFragment;
import com.guanshinfo.win7.stand_alone_control.view.fragment.SystemAppFragment;

import static com.guanshinfo.win7.stand_alone_control.base.BaseApplication.isFirstDevicePw;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private TextView toolbarTv;
    private BottomNavigationView navigation;
    private AppFragment appFragment;
    private ControlAppFragment controlAppFragment;
    private PolicyFragment policyFragment;
    private SystemAppFragment systemAppFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int color;
    private SharedPreferences userSp;
    private SharedPreferences.Editor userEditor;
    private EntryDialog entryDialog = new EntryDialog(this, this);

    @Override
    protected void themeColor(int color) {
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFirstDevicePw) {

        } else {
            entryDialog.showDialog();
            isFirstDevicePw = true;
        }
    }

    private void init() {
        userSp = getSharedPreferences(Constant.USER_SP_TABLE, MODE_PRIVATE);
        userEditor = userSp.edit();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTv = (TextView) findViewById(R.id.tool_tv);
        toolbar.setBackgroundColor(color);
        fragmentManager = getSupportFragmentManager();
        defultFragment();
        toolbar.setNavigationIcon(R.drawable.ic_account_circle_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
        //判断是否为试用期用户如果不是开始计费
        if (userSp.getString(Constant.USERTYPE_SP_VALUSE, "0").equals("0")) {
            Snackbar.make(navigation, getResources().getString(R.string.tryuser), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
        } else {
            LoginBean loginBean = (LoginBean) getIntent().getSerializableExtra("loginBean");
            try {
                if (!loginBean.getLimitDay().equals("")) {
                    Snackbar.make(navigation, getResources().getString(R.string.you_has_only) + loginBean.getLimitDay() + getResources().getString(R.string.day_please_recharge),
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(getResources().getString(R.string.cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                }
            } catch (Exception e) {

            }
        }
        //toolbar.inflateMenu(R.menu.storeactivity_toolbar_menu);
        //toolbar.setOnMenuItemClickListener(this);
    }

    /**
     * 默认打开的fragment
     */
    private void defultFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        toolbarTv.setText(getResources().getString(R.string.userapp) + getResources().getString(R.string.longclick_info));
        appFragment = AppFragment.newInstance();
        fragmentTransaction.replace(R.id.content, appFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_userapp:
                toolbarTv.setText(getResources().getString(R.string.userapp) + getResources().getString(R.string.longclick_info));
                appFragment = AppFragment.newInstance();
                fragmentTransaction.replace(R.id.content, appFragment);
                fragmentTransaction.commit();
                return true;
            case R.id.navigation_systemapp:
                toolbarTv.setText(getResources().getString(R.string.systemapp) + getResources().getString(R.string.longclick_info));
                systemAppFragment = SystemAppFragment.newInstance();
                fragmentTransaction.replace(R.id.content, systemAppFragment);
                fragmentTransaction.commit();
                return true;
            case R.id.navigation_controlapp:
                toolbarTv.setText(getResources().getString(R.string.controlapp) + getResources().getString(R.string.longclick2_info));
                controlAppFragment = ControlAppFragment.newInstance();
                fragmentTransaction.replace(R.id.content, controlAppFragment);
                fragmentTransaction.commit();
                return true;
            case R.id.navigation_policy:
                toolbarTv.setText(getResources().getString(R.string.policy) + getResources().getString(R.string.longclick3_info));
                policyFragment = PolicyFragment.newInstance();
                fragmentTransaction.replace(R.id.content, policyFragment);
                fragmentTransaction.commit();
                return true;
        }
        return false;
    }

}
