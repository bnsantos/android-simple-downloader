package com.bruno.uploader.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bruno.uploader.DownloadManager;

import java.io.File;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File directory = Environment.getExternalStoragePublicDirectory("DownloaderTest");
        if (!directory.exists()) {
            Log.i(TAG, "Creating [" + directory.getAbsolutePath() + "] folders " + directory.mkdirs());
        }

        String urls[] = getResources().getStringArray(R.array.urls);
        int index = 0;
        for(String url: urls){
            DownloadManager.download(this, url, "file_"+Integer.toString(index++)+"_"+randomInt()+".png", directory.getAbsolutePath());
        }
        //DownloadManager.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static Random sRandom;

    private static String randomInt() {
        if (sRandom == null) {
            sRandom = new Random();
        }
        return Integer.toString(Math.abs(sRandom.nextInt(1000)));
    }
}
