package com.bruno.uploader.test;

import android.util.Log;

import com.bruno.uploader.IDownloadWorkerInterface;

/**
 * Created by bruno on 25/02/15.
 */
public class DownloadListener implements IDownloadWorkerInterface {
    private static final String TAG = DownloadListener.class.getSimpleName();
    private final String mUrl;
    private Thread mCurrentThread;

    public DownloadListener(String url) {
        mUrl = url;
    }

    @Override
    public void setThread(Thread currentThread) {
        mCurrentThread = currentThread;
    }

    @Override
    public void onStarted() {
        Log.i(TAG, "Started download " + mUrl);
    }

    @Override
    public void onError(Exception e) {
        Log.i(TAG, "Error download " + mUrl, e);
    }

    @Override
    public void onCompleted(String path) {
        Log.i(TAG, "Downloaded " + mUrl + " into " + path);
    }
}
