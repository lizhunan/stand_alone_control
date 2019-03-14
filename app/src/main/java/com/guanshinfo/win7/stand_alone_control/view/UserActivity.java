/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseActivity;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.presenter.ChangePwPresenter;
import com.guanshinfo.win7.stand_alone_control.ui.CollapsingToolbarLayoutState;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.ActivateDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.AreYouSureDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.ChangePwDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.ProgressDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.UserIdeaDialog;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener, View.OnLongClickListener, IChange {

    private int color;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayoutState state;
    private View include;
    private TextView usernameTv, phoneTv, emailTv, activateState, userIdeaTv, deviceNameTv, deviceVerTv, deviceIdTv, cancelActivateSateTv;
    private TextView pwVisTv, registedTv, registcodeTv;
    private CircleImageView userImage;
    private FloatingActionButton editerFab;
    private RelativeLayout userback;
    private AreYouSureDialog dialog = new AreYouSureDialog();
    private UserIdeaDialog userIdeaDialog = new UserIdeaDialog();
    private SharedPreferences deviceSp;
    private SharedPreferences userSp;
    private SharedPreferences.Editor userEditor;
    private int RESULT_LOAD_IMAGE = 1;
    private ProgressDialog progressDialog = new ProgressDialog();
    private ChangePwPresenter changePwPresenter = new ChangePwPresenter(this);
    private ChangePwDialog changePwDialog = new ChangePwDialog(this, this);
    private ActivateDialog activateDialog = new ActivateDialog(this, this);

    @Override
    protected void themeColor(int color) {
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        deviceSp = getSharedPreferences(Constant.DEVICE_SP_TABLE, Context.MODE_PRIVATE);
        userSp = getSharedPreferences(Constant.USER_SP_TABLE, Context.MODE_PRIVATE);
        userEditor = userSp.edit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        userback = (RelativeLayout) findViewById(R.id.userback);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        userImage = (CircleImageView) findViewById(R.id.profile_image);
        editerFab = (FloatingActionButton) findViewById(R.id.fab);
        init();
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.userinfo));
        String picturePath = userSp.getString(Constant.USERBACKGROUND_SP_VALUSE, "");
        if (!picturePath.equals("")) {
            userback.setBackground(new BitmapDrawable(BitmapFactory.decodeFile(picturePath)));
        }
        toolbar.setBackgroundColor(color);
        userback.setOnLongClickListener(this);
        appBarLayout.addOnOffsetChangedListener(this);
        editerFab.setOnClickListener(this);
        if (userSp.getBoolean(Constant.ISCHANGEDEVICEPW_SP_VALUSE, false)) {
            Log.d("aad", "s" + userSp.getString(Constant.DEVICEID_SP_VALUSE, ""));
            changePwPresenter.change(this, userSp.getString(Constant.USERID_SP_VALUSE, ""),
                    deviceSp.getString(Constant.DEVICEID_SP_VALUSE, ""), userSp.getString(Constant.DEVICEPASSWORD_SP_VALUSE, "111111"));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**
     * include控件内容初始化
     */
    private void init() {
        include = findViewById(R.id.content);
        pwVisTv = (TextView) include.findViewById(R.id.passwrod_isvisible_tv);
        usernameTv = (TextView) include.findViewById(R.id.username_tv);
        phoneTv = (TextView) include.findViewById(R.id.phone_tv);
        emailTv = (TextView) include.findViewById(R.id.email_tv);
        activateState = (TextView) include.findViewById(R.id.activate_state_tv);
        userIdeaTv = (TextView) include.findViewById(R.id.user_idea_tv);
        deviceNameTv = (TextView) include.findViewById(R.id.devicename_tv);
        deviceVerTv = (TextView) include.findViewById(R.id.deviceapi_tv);
        deviceIdTv = (TextView) include.findViewById(R.id.deviceid_tv);
        registedTv = (TextView) include.findViewById(R.id.registed_tv);
        registcodeTv = (TextView) include.findViewById(R.id.registcode_tv);
        cancelActivateSateTv = (TextView) include.findViewById(R.id.cancel_activate_tv);
        userIdeaTv.setOnClickListener(this);
        registedTv.setOnClickListener(this);
        activateState.setOnClickListener(this);
        cancelActivateSateTv.setOnClickListener(this);
        deviceIdTv.setText(deviceSp.getString(Constant.DEVICEID_SP_VALUSE, "null"));
        deviceNameTv.setText(deviceSp.getString(Constant.DEVICENAME_SP_VALUSE, "null"));
        deviceVerTv.setText("Android" + deviceSp.getString(Constant.DEVICEVER_SP_VALUSE, "null"));
        usernameTv.setText(userSp.getString(Constant.USERNAME_SP_VALUSE, "null"));
        phoneTv.setText(userSp.getString(Constant.USERPHONE_SP_VALUSE, "null"));
        if (userSp.getString(Constant.USERTYPE_SP_VALUSE, "0").equals("0")) {
            activateState.setText("试用期");
        } else {
            activateState.setText("剩余" + userSp.getInt(Constant.LIMITDAY_SP_VALUSE, -1) + "天");
        }
        emailTv.setText(userSp.getString(Constant.EMAIL_SP_VALUSE, ""));
        registcodeTv.setText(userSp.getString(Constant.REGISTEDCODE_SP_VALUSE, ""));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (state != CollapsingToolbarLayoutState.EXPANDED) {
                state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                toolbar.setAlpha(0);
                collapsingToolbarLayout.setTitle("");
            }
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                collapsingToolbarLayout.setTitle(getResources().getString(R.string.userinfo));
                toolbar.setAlpha(1);
                state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
            }
        } else {
            if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                }
                collapsingToolbarLayout.setTitle("");
                state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_idea_tv:
                userIdeaDialog.showDialog(this);
                break;
            case R.id.cancel_activate_tv:
                dialog.showDialog(this, this, getResources().getString(R.string.cancel_activate), getResources().getString(R.string.is_cancel_activate));
                break;
            case R.id.fab:
                changePwDialog.showDialog();
                break;
            case R.id.registed_tv:
                activateDialog.showDialog();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.userback:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            Log.d("picturePath", picturePath);
            cursor.close();
            userEditor.putString(Constant.USERBACKGROUND_SP_VALUSE, picturePath);
            userEditor.commit();
            userback.setBackground(new BitmapDrawable(BitmapFactory.decodeFile(picturePath)));
        }
    }

    @Override
    public void onSuccess() {
        userEditor.putBoolean(Constant.ISCHANGEDEVICEPW_SP_VALUSE, false);
        userEditor.commit();
        pwVisTv.setText(getResources().getString(R.string.pw_success));
    }

    @Override
    public void onFiled(String s) {
        pwVisTv.setTextColor(Color.RED);
        pwVisTv.setText(getResources().getString(R.string.new_pw_net_f));
        Toast.makeText(UserActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {
        progressDialog.showDialog(this, getResources().getString(R.string.changePw), getResources().getString(R.string.changeing));
    }

    @Override
    public void onLoaded() {
        progressDialog.hideDialog();
    }

    @Override
    public void onChange2Cache() {
        pwVisTv.setTextColor(Color.RED);
        pwVisTv.setText(getResources().getString(R.string.new_pw_net_f));
    }

}
