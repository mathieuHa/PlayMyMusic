package fr.free.playmymusic.playmymusic;

import org.json.JSONObject;

/**
 * Created by Alexandre on 23/03/2016.
 */
public interface OnTaskCompleted<T> {
    void onTaskCompleted(T object, int i);
}
