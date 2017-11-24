package fr.free.playmymusic.playmymusic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 * Created by mat on 24/03/2016.
 */
public class ImageFromURL extends AsyncTask<String, String, Drawable>{


    private static HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

    private ImageView welcome;
    private URL url = null;
    private String urls = "";
    private OnTaskCompleted callback = null;
    private int i = 0;

    ImageFromURL (OnTaskCompleted callback, String url, int i) {
        this.callback = callback;
        this.i = i;
        this.urls = url;
        //Log.i("i", url);
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Drawable doInBackground(String... params) {
        try {
            if(imageCache.containsKey(urls)) {
                SoftReference<Drawable> softReference = imageCache.get(urls);
                Drawable drawable = softReference.get();

                if(drawable != null) {
                    return drawable;
                }
            }

            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            Drawable img = Drawable.createFromStream(inputStream, "src");

            imageCache.put(urls,new SoftReference<Drawable>(img));

            return img;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable b) {
        super.onPostExecute(b);
        callback.onTaskCompleted(b,i);
    }

}
