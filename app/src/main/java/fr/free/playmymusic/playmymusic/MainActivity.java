package fr.free.playmymusic.playmymusic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnTaskCompleted<JSONObject> {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private static ListView navList;
    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;
    public static MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        navList = (ListView)findViewById(R.id.navigationlist);

        ArrayList<String> navArray = new ArrayList<String>();
        navArray.add("Home");
        navArray.add("Historique");
        navArray.add("Options");

        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,navArray);
        navList.setAdapter(adapter);
        navList.setOnItemClickListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        int userid = sharedPreferences.getInt("userid", 0);

        if(!username.equals("") && userid != 0) {

            States.usersinfo = true;
            String[] params = new String[3];
            params[0] = "all";
            params[1] = "id";
            params[2] = String.valueOf(userid);

            ApiPlayMyMusic apiPlayMyMusic = new ApiPlayMyMusic(this,"get","users",params);
            apiPlayMyMusic.execute();

            States.HomeState = 5;
            loadSelection(0);
        }
        else {
            States.usersinfo = false;
            States.HomeState = 5;
            loadSelection(0);
        }

    }

    public static void loadSelection(int i) {
        navList.setItemChecked(i,true);
        States.ActualMainFragment = i;
        switch(i) {
            case 0:
                if(States.HomeState == 0) {
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,homeFragment,"HomeFragmentTag");
                    fragmentTransaction.commit();
                }
                else if(States.HomeState == 1) {
                    ListepartieFragment listepartieFragment = new ListepartieFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,listepartieFragment,"ListeFragmentTag");
                    fragmentTransaction.commit();
                }
                else if(States.HomeState == 2) {
                    ClientFragment clientFragment = new ClientFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,clientFragment,"ClientFragmentTag");
                    fragmentTransaction.commit();
                }
                else if(States.HomeState == 5) {
                    ConnectionFragment connectionFragment = new ConnectionFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container,connectionFragment,"ConnectionFragmentTag");
                    fragmentTransaction.commit();
                }
                break;
            case 1:
                HistoricFragment historicFragment = new HistoricFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,historicFragment,"HistoricFragmentTag");
                fragmentTransaction.commit();
                break;
            case 2:
                OptionsFragment optionsFragment = new OptionsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,optionsFragment,"OptionsFragmentTag");
                fragmentTransaction.commit();
                break;
            case 3:
                break;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.leaveParty);
        menuItem.setVisible(false);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.leaveParty) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Etes-vous sur de vouloir quitter la partie ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // wait
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .create().show();
        }
        else if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(navList)) {
                drawerLayout.closeDrawer(navList);
            }
            else {
                drawerLayout.openDrawer(navList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        loadSelection(position);

        drawerLayout.closeDrawer(navList);
    }

   @Override
    public void onBackPressed() {
    }

    @Override
    public void onTaskCompleted(JSONObject jsonObject, int i) {
        if(jsonObject != null) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("users");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");

                if(jsonObject1.getString("name").equals(username)) {
                    Log.i("PlayMyMusic",jsonObject1.getString("name")+" : "+username);
                    States.connected = true;
                    States.userid = jsonObject1.getInt("id");
                    States.username = jsonObject1.getString("name");

                    if(jsonObject1.getInt("party") != -1) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("party", jsonObject1.getInt("party"));
                        editor.commit();
                        States.party_id = jsonObject1.getInt("party");
                        States.HomeState = 2;
                        loadSelection(0);
                    }
                    else {
                        States.HomeState = 0;
                        loadSelection(0);
                    }
                }
                else {
                    States.usersinfo = false;
                    States.HomeState = 5;
                    loadSelection(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
