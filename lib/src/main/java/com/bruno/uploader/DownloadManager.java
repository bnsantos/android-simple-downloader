package com.bruno.uploader;

import android.content.Context;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruno on 25/02/15.
 */
public class DownloadManager {
    private static final String TAG = DownloadManager.class.getSimpleName();

    // Sets the amount of time an idle thread will wait for a task before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final ThreadPoolExecutor mDownloadThreadPool;
    private final BlockingQueue<Runnable> mDownloadWorkQueue;

    private static DownloadManager sInstance = null;

    static {
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        sInstance = new DownloadManager();
    }

    private DownloadManager(){
        mDownloadWorkQueue = new LinkedBlockingQueue<>();
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDownloadWorkQueue);
    }

    public static void download(Context context, String url, String filename, String path, IDownloadWorkerInterface listener){
        sInstance.mDownloadThreadPool.execute(new DownloadWorker(context, url, filename, path, listener));
    }
}
