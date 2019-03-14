/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.slidingtutorial.Direction;
import com.cleveroad.slidingtutorial.IndicatorOptions;
import com.cleveroad.slidingtutorial.PageOptions;
import com.cleveroad.slidingtutorial.TransformItem;
import com.cleveroad.slidingtutorial.TutorialFragment;
import com.cleveroad.slidingtutorial.TutorialOptions;
import com.cleveroad.slidingtutorial.TutorialPageOptionsProvider;
import com.githang.statusbar.StatusBarCompat;
import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseActivity;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.model.LoginModel;
import com.guanshinfo.win7.stand_alone_control.ui.SystemBarTintManager;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.TimeErrorDialog;
import com.guanshinfo.win7.stand_alone_control.util.DeviceUuidFactory;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SplashscreenActivity extends BaseActivity implements View.OnClickListener {

    private static final int TOTAL_PAGES = 3;
    private static final int ACTUAL_PAGES_COUNT = 3;
    private int[] mPagesColors;
    private SharedPreferences userSp;
    private SharedPreferences.Editor editor;
    private Snackbar snackbar;
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE};//申请的权限
    private SharedPreferences deviceSp;
    private SharedPreferences.Editor deviceEditor;
    private TimeErrorDialog timeErrorDialog = new TimeErrorDialog(this, this);

    @Override
    protected void themeColor(int color) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_splashscreen);
            deviceSp = getSharedPreferences(Constant.DEVICE_SP_TABLE, MODE_PRIVATE);
            userSp = getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
            deviceEditor = deviceSp.edit();
            editor = userSp.edit();
            checkPermissions();
            getDeviceInfo();
            init();
            String localTime = userSp.getString(Constant.LOCALTIME_SP_VALUSE, "null");
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updateTime = sDateFormat.format(new java.util.Date());
            if (localTime.equals("null")) {
                //如果为null则为还没经过登录，经过登录后赋予初始时间
                if (userSp.getBoolean(Constant.ISFIRST_SP_VALUSE, true)) {
                    // TODO: 2017/7/20 1.检测是否开启usb调试
                    if (!enableAdb()) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), getResources().getString(R.string.adb_unenable), Snackbar.LENGTH_LONG)
                                .setAction(getResources().getString(R.string.enable), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent().setClassName("com.android.settings", "com.android.settings.DevelopmentSettings"));
                                    }
                                });
                        snackbar.show();
                    }
                    replaceTutorialFragment();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
            } else {
                //如果有初始时间，则以初始时间开始计费，时间也通过每次登录进行更新，如果更新时间小于上次更新时间则发生错误
                if (timeOk(updateTime, localTime) < 0) {
                    //时间发生异常
                    timeErrorDialog.showDialog();
                    //startActivity(new Intent(this, LoginActivity.class));
                } else {
                    editor.putString(Constant.LOCALTIME_SP_VALUSE, updateTime);
                    editor.commit();
                    if (userSp.getBoolean(Constant.ISFIRST_SP_VALUSE, true)) {
                        // TODO: 2017/7/20 1.检测是否开启usb调试
                        if (!enableAdb()) {
                            Snackbar snackbar = Snackbar.make(findViewById(R.id.container), getResources().getString(R.string.adb_unenable), Snackbar.LENGTH_LONG)
                                    .setAction(getResources().getString(R.string.enable), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent().setClassName("com.android.settings", "com.android.settings.DevelopmentSettings"));
                                        }
                                    });
                            snackbar.show();
                        }
                        replaceTutorialFragment();
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    }
                }
            }
            editor.putBoolean(Constant.ISFIRST_SP_VALUSE, false);
            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 时间校准
     *
     * @param limitTime
     * @param localTime
     * @return
     * @throws ParseException
     */
    public long timeOk(String limitTime, String localTime) throws ParseException {
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
        return diff;
    }


    /**
     * 获取设备ID
     */
    private void getDeviceInfo() {
     /*   final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();*/
        String uniqueId = DeviceUuidFactory.getUniquePsuedoID();
        deviceEditor.putString(Constant.DEVICEID_SP_VALUSE, uniqueId);
        deviceEditor.putString(Constant.DEVICENAME_SP_VALUSE, Build.MODEL);
        deviceEditor.putString(Constant.DEVICEVER_SP_VALUSE, Build.VERSION.RELEASE);
        deviceEditor.commit();
    }

    /**
     * 检查权限
     */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                startRequestPermission();
            } else {
                getDeviceInfo();
            }
        }
    }

    /**
     * 开始提交请求权限
     */
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        goToAppSetting();
                    } else
                        finish();
                } else {
                    getDeviceInfo();
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    goToAppSetting();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void init() {
        mPagesColors = new int[]{
                ContextCompat.getColor(this, android.R.color.holo_blue_dark),
                ContextCompat.getColor(this, android.R.color.holo_blue_dark),
                ContextCompat.getColor(this, android.R.color.holo_blue_dark),
        };
        View view = LayoutInflater.from(this).inflate(R.layout.fragment_page_third,null);
        TextView textView = (TextView) view.findViewById(R.id.into_tv);
        textView.setText("sss");
    }

    public void replaceTutorialFragment() {
        final IndicatorOptions indicatorOptions = IndicatorOptions.newBuilder(this)
                .build();
        final TutorialOptions tutorialOptions = TutorialFragment.newTutorialOptionsBuilder(this)
                .setUseAutoRemoveTutorialFragment(true)
                .setUseInfiniteScroll(true)
                .setPagesColors(mPagesColors)
                .setPagesCount(TOTAL_PAGES)
                .setIndicatorOptions(indicatorOptions)
                .setTutorialPageProvider(new TutorialPagesProvider())
                .setOnSkipClickListener(new OnSkipClickListener(this, this))
                .build();
        final TutorialFragment tutorialFragment = TutorialFragment.newInstance(tutorialOptions);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, tutorialFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 是否打开USB调试
     *
     * @return true 打开，false 未打开
     */
    private boolean enableAdb() {
        return (Settings.Secure.getInt(getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
    }

    private static final class TutorialPagesProvider implements TutorialPageOptionsProvider {

        private TransformItem transformItem;

        @NonNull
        @Override
        public PageOptions provide(int position) {
            @LayoutRes int pageLayoutResId;
            TransformItem[] tutorialItems;
            position %= ACTUAL_PAGES_COUNT;
            switch (position) {
                case 0: {
                    pageLayoutResId = R.layout.fragment_page_first;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.20f),
                            TransformItem.create(R.id.ivSecondImage, Direction.RIGHT_TO_LEFT, 0.06f),
                            TransformItem.create(R.id.ivThirdImage, Direction.LEFT_TO_RIGHT, 0.08f),
                            TransformItem.create(R.id.ivSixthImage, Direction.RIGHT_TO_LEFT, 0.09f),
                            TransformItem.create(R.id.ivEighthImage, Direction.RIGHT_TO_LEFT, 0.07f)
                    };
                    break;
                }
                case 1: {
                    pageLayoutResId = R.layout.fragment_page_second;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivThirdImage, Direction.RIGHT_TO_LEFT, 0.08f),
                            TransformItem.create(R.id.ivFourthImage, Direction.LEFT_TO_RIGHT, 0.1f),
                            TransformItem.create(R.id.ivFifthImage, Direction.LEFT_TO_RIGHT, 0.03f),
                            TransformItem.create(R.id.ivEighthImage, Direction.LEFT_TO_RIGHT, 0.09f),
                            TransformItem.create(R.id.ivSeventhImage, Direction.LEFT_TO_RIGHT, 0.14f)
                    };
                    break;
                }
                case 2: {
                    pageLayoutResId = R.layout.fragment_page_third;
                    tutorialItems = new TransformItem[]{
                            TransformItem.create(R.id.ivFirstImage, Direction.LEFT_TO_RIGHT, 0.20f),
                    };
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown position: " + position);
                }
            }
            return PageOptions.create(pageLayoutResId, position, tutorialItems);
        }
    }

    private static final class OnSkipClickListener implements View.OnClickListener {

        @NonNull
        private final Context mContext;
        private Activity activity;

        OnSkipClickListener(@NonNull Context context, Activity activity) {
            mContext = context.getApplicationContext();
            this.activity = activity;
        }

        @Override
        public void onClick(View v) {
            // TODO: 2017/7/20 2.检测版本是否为5.0及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContext.startActivity(new Intent(activity, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                activity.finish();
            } else {
                Toast.makeText(activity, activity.getResources().getString(R.string.sdk_low), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
