package com.bruno.uploader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by bruno on 25/02/15.
 */
public class ImageUtils {
    public static String saveDownloadedPicture(Context context, String path, String filename, byte[] bytes) throws ExternalStorageException, IOException {
        File file = createFile(path, filename);
        FileOutputStream fos = new FileOutputStream(file.getPath());
        fos.write(bytes, 0, bytes.length);
        fos.close();

        //Adding picture into gallery
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
        values.put("_data", file.getAbsolutePath());

        ContentResolver cr = context.getContentResolver();
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return file.getAbsolutePath();
    }

    public static File createFile(String path, String filename) throws ExternalStorageException, IOException {
        isExternalStorageWritable();
        return new File(path, filename);
    }

    public static boolean isExternalStorageWritable() throws ExternalStorageException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        throw new ExternalStorageException("Can't write on external storage, current state - " + state);
    }
}
