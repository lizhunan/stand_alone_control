/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseActivity;
import com.guanshinfo.win7.stand_alone_control.presenter.RegistPresenter;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.ProgressDialog;
import com.guanshinfo.win7.stand_alone_control.util.FormatCheckUtils;

import java.util.ArrayList;
import java.util.List;

public class RegistActivity extends BaseActivity implements IRegist, Toolbar.OnMenuItemClickListener {

    private EditText usernameEt, passwordEt, rePasswordEt, phoneEt, emailEt, passwordAEt, inviteCodeEt;
    private Spinner passwordASp;
    private TextView errorInfoTv, usernameChTv, passwordChTv, rePasswordChTv, phoneChTv, passwordAChTv, emailChTv;
    private Toolbar toolbar;
    private TextView toolbarTv;
    private List<String> list;
    private int color;
    private boolean usernameInputError = true, passwordInputError = true, rePasswordInputError = true, phoneInputError = true, passwordAInputError = true, emailInputError = false;
    private RegistPresenter registPresenter = new RegistPresenter(this);
    private ProgressDialog progressDialog = new ProgressDialog();

    @Override
    protected void themeColor(int color) {
        this.color = color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        init();
    }

    private void init() {
        list = new ArrayList<>();
        list.add("您的母校");
        list.add("您父亲的生日");
        list.add("您母亲的生日");
        list.add("您父亲的姓名");
        list.add("您母亲的姓名");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTv = (TextView) findViewById(R.id.tool_tv);
        usernameEt = (EditText) findViewById(R.id.username_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        phoneEt = (EditText) findViewById(R.id.phone_et);
        rePasswordEt = (EditText) findViewById(R.id.re_password_et);
        errorInfoTv = (TextView) findViewById(R.id.error_info_tv);
        usernameChTv = (TextView) findViewById(R.id.username_ch_tv);
        passwordChTv = (TextView) findViewById(R.id.password_ch_tv);
        phoneChTv = (TextView) findViewById(R.id.phone_ch_tv);
        rePasswordChTv = (TextView) findViewById(R.id.re_password_ch_tv);
        passwordAChTv = (TextView) findViewById(R.id.password_a_ch_tv);
        emailChTv = (TextView) findViewById(R.id.email_ch_tv);
        passwordASp = (Spinner) findViewById(R.id.password_a_sp);
        emailEt = (EditText) findViewById(R.id.email_et);
        passwordAEt = (EditText) findViewById(R.id.password_a_et);
        inviteCodeEt = (EditText) findViewById(R.id.invite_code_et);
        toolbar.setBackgroundColor(color);
        toolbar.inflateMenu(R.menu.menu_regist);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setTitle("");
        toolbarTv.setText(getResources().getString(R.string.regist));
        toolbarTv.setGravity(Gravity.CENTER);
        passwordASp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list));
        editWatch();
    }

    private void editWatch() {
        usernameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (usernameEt.getText().toString().equals("")) {
                    usernameChTv.setText(getResources().getString(R.string.username_not_null));
                    usernameInputError = true;
                } else {
                    if (usernameEt.getText().length() < 6 || usernameEt.getText().length() >= 32) {
                        usernameChTv.setText(getResources().getString(R.string.username_tooshort));
                        usernameInputError = true;
                    } else {
                        if (!FormatCheckUtils.isUsername(usernameEt.getText().toString())) {
                            usernameChTv.setText(getResources().getString(R.string.username_formaterror));
                            usernameInputError = true;
                        } else {
                            usernameChTv.setText("");
                            usernameInputError = false;
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rePasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (rePasswordEt.getText().toString().equals("")) {
                    rePasswordChTv.setText(getResources().getString(R.string.password_not_null));
                    rePasswordInputError = true;
                } else {
                    if (!rePasswordEt.getText().toString().equals(passwordEt.getText().toString())) {
                        rePasswordChTv.setText(getResources().getString(R.string.repassword_error));
                        rePasswordInputError = true;
                    } else {
                        rePasswordChTv.setText("");
                        rePasswordInputError = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (passwordEt.getText().toString().equals("")) {
                    passwordChTv.setText(getResources().getString(R.string.password_not_null));
                    passwordInputError = true;
                } else {
                    if (passwordEt.getText().length() < 6 || passwordEt.getText().length() >= 32) {
                        passwordChTv.setText(getResources().getString(R.string.password_tooshort));
                        passwordInputError = true;
                    } else {
                        passwordChTv.setText("");
                        passwordInputError = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneEt.getText().toString().equals("")) {
                    phoneChTv.setText(getResources().getString(R.string.phone_not_null));
                    phoneInputError = true;
                } else {
                    if (!FormatCheckUtils.isChinaPhoneLegal(phoneEt.getText().toString())) {
                        phoneChTv.setText(getResources().getString(R.string.phone_error));
                        phoneInputError = true;
                    } else {
                        phoneChTv.setText("");
                        phoneInputError = false;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        passwordAEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!passwordAEt.getText().toString().equals("")) {
                    passwordAChTv.setText("");
                    passwordAInputError = false;
                } else {
                    passwordAChTv.setText(getResources().getString(R.string.password_a_not_null));
                    passwordAInputError = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInputError = false;
                if (!emailEt.getText().toString().equals("")) {
                    if (FormatCheckUtils.isEmail(emailEt.getText().toString())) {
                        emailInputError = false;
                        emailChTv.setText("");
                    } else {
                        emailChTv.setText(getResources().getString(R.string.email_error));
                        emailInputError = true;
                    }
                } else {
                    emailInputError = false;
                    emailChTv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regist, menu);
        return true;
    }

    @Override
    public void onSuccess() {
        startActivity(new Intent(RegistActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onFiled(String s) {
        Toast.makeText(RegistActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoading() {
        progressDialog.showDialog(RegistActivity.this, getResources().getString(R.string.regist), getResources().getString(R.string.registing));
    }

    @Override
    public void onLoaded() {
        progressDialog.hideDialog();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int menuItemId = item.getItemId();
        Log.d("StoreActivity", "onMenuItemClick");
        if (menuItemId == R.id.regist_m) {
            Log.d("ddddd", "" + usernameInputError + passwordInputError + phoneInputError + passwordAInputError + rePasswordInputError + emailInputError);
            if (!usernameInputError && !passwordInputError && !phoneInputError && !passwordAInputError && !rePasswordInputError && !emailInputError) {
                registPresenter.regist(RegistActivity.this, usernameEt.getText().toString(),
                        passwordEt.getText().toString(), phoneEt.getText().toString(),
                        passwordASp.getSelectedItem().toString(), passwordAEt.getText().toString(), inviteCodeEt.getText().toString(), emailEt.getText().toString());
            } else {
                Toast.makeText(RegistActivity.this, getResources().getString(R.string.check_regist), Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

}
