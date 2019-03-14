/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.adapter.AppListAdapter;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseHelper;
import com.guanshinfo.win7.stand_alone_control.db.DatabaseUtils;
import com.guanshinfo.win7.stand_alone_control.presenter.AppPresenter;
import com.guanshinfo.win7.stand_alone_control.ui.DividerItemDecoration;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.CrePolicyGroupDialog;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.ProgressDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AppFragment extends Fragment implements IGetData, View.OnClickListener, AppListAdapter.OnItemLongClickListener, AppListAdapter.OnItemClickListener, ActionMode.Callback {

    private OnFragmentInteractionListener mListener;

    public static AppFragment INSTANCE;
    private AppPresenter appPresenter = new AppPresenter(this);
    private View view;
    private RecyclerView recyclerView;
    private AppListAdapter appListAdapter;
    private FloatingActionButton controlFab;
    private List<AppInfo> data = new ArrayList<>();
    private List<AppInfo> selectData = new ArrayList<>();
    private ActionMode actionMode;
    public Set<Integer> positionSet = new HashSet<>();
    private CrePolicyGroupDialog crePolicyGroupDialog;
    private SharedPreferences poSh = BaseApplication.getContext().getSharedPreferences(Constant.POLICY_SP_TABLE, Context.MODE_PRIVATE);
    private SharedPreferences.Editor poEd = poSh.edit();
    private DatabaseHelper db = new DatabaseHelper(BaseApplication.getContext());
    private ProgressDialog dialog = new ProgressDialog();

    public AppFragment() {

    }


    public static AppFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AppFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_app, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        controlFab = (FloatingActionButton) view.findViewById(R.id.control_fab);
        recyclerView = (RecyclerView) view.findViewById(R.id.applist_lv);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        controlFab.setOnClickListener(this);
        appPresenter.getData(1);
    }

    private void init() {

    }

    /**
     * 多选逻辑
     *
     * @param position
     */
    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            positionSet.remove(position);
            selectData.remove(data.get(position));
        } else {
            // 如果不包含，则添加
            positionSet.add(position);
            selectData.add(data.get(position));
        }
        if (positionSet.size() == 0) {
            // 如果没有选中任何的item，则退出多选模式
            actionMode.finish();
        } else {
            // 设置ActionMode标题
            actionMode.setTitle(getResources().getString(R.string.has_select) + positionSet.size());
            // 更新列表界面，否则无法显示已选的item
            appListAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showSuccess(List<AppInfo> appInfos) {
        Collections.sort(appInfos);
        data = appInfos;
        appListAdapter = new AppListAdapter(data, 0);
        appListAdapter.setOnItemClickListener(this);
        appListAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(appListAdapter);
    }

    @Override
    public void showFaild() {

    }

    @Override
    public void showProgress() {
        dialog.showDialog(getContext(), getResources().getString(R.string.loading), getResources().getString(R.string.waiting));
    }

    @Override
    public void hintProgress() {
        dialog.hideDialog();
    }

    @Override
    public void refreshUI() {
        appPresenter.getData(1);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_fab:
                try {
                    List<String> poStr = new ArrayList<>();
                    List<AppInfo> appInfos = new ArrayList<>();
                    Cursor c = DatabaseUtils.getPolicy(db);
                    if (c != null && c.moveToFirst()) {
                        do {
                            poStr.add(c.getString(c.getColumnIndex("policyName")));
                        } while (c.moveToNext());
                    }
                    crePolicyGroupDialog = new CrePolicyGroupDialog(getActivity());
                    String policyStr = crePolicyGroupDialog.showDialog(poStr, selectData, this);
                    selectData = new ArrayList<>();
                    /*Set<AppInfo> valueSet = new HashSet<>();
                    for (int position : positionSet) {
                        valueSet.add(appListAdapter.getItem(position));
                    }
                    for (AppInfo val : valueSet) {
                        BaseApplication.mDPM.setApplicationHidden(BaseApplication.mComponentName, val.getPkgName(), true);
                    }*/
                    actionMode.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        if (actionMode != null) {
            // 如果当前处于多选状态，则进入多选状态的逻辑
            // 维护当前已选的position
            addOrRemove(position);
        } else {
            // 如果不是多选状态，则进入点击事件的业务逻辑
            // TODO something
        }
    }

    @Override
    public void OnItemLongClickListener(View view, int position) {
        if (actionMode == null) {
            actionMode = getActivity().startActionMode(this);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        if (actionMode == null) {
            actionMode = mode;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_delete, menu);
            Constant.isMuCheck = true;
            controlFab.setVisibility(View.VISIBLE);
            appListAdapter.notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        positionSet.clear();
        Constant.isMuCheck = false;
        controlFab.setVisibility(View.GONE);
        appListAdapter.notifyDataSetChanged();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
