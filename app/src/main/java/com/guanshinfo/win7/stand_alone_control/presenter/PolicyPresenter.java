/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.presenter;

import android.os.Handler;

import com.guanshinfo.win7.stand_alone_control.bean.PolicyInfo;
import com.guanshinfo.win7.stand_alone_control.model.OnGetPolicyInfo;
import com.guanshinfo.win7.stand_alone_control.model.PolicyModel;
import com.guanshinfo.win7.stand_alone_control.view.fragment.IGetPolicy;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/17.
 * 策略内容提供
 */

public class PolicyPresenter {

    private IGetPolicy iGetPolicy;
    private PolicyModel policyModel;
    private Handler handler = new Handler();

    public PolicyPresenter(IGetPolicy iGetPolicy) {
        this.iGetPolicy = iGetPolicy;
        policyModel = new PolicyModel();
    }

    public void getData() throws Exception {
        policyModel.data(new OnGetPolicyInfo() {
            @Override
            public void onSuccess(final List<PolicyInfo> policyInfos) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetPolicy.showSuccess(policyInfos);
                    }
                });
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
