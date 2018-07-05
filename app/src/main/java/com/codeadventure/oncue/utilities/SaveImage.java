package com.codeadventure.oncue.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Deep on 9/3/2017.
 */

public class SaveImage {

    static Context context;
    public SaveImage(Context context){
        this.context = context;
    }


    public static class DownloadImage extends AsyncTask<String,Void,Bitmap>
    {
        protected Bitmap doInBackground(String... urls)
        {
            Bitmap bitmap = null;
            try
            {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    public static void saveMyImage(String Uri,String name) {

        Bitmap bitmap = null;
        DownloadImage downloadImage = new DownloadImage();
        try
        {
            bitmap = downloadImage.execute(Uri).get();          //Put here
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        File filename;
        try {
            String path1 = android.os.Environment.getExternalStorageDirectory()
                    .toString();
            File file = new File(path1 + "/" + "OnCue");
            if (!file.exists())
                file.mkdirs();
            filename = new File(file.getAbsolutePath() + "/" + name
                    + ".jpg");
            FileOutputStream out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            ContentValues image = getImageContent(filename);
            Uri result = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
            System.out.println(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ContentValues getImageContent(File parent) {
        ContentValues image = new ContentValues();
        image.put(MediaStore.Images.Media.TITLE, "OnCue");
        image.put(MediaStore.Images.Media.DISPLAY_NAME, "ProfilePic");
        image.put(MediaStore.Images.Media.DESCRIPTION, "Profile Image");
        image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        image.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        image.put(MediaStore.Images.Media.ORIENTATION, 0);
        image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString()
                .toLowerCase().hashCode());
        image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
                .toLowerCase());
        image.put(MediaStore.Images.Media.SIZE, parent.length());
        image.put(MediaStore.Images.Media.DATA, parent.getAbsolutePath());
        return image;
    }




}
