/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guanshinfo.win7.stand_alone_control.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by guanshinfo-lizhunan on 2017/7/14.
 * 策略列表构造器
 */
@Deprecated
public class CrePolicyListAdapter extends RecyclerView.Adapter<CrePolicyListAdapter.ViewHolder> {

    private Context context;
    private List<String> data = new ArrayList<>();
    private OnItemClickListener itemClickListener;

    public CrePolicyListAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cre_policy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(data.get(position));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.OnItemClickListener(holder.itemView,holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.policyname_tv);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.policy_item_rl);
        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }
}
