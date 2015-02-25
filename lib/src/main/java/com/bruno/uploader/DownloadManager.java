package com.bruno.uploader;

import android.content.Context;
import android.util.Log;

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

    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;

    // Sets the initial threadpool size to 8
    private static final int CORE_POOL_SIZE = 8;

    // Sets the maximum threadpool size to availableProcessors
    private static final int MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    // A managed pool of background download threads
    private final ThreadPoolExecutor mDownloadThreadPool;

    // A queue of Runnables for the image download pool
    private final BlockingQueue<Runnable> mDownloadWorkQueue;

    // A single instance of PhotoManager, used to implement the singleton pattern
    private static DownloadManager sInstance = null;

    // A static block that sets class fields
    static {
        // The time unit for "keep alive" is in seconds
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        // Creates a single static instance of PhotoManager
        sInstance = new DownloadManager();
    }

    private DownloadManager(){
        /*
         * Creates a work queue for the pool of Thread objects used for downloading, using a linked
         * list queue that blocks when the queue is empty.
         */
        mDownloadWorkQueue = new LinkedBlockingQueue<>();

        /*
         * Creates a new pool of Thread objects for the download work queue
         */
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDownloadWorkQueue);

        Log.i(TAG, "Starting threadPool with MaxPool[" + MAXIMUM_POOL_SIZE + "]");
    }

    public static void download(Context context, String url, String filename, String path){
        sInstance.mDownloadWorkQueue.add(new DownloadWorker(context, url, filename, path));
        start();
    }

    public static void start(){
        sInstance.mDownloadThreadPool.execute(sInstance.mDownloadWorkQueue.poll());
    }
}
