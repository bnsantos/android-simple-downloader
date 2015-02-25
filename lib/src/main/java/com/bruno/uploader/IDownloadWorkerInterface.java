package com.bruno.uploader;

/**
 * Created by bruno on 25/02/15.
 */
public interface IDownloadWorkerInterface {
    public void setThread(Thread currentThread);
    public void onStarted();
    public void onError(Exception e);
    public void onCompleted(String path);
}
