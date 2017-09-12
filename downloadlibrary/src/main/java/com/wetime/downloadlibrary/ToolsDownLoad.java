package com.wetime.downloadlibrary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import zlc.season.rxdownload2.RxDownload;

/**
 * Created by zhoukang on 2017/9/12.
 */

public class ToolsDownLoad {
    private static final String TAG = "zk download";

    public static void downXiaoshuo(final Context mContext) {
        downApkAndInstall(mContext, ConstDownLoad.xiaoshuo);
    }

    private static void downApkAndInstall(final Context mContext, String url) {
        downFile(mContext, url);
    }

    private static void downFile(final Context mContext, final String url) {
        RxDownload.getInstance(mContext)
                .serviceDownload(url)   //只需传url即可，添加一个下载任务
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.w(TAG, throwable);
                        Toast.makeText(mContext, "添加任务失败", Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        File[] files = RxDownload.getInstance(mContext).getRealFiles(url);
                        if (files != null) {
                            File file = files[0];
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                            mContext.startActivity(intent);
                        }
                    }
                });
    }
}
