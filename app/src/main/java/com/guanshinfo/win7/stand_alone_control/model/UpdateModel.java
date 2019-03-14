/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.model;

import android.content.Context;

import com.guanshinfo.win7.stand_alone_control.base.Constant;
import com.yuan.leopardkit.LeopardHttp;
import com.yuan.leopardkit.download.model.DownloadInfo;
import com.yuan.leopardkit.http.base.HttpMethod;
import com.yuan.leopardkit.interfaces.HttpRespondResult;
import com.yuan.leopardkit.interfaces.IDownloadProgress;

/**
 * Created by guanshinfo-lizhunan on 2017/7/27.
 * 更新应用model，并且同步服务器数据
 */

public class UpdateModel {

    private String url;
    private DownloadInfo downloadInfo;

    /**
     * 获取下载和更新信息
     *
     * @param onUpdate
     * @param context
     */
    public void update(final OnUpdate onUpdate, final Context context) {
        downloadInfo = new DownloadInfo();
        LeopardHttp.SEND(HttpMethod.POST, context, null, new HttpRespondResult() {
            @Override
            public void onSuccess(String content) {
                url = content;
                downloadInfo.setUrl(content);
                downloadInfo.setFileName(Constant.UPDATE_APP_NAME);
                downloadInfo.setFileSavePath(Constant.SD_PATH);
                download(onUpdate, context);
            }

            @Override
            public void onFailure(Throwable error, String content) {

            }
        });

    }

    /**
     * 进行下载
     *
     * @param onUpdate
     * @param context
     */
    private void download(OnUpdate onUpdate, Context context) {
        LeopardHttp.DWONLOAD(downloadInfo, new IDownloadProgress() {
            @Override
            public void onProgress(long key, long progress, long total, boolean done) {

            }

            @Override
            public void onSucess(String result) {

            }

            @Override
            public void onFailed(Throwable e, String reason) {

            }
        });
    }

}
