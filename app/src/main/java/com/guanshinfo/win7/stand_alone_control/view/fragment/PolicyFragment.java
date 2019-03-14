/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.adapter.PolicyListAdapter;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.bean.PolicyInfo;
import com.guanshinfo.win7.stand_alone_control.presenter.PolicyPresenter;
import com.guanshinfo.win7.stand_alone_control.ui.DividerItemDecoration;
import com.guanshinfo.win7.stand_alone_control.ui.dialog.DeletePolicyDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolicyFragment extends Fragment implements IGetPolicy ,PolicyListAdapter.OnItemLongClickListener {

    private OnFragmentInteractionListener mListener;

    private static PolicyFragment INSTANCE;

    private View view;
    private RecyclerView recyclerView;
    private PolicyPresenter policyPresenter = new PolicyPresenter(this);
    private PolicyListAdapter policyListAdapter;
    private DeletePolicyDialog deletePolicyDialog;

    public PolicyFragment() {

    }

    public static PolicyFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PolicyFragment();
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
        view = inflater.inflate(R.layout.fragment_policy, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.applist_lv);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            policyPresenter.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshUI(){
        try {
            policyPresenter.getData();
        } catch (Exception e) {
            e.printStackTrace();
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
    public void showSuccess(List<PolicyInfo> policyInfos) {
        policyListAdapter = new PolicyListAdapter(policyInfos);
        Log.d("showSuccess", "showSuccess");
        policyListAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(policyListAdapter);
        policyListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFaild() {

    }

    @Override
    public void OnItemLongClickListener(View view, int position, String poName, List<AppInfo> appInfos) {
        deletePolicyDialog = new DeletePolicyDialog(getActivity());
        deletePolicyDialog.showDialog(poName,appInfos,this);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
