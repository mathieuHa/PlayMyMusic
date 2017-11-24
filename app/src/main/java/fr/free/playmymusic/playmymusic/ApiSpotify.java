package fr.free.playmymusic.playmymusic;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Alexandre on 21/03/2016.
 */
public class ApiSpotify extends AsyncTask<String, String, JSONObject> {

    private URL url = null;
    public StringBuffer stringBuffer = null;
    private OnTaskCompleted callback = null;

    ApiSpotify(OnTaskCompleted callback, String nom_musique) {
        this.callback = callback;
        String urlString = "";

        // ici tu crée ton url

        try {
            nom_musique = URLEncoder.encode(nom_musique, "UTF-8");
            urlString = "https://api.spotify.com/v1/search?q="+ nom_musique +"&type=track";
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        BufferedReader bufferedReader = null ;
        try {

            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            stringBuffer = new StringBuffer();
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            JSONObject jsonObject = new JSONObject(stringBuffer.toString());

            return jsonObject;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        callback.onTaskCompleted(s,0);
    }
}
