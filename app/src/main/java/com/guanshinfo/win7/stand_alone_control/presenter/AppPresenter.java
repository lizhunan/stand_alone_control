/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.presenter;

import android.os.Handler;

import com.guanshinfo.win7.stand_alone_control.bean.AppInfo;
import com.guanshinfo.win7.stand_alone_control.model.AppModel;
import com.guanshinfo.win7.stand_alone_control.model.OnGetAppInfo;
import com.guanshinfo.win7.stand_alone_control.view.fragment.IGetData;

import java.util.List;

/**
 * Created by guanshinfo-lizhunan on 2017/7/12.
 * app内容提供
 */

public class AppPresenter {

    private IGetData iGetData;
    private AppModel appModel;
    private Handler handler = new Handler();

    public AppPresenter() {
    }

    public AppPresenter(IGetData iGetData) {
        this.iGetData = iGetData;
        this.appModel = new AppModel();
    }

    /**
     * 提供数据
     *
     * @param i 数据类型
     */
    public void getData(int i) {
        appModel.data(i, new OnGetAppInfo() {
            @Override
            public void onSuccess(final List<AppInfo> appInfos) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.showSuccess(appInfos);
                    }
                });
            }

            @Override
            public void onFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.showFaild();
                    }
                });
            }

            @Override
            public void onLoading() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.showProgress();
                    }
                });
            }

            @Override
            public void onLoaded() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        iGetData.hintProgress();
                    }
                });
            }
        });
    }

}
