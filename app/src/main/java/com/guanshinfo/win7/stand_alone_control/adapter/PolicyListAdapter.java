/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.bean.PolicyInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * policy列表adapter
 */

public class PolicyListAdapter extends RecyclerView.Adapter<PolicyListAdapter.ViewHolder> {

    private Context context;
    private List<PolicyInfo> policyInfos = new ArrayList<>();
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private OnItemLongClickListener itemLongClickListener;

    public PolicyListAdapter() {
    }

    public PolicyListAdapter(List<PolicyInfo> policyInfos) {
        this.policyInfos = policyInfos;
        for (int i = 0; i < policyInfos.size(); i++) {
            expandState.append(i, false);
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new PolicyListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.policy_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PolicyInfo policyInfo = policyInfos.get(position);
        final String policy = policyInfo.getPolicy();
        StringBuilder appnameStr = new StringBuilder();
        Log.d("aaa", "ss" + policyInfo.getAppInfos().size());
        for (int i = 0; i < policyInfo.getAppInfos().size(); i++) {
            Log.d("aaa", "sss" + policyInfo.getAppInfos().get(i).getAppLabel());
            appnameStr.append(policyInfo.getAppInfos().get(i).getAppLabel() + "\n");
        }
        holder.setIsRecyclable(false);
        holder.policyNameTv.setText(policy);
        holder.appNameTv.setText(appnameStr);
        holder.expandableLinearLayout.setInRecyclerView(true);
        holder.expandableLinearLayout.setExpanded(expandState.get(position));
        holder.expandableLinearLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                super.onPreOpen();
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                super.onPreClose();
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });
        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(holder.expandableLinearLayout);
            }
        });
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(holder.expandableLinearLayout);
            }
        });
        holder.itemRl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemLongClickListener.OnItemLongClickListener(holder.itemView, position,policy,policyInfo.getAppInfos());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return policyInfos.size();
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout itemRl;
        public TextView appNameTv;
        public TextView policyNameTv;

        public RelativeLayout buttonLayout;
        public ExpandableLinearLayout expandableLinearLayout;
        public View buttonV;

        public ViewHolder(View itemView) {
            super(itemView);
            itemRl = (RelativeLayout) itemView.findViewById(R.id.list_rl);
            appNameTv = (TextView) itemView.findViewById(R.id.appname_tv);
            policyNameTv = (TextView) itemView.findViewById(R.id.policyame_tv);
            buttonLayout = (RelativeLayout) itemView.findViewById(R.id.button);
            expandableLinearLayout = (ExpandableLinearLayout) itemView.findViewById(R.id.expandableLayout);
            buttonV = itemView.findViewById(R.id.button_view_v);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public interface OnItemLongClickListener {
        void OnItemLongClickListener(View view, int position,String poName,List<AppInfo> list);
    }
}
