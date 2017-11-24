package fr.free.playmymusic.playmymusic;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabMusicSearchFragment extends Fragment {

    public ArrayList<DataSpotify> arrayListOfDataSpotify = null;
    private ListView maListViewPerso = null;
    public MusicDisplayAdapter musicDisplayAdapter = null;
    private ArrayList<Drawable> drawables = null;

    public class OTCJSONObject implements OnTaskCompleted<JSONObject> {
        @Override
        public void onTaskCompleted(JSONObject jsonObject, int i) {
            try {
                JSONObject object = null;

                if (jsonObject != null) {
                    object = jsonObject.getJSONObject("tracks");

                    if (object.length() != 0) {
                        String href = object.getString("href");
                        JSONArray array = object.getJSONArray("items");

                        arrayListOfDataSpotify = new ArrayList<DataSpotify>();
                        drawables = new ArrayList<Drawable>();

                        ArrayList<String> list_id_music = new ArrayList<>();

                        for (int j = 0; j < array.length(); j++) {
                            JSONObject O_item = array.getJSONObject(j);
                            String tmp_id = O_item.getString("id");
                            String tmp_nomMusic = O_item.getString("name");
                            JSONObject O_album = O_item.getJSONObject("album");
                            String tmp_album = O_album.getString("name");
                            JSONArray A_images = O_album.getJSONArray("images");
                            JSONObject O_maxi_img = A_images.getJSONObject(0);
                            JSONObject O_mini_img = A_images.getJSONObject(2);
                            String tmp_maxi_img = O_maxi_img.getString("url");
                            String tmp_mini_img = O_mini_img.getString("url");
                            JSONArray A_artist = O_item.getJSONArray("artists");
                            JSONObject O_artist = A_artist.getJSONObject(0);
                            String tmp_artist = O_artist.getString("name");
                            list_id_music.add(O_item.getString("id"));
                            Log.i("i", tmp_id + " : " + tmp_nomMusic + " de " + tmp_artist + " sur " + tmp_album);
                            arrayListOfDataSpotify.add(new DataSpotify(tmp_id, tmp_nomMusic, tmp_maxi_img, tmp_mini_img, tmp_artist, tmp_album,0));
                        }

                        ApiPlayMyMusic api = new ApiPlayMyMusic(new OTCJSONObject2(),"get","musics",1,"vote",list_id_music);
                        api.execute();

                        maListViewPerso = (ListView) getView().findViewById(R.id.listviewperso);

                        musicDisplayAdapter = new MusicDisplayAdapter(getActivity(),arrayListOfDataSpotify);
                        maListViewPerso.setAdapter(musicDisplayAdapter);
                        maListViewPerso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            @SuppressWarnings("unchecked")
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                maListViewPerso.getItemAtPosition(position);
                                Log.i("i", "cliqué " + position + " ajout en cours");
                                ApiPlayMyMusic apiPlaylist = new ApiPlayMyMusic(new OTCJSONObject3(),"post","addmusics",arrayListOfDataSpotify.get(position).getID(),
                                        States.userid,States.party_id, position);
                                apiPlaylist.execute();

                            }
                        });

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class OTCJSONObject2 implements OnTaskCompleted<JSONObject> {
        @Override
        public void onTaskCompleted(JSONObject object, int i) {
            try {
                if (object != null) {
                JSONArray Amusic = object.getJSONArray("musics");
                for (int j=0; j<Amusic.length(); j++){
                    JSONObject OId = Amusic.getJSONObject(j);
                    for (int ind = 0; ind<arrayListOfDataSpotify.size(); ind++) {
                        if (arrayListOfDataSpotify.get(ind).getID().equals(OId.getString("id"))) {
                            int vote = OId.getInt("vote");
                            arrayListOfDataSpotify.get(ind).setVote(vote);
                            Log.i("i", "OK " + vote + " applause for mathieu ! ");

                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }

    public class OTCJSONObject3 implements OnTaskCompleted<JSONObject> {
        @Override
        public void onTaskCompleted(JSONObject object, int i) {
            try {
                String valid = object.getString("valid");
                if (valid.equals("done")){
                    arrayListOfDataSpotify.get(i).setVote(arrayListOfDataSpotify.get(i).getVote()+1);
                    musicDisplayAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public TabMusicSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music_search, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editText = (EditText)getView().findViewById(R.id.edit);

        ImageButton button = (ImageButton)getView().findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiSpotify api = new ApiSpotify(new OTCJSONObject(), editText.getText().toString());
                api.execute();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("top",666);
    }
}
