/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.dialog;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.adapter.CrePolicyListAdapter;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseHelper;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseUtils;
import com.guanshinfo.win7.stand_alone_control.ui.DividerItemDecoration;
import com.guanshinfo.win7.stand_alone_control.view.fragment.IGetData;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/14.
 * 创建策略组Dialog
 */

public class CrePolicyGroupDialog {

    private Context context;
    private EditText editText;
    private TextView textView;
    private Spinner spinner;
    private ArrayAdapter<String> policyListAdapter;
    private String policyName;
    private boolean isCre = false;
    private SharedPreferences poSh;
    private SharedPreferences.Editor poEd;
    private DatabaseHelper db = new DatabaseHelper(BaseApplication.getContext());


    public CrePolicyGroupDialog(Context context) {
        this.context = context;
    }

    public String showDialog(final List<String> policyNames, final List<AppInfo> selectData, final IGetData iGetData) {

        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.add_policy_dialog, null);
            editText = (EditText) view.findViewById(R.id.policy_et);
            textView = (TextView) view.findViewById(R.id.creatnew_tv);
            spinner = (Spinner) view.findViewById(R.id.spinner);
            textView.setText(context.getResources().getString(R.string.cre_policy));
            policyListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, policyNames);
            policyListAdapter.setDropDownViewResource(R.layout.policy_drop_down_item);
            spinner.setAdapter(policyListAdapter);

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.add_policy));
            builder.setMessage(context.getResources().getString(R.string.add_policy_msg));
            builder.setView(view);
            builder.setIcon(R.mipmap.logo_bee);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isCre) {
                        isCre = true;
                        textView.setText(context.getResources().getString(R.string.cancel_cre));
                        editText.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                    } else {
                        isCre = false;
                        textView.setText(context.getResources().getString(R.string.cre_policy));
                        editText.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                    }
                }
            });
            builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton(context.getResources().getString(R.string.entry), new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < policyNames.size(); i++) {
                        if (policyNames.get(i).equals(editText.getText().toString())) {
                            Toast.makeText(context, context.getResources().getString(R.string.policy_not_respect), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (!editText.getText().toString().equals("")) {
                        policyName = editText.getText().toString();
                        DatabaseUtils.insertPolicy(db, policyName);
                    } else {
                        try {
                            policyName = spinner.getSelectedItem().toString();
                        } catch (NullPointerException e) {
                            Toast.makeText(context, context.getResources().getString(R.string.faild_chose_policy), Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (policyName == null) {
                        Toast.makeText(context, context.getResources().getString(R.string.faild_chose_policy), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        int poInt = DatabaseUtils.GetPolicyID(db, policyName);
                        Log.d("aaaa", "a:" + poInt);
                        for (int i = 0; i < selectData.size(); i++) {
                            Log.d("aaaa", "a:" + selectData.get(i).getAppLabel());
                            BaseApplication.mDPM.setApplicationHidden(BaseApplication.mComponentName, selectData.get(i).getPkgName(), true);
                        }
                        DatabaseUtils.insertPkgs(db, selectData, poInt);
                    }
                    iGetData.refreshUI();
                }
            });
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return policyName;
    }
}
