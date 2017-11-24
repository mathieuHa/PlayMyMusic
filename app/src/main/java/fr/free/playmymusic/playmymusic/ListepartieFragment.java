package fr.free.playmymusic.playmymusic;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListepartieFragment extends Fragment implements OnTaskCompleted<JSONObject> {

    public ListView listView;
    private  ArrayAdapter<DataParty> arrayAdapter = null;

    public ListepartieFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_listepartie, container, false);
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

        States.HomeState = 1;

        arrayAdapter = new ArrayAdapter<DataParty>(getActivity(),android.R.layout.simple_expandable_list_item_1);

        listView = (ListView)getView().findViewById(R.id.listView);
        this.listView = listView;
        listView.setVisibility(View.GONE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            class OTCJSONObject implements OnTaskCompleted<JSONObject> {
                @Override
                public void onTaskCompleted(JSONObject object, int i) {
                    try {
                        //Log.i("i","Party : " + object.getInt("valid"));
                        if(object.getInt("valid") != -1) {
                            States.party_id = object.getInt("valid");
                            States.party_join = true;
                            States.HomeState = 2;
                            MainActivity.loadSelection(0);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // id party : arrayAdapter.getItem(position).getDp_id()
                //Log.i("i","Connexion à : " + arrayAdapter.getItem(position).getDp_id());
                ApiPlayMyMusic api = new ApiPlayMyMusic(new OTCJSONObject(),"put","users","party",arrayAdapter.getItem(position).getDp_id(),String.valueOf(States.userid));
                api.execute();
            }
        });

        ApiPlayMyMusic api = new ApiPlayMyMusic(this, "get", "party", "all");
        api.execute();


        EditText editText = (EditText)getView().findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onTaskCompleted(JSONObject jsonObject, int i) {
        RelativeLayout relativeLayout = (RelativeLayout)getView().findViewById(R.id.loadingPanel);
        relativeLayout.setVisibility(View.GONE);

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("party");

            for(int j = 0 ; j < jsonArray.length() ; j++) {
                JSONObject finalJsonObject = jsonArray.getJSONObject(j);
                arrayAdapter.add(new DataParty(finalJsonObject.getString("name"),finalJsonObject.getString("author"),finalJsonObject.getString("id")));
            }
            listView.setAdapter(arrayAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.listView.setVisibility(View.VISIBLE);
    }
}
