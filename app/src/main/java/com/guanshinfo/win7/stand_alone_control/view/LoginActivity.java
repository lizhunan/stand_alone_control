/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseActivity;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.bean.LoginBean;
import com.guanshinfo.win7.stand_alone_control.model.net.LoginModel;
import com.guanshinfo.win7.stand_alone_control.presenter.LoginPresenter;
import com.guanshinfo.win7.stand_alone_control.ui.EditTextClearTools;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.BindDeviceDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.ForgetPwDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.NoD_ownerDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.ProgressDialog;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;

import static com.guanshinfo.win7.stand_alone_control.base.BaseApplication.mDPM;

public class LoginActivity extends BaseActivity implements ILogin {

    private EditText usernameEt, passwordEt;
    private static RelativeLayout content;
    private ImageView m1, m2;
    private Button loginBtn, registBtn;
    private Toolbar toolbar;
    private TextView toolbarTv, forgetTv;
    private int color;
    private static Snackbar snackbar;
    private NoD_ownerDialog noD_ownerDialog = new NoD_ownerDialog(this, this);
    private BindDeviceDialog bindDeviceDialog = new BindDeviceDialog(this, this);
    private SharedPreferences userSp;
    private SharedPreferences deviceSp;
    private SharedPreferences.Editor userEditor;
    private SharedPreferences.Editor deviceEditor;
    private LoginPresenter loginPresenter = new LoginPresenter(this);
    private ProgressDialog progressDialog = new ProgressDialog();
    private ForgetPwDialog forgetPwDialog = new ForgetPwDialog(this);

    @Override
    protected void themeColor(int color) {
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userSp = getSharedPreferences(Constant.USER_SP_TABLE, MODE_PRIVATE);
        deviceSp = getSharedPreferences(Constant.DEVICE_SP_TABLE, MODE_PRIVATE);
        userEditor = userSp.edit();
        deviceEditor = deviceSp.edit();
        init();
        // TODO: 2017/7/20 3.是否开启device-owner权限
        if (mDPM.isDeviceOwnerApp("com.guanshinfo.win7.stand_alone_control")) {
            //是否自动登录
            if (userSp.getBoolean(Constant.ISAOTULOGIN_SP_VALUSE, false)) {
                if (deviceSp.getBoolean(Constant.ISNET_SP_VALUSE, false)) {
                    loginPresenter.login(LoginActivity.this, userSp.getString(Constant.USERNAME_SP_VALUSE, ""), userSp.getString(Constant.PASSWORD_SP_VALUSE, ""));
                } else {
                    loginPresenter.login(LoginActivity.this);
                }
            } else {

            }
        } else {
            noD_ownerDialog.showDialog();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        content = (RelativeLayout) findViewById(R.id.content);
        usernameEt = (EditText) findViewById(R.id.username_et);
        passwordEt = (EditText) findViewById(R.id.password);
        m1 = (ImageView) findViewById(R.id.del_username);
        m2 = (ImageView) findViewById(R.id.del_password);
        loginBtn = (Button) findViewById(R.id.loginButton);
        registBtn = (Button) findViewById(R.id.registButton);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTv = (TextView) findViewById(R.id.tool_tv);
        forgetTv = (TextView) findViewById(R.id.forgetpw_tv);
        toolbarTv.setText(getResources().getString(R.string.login));
        toolbarTv.setGravity(Gravity.CENTER);
        EditTextClearTools.addclerListener(usernameEt, m1);
        EditTextClearTools.addclerListener(passwordEt, m2);
        toolbar.setBackgroundColor(color);
        usernameEt.setText(userSp.getString(Constant.USERNAME_SP_VALUSE, ""));
        passwordEt.setText(userSp.getString(Constant.PASSWORD_SP_VALUSE, ""));
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameEt.getText().toString().equals("") && !passwordEt.getText().toString().equals("")) {
                    //loginPresenter.login(LoginActivity.this, usernameEt.getText().toString(), passwordEt.getText().toString());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.check_login), Toast.LENGTH_SHORT).show();
                }
            }
        });
        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                finish();
            }
        });
        forgetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPwDialog.showDialog();
            }
        });
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        userEditor.putBoolean(Constant.ISAOTULOGIN_SP_VALUSE, true);
        userEditor.putString(Constant.USERNAME_SP_VALUSE, usernameEt.getText().toString());
        userEditor.putString(Constant.PASSWORD_SP_VALUSE, passwordEt.getText().toString());
        userEditor.commit();
        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("loginBean", loginBean));
        finish();
    }

    @Override
    public void onFiled(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        if (s.equals(getResources().getString(R.string.use_time_out))) {
            startActivity(new Intent(LoginActivity.this, UserActivity.class));
            finish();
        } else if (s.equals(getResources().getString(R.string.not_registered))) {
            bindDeviceDialog.showDialog(userSp.getString(Constant.USERID_SP_VALUSE, "null"), deviceSp.getString(Constant.DEVICENAME_SP_VALUSE, "null"),
                    deviceSp.getString(Constant.DEVICEID_SP_VALUSE, "null"), userSp.getInt(Constant.USEABLE_SP_VALUSE, 0));
        }
    }

    @Override
    public void onLoading() {
        progressDialog.showDialog(this, getResources().getString(R.string.userlogin), getResources().getString(R.string.logning));
    }

    @Override
    public void onLoaded() {
        progressDialog.hideDialog();
    }


    public static class MyHandler extends Handler {

        private Context context;

        public MyHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    final String password = msg.obj.toString();
                    Snackbar.make(content, password, Snackbar.LENGTH_INDEFINITE).setAction(context.getResources().getString(R.string.copy), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager cmb = (ClipboardManager) BaseApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            cmb.setText(password);
                        }
                    }).show();
                    break;
            }
        }
    }
}
