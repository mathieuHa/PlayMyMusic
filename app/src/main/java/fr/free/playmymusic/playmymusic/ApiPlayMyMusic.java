package fr.free.playmymusic.playmymusic;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alexandre on 15/03/2016.
 */
public class ApiPlayMyMusic extends AsyncTask<String, String, JSONObject> {

    private URL url;
    private String userid = "0a4r5y4ez6t5ze948z7t2vz8t71e87";
    public StringBuffer stringBuffer = null;
    private OnTaskCompleted callback = null;
    private int i;

    ApiPlayMyMusic(OnTaskCompleted callback,String request, String info, String selection) {

        this.callback = callback;
        //Log.i("i","http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&selection=" + selection);
        try {
            url = new URL("http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&selection=" + selection);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    ApiPlayMyMusic(OnTaskCompleted callback,String request, String info, String[] params) {
        this.callback = callback;
        //Log.i("i","http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&selection=" + params[0] + "&where=" + params[1] + "&equals=" + params[2]);
        try {
            url = new URL("http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&selection=" + params[0] + "&where=" + params[1] + "&equals=" + params[2]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    ApiPlayMyMusic(OnTaskCompleted callback,String request, String info, String id_music, int id_user, int id_party, int i) {
        this.callback = callback;
        this.i = i;
        Log.i("i","http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&id_music=" + id_music + "&id_party=" + id_party + "&id_user=" + id_user);
        try {
            url = new URL("http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&id_music=" + id_music + "&id_party=" + id_party + "&id_user=" + id_user);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    ApiPlayMyMusic(OnTaskCompleted callback,String request, String info, String selection, int party, String id) {
        this.callback = callback;
        Log.i("i","http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&party=" + party + "&selection=" + selection + "&where=id&id=" + id);
        try {
            url = new URL("http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&party=" + party + "&selection=" + selection + "&where=id&id=" + id);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    ApiPlayMyMusic(OnTaskCompleted callback,String request, String info, int id_party, String selection, ArrayList<String> params) {
        this.callback = callback;

        String urls = "http://" + States.url + "/" + States.file + "?userid=" + this.userid + "&request=" + request + "&info=" + info + "&selection=" + selection + "&id_party=" + id_party;

        for(int i=0 ; i < params.size() ; i++) {
            urls = urls + "&where" + i + "=" + params.get(i);
        }

        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.i("i",urls);
    }

    ApiPlayMyMusic(OnTaskCompleted callback, String username, String password, int i) {
        this.callback = callback;
        String urls = "";

        if(i==0) {
            urls = "http://" + States.url + "/inscription.php?userid=" + this.userid + "&username=" + username + "&password=" + password;
        }
        else {
            urls = "http://" + States.url + "/connection.php?userid=" + this.userid + "&username=" + username + "&password=" + password;
        }

        try {
            url = new URL(urls);
        } catch (MalformedURLException e) {
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
            Log.e("ApiPlayMymusic", "None JSONObject from " + url);

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
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        callback.onTaskCompleted(jsonObject, i);
    }
}
