package com.bruno.uploader;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bruno on 25/02/15.
 */
public class DownloadWorker  implements Runnable{
    private static final String TAG = DownloadWorker.class.getSimpleName();
    private final Context mContext;
    private final String mUrl;
    private final String mFilename;
    private final String mPath;

    public DownloadWorker(Context context, String url, String filename, String path) {
        mContext = context;
        mUrl = url;
        mFilename = filename;
        mPath = path;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        try {
            Log.i(TAG, "Downloading picture " + mUrl);

            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream input = connection.getInputStream();
            if(input!=null){
                ImageUtils.saveDownloadedPicture(mContext, mPath, mFilename, convertInputStreamToByteArray(input));
                Log.i(TAG, "Downloaded picture[" + mUrl + "] path[" + mPath + "]");
            }else{
                Log.e(TAG, "Response null");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error downloading picture[" + mUrl + "]", e);
        } catch (ExternalStorageException e) {
            Log.e(TAG, "Error saving picture[" + mUrl + "]", e);
        }
    }

    public byte[] convertInputStreamToByteArray(InputStream inputStream){
        byte[] bytes= null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte data[] = new byte[1024];
            int count;

            while ((count = inputStream.read(data)) != -1)
            {
                bos.write(data, 0, count);
            }

            bos.flush();
            bos.close();
            inputStream.close();

            bytes = bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }
}
