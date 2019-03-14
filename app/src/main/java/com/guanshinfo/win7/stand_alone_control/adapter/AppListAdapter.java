/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.guanshinfo.win7.stand_alone_control.R;
import com.guanshinfo.win7.stand_alone_control.base.BaseApplication;
import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.view.fragment.AppFragment;
import com.guanshinfo.win7.stand_alone_control.view.fragment.ControlAppFragment;
import com.guanshinfo.win7.stand_alone_control.view.fragment.SystemAppFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by guanshinfo-lizhunan on 2017/7/12.
 * app列表adapter
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    private List<AppInfo> appInfos = new ArrayList<>();
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private SparseBooleanArray checkBoxState = new SparseBooleanArray();
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    private int fragmentFlag;//fragment标志位

    public AppListAdapter() {
    }

    public AppListAdapter(List<AppInfo> appInfos, int fragmentFlag) {
        this.appInfos = appInfos;
        this.fragmentFlag = fragmentFlag;
        for (int i = 0; i < appInfos.size(); i++) {
            expandState.append(i, false);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.app_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AppInfo appInfo = appInfos.get(position);
        if (Constant.isMuCheck) {
            holder.setData(appInfos.get(position).getAppLabel(), position, fragmentFlag);
        }
        holder.setIsRecyclable(false);
        holder.appNameTv.setText(appInfo.getAppLabel());
        holder.appIocnIv.setImageDrawable(appInfo.getAppIcon());
        holder.appPkgTv.setText(context.getResources().getString(R.string.pkg) + appInfo.getPkgName());
        holder.appVerTv.setText(context.getResources().getString(R.string.ver) + appInfo.getVersionName());
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
                if (!Constant.isMuCheck) {
                    onClickButton(holder.expandableLinearLayout);
                }
            }
        });
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constant.isMuCheck) {
                    onClickButton(holder.expandableLinearLayout);
                }
                itemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
            }
        });
        holder.itemRl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemLongClickListener.OnItemLongClickListener(holder.itemView, holder.getLayoutPosition());
                return false;
            }
        });
        holder.buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constant.isMuCheck) {
                    onClickButton(holder.expandableLinearLayout);
                }
                itemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
            }
        });
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Constant.isMuCheck) {
                    onClickButton(holder.expandableLinearLayout);
                }
                itemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public RelativeLayout itemRl;
        public ImageView appIocnIv;
        public TextView appNameTv;
        public TextView appPkgTv;
        public TextView appVerTv;
        public TextView appDesTv;

        public RelativeLayout buttonLayout;
        public ExpandableLinearLayout expandableLinearLayout;
        public View buttonV;

        public ViewHolder(View itemView) {
            super(itemView);
            itemRl = (RelativeLayout) itemView.findViewById(R.id.list_item);
            appPkgTv = (TextView) itemView.findViewById(R.id.pkg_tv);
            appDesTv = (TextView) itemView.findViewById(R.id.des_tv);
            appVerTv = (TextView) itemView.findViewById(R.id.ver_tv);
            appIocnIv = (ImageView) itemView.findViewById(R.id.appicon_iv);
            appNameTv = (TextView) itemView.findViewById(R.id.appname_tv);
            buttonLayout = (RelativeLayout) itemView.findViewById(R.id.button);
            expandableLinearLayout = (ExpandableLinearLayout) itemView.findViewById(R.id.expandableLayout);
            buttonV = itemView.findViewById(R.id.button_view_v);
        }

        public void setData(String item, int position, int fragmentFlag) {
            switch (fragmentFlag) {
                case 0:
                    Set<Integer> posSetAppFragment = AppFragment.INSTANCE.positionSet;
                    if (posSetAppFragment.contains(position)) {
                        buttonV.setBackgroundResource(R.drawable.ic_check_box_black_24dp);
                    } else {
                        buttonV.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    }
                    break;
                case 1:
                    Set<Integer> posSetSystemFragment = SystemAppFragment.INSTANCE.positionSet;
                    if (posSetSystemFragment.contains(position)) {
                        buttonV.setBackgroundResource(R.drawable.ic_check_box_black_24dp);
                    } else {
                        buttonV.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    }
                    break;
                case 2:
                    Set<Integer> posSetControlAppFragment = ControlAppFragment.INSTANCE.positionSet;
                    if (posSetControlAppFragment.contains(position)) {
                        buttonV.setBackgroundResource(R.drawable.ic_check_box_black_24dp);
                    } else {
                        buttonV.setBackgroundResource(R.drawable.ic_check_box_outline_blank_black_24dp);
                    }
                    break;
            }
        }


    }

    public AppInfo getItem(int pos) {
        return appInfos.get(pos);
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }

    public interface OnItemLongClickListener {
        void OnItemLongClickListener(View view, int position);
    }
}
