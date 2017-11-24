package fr.free.playmymusic.playmymusic;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionFragment extends Fragment {


    public ConnectionFragment() {
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
        return inflater.inflate(R.layout.fragment_connection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(States.usersinfo == false) {

            ProgressBar progressBar = (ProgressBar)getView().findViewById(R.id.progressBarC);
            progressBar.setVisibility(View.GONE);

            Button buttonC = (Button)getView().findViewById(R.id.connection);
            Button buttonI = (Button)getView().findViewById(R.id.inscription);

            final LinearLayout linearLayoutInfo = (LinearLayout)getView().findViewById(R.id.linearLayoutInfo);

            final LinearLayout linearLayoutI = (LinearLayout)getView().findViewById(R.id.linearLayoutInscription);
            linearLayoutI.setVisibility(View.GONE);
            final LinearLayout linearLayoutC = (LinearLayout)getView().findViewById(R.id.linearLayoutConnection);
            linearLayoutC.setVisibility(View.GONE);

            Button buttonIns = (Button)getView().findViewById(R.id.inscriptionb);
            Button buttonCon = (Button)getView().findViewById(R.id.connectionb);


            final EditText username = (EditText)getView().findViewById(R.id.ins_username_et);
            final EditText password = (EditText)getView().findViewById(R.id.ins_password_et);
            final EditText confirma = (EditText)getView().findViewById(R.id.ins_password_et_2);

            final EditText usernamec = (EditText)getView().findViewById(R.id.con_username_et);
            final EditText passwordc = (EditText)getView().findViewById(R.id.con_password_et);

            buttonIns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!username.getText().toString().equals("")) {
                        if (!password.getText().toString().equals("") && password.getText().toString().equals(confirma.getText().toString())) {
                            ApiPlayMyMusic api = new ApiPlayMyMusic(new OTCJSONObject(),username.getText().toString(),password.getText().toString(),0);
                            api.execute();
                        }
                        else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Erreur :");
                            alertDialog.setMessage("Le mot de passer et sa confirmation doivent être identique.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                    else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erreur :");
                        alertDialog.setMessage("Le nom d'utilisateur est requis.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

                class OTCJSONObject implements OnTaskCompleted<JSONObject> {
                    @Override
                    public void onTaskCompleted(JSONObject object, int i) {

                        try {
                            String valid = object.getString("valid");
                            if (valid.equals("done")){
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Inscription");
                                alertDialog.setMessage("L'inscription s'est bien déroulée !");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                            else {
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Erreur");
                                alertDialog.setMessage("Ce nom d'utilisateur est déjà pris.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            buttonCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!usernamec.getText().toString().equals("")) {
                        if (!passwordc.getText().toString().equals("")) {
                            ApiPlayMyMusic api = new ApiPlayMyMusic(new OTCJSONObject(),usernamec.getText().toString(),passwordc.getText().toString(),1);
                            api.execute();
                        } else {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Erreur :");
                            alertDialog.setMessage("Le mot de passer est requis.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                        alertDialog.setTitle("Erreur :");
                        alertDialog.setMessage("Le nom d'utilisateur est requis.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }

                class OTCJSONObject implements OnTaskCompleted<JSONObject> {
                    @Override
                    public void onTaskCompleted(JSONObject object, int i) {

                        try {
                            if (object != null){
                                JSONObject user = object.getJSONObject("user");
                                if(user != null) {
                                    States.userid = user.getInt("id");
                                    States.username = user.getString("name");
                                    States.usersinfo = true;
                                    States.connected = true;

                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", user.getString("name"));
                                    editor.putInt("userid",user.getInt("id"));
                                    editor.commit();

                                    if(user.getInt("party") != -1) {
                                        States.party_join = true;
                                        States.party_id = user.getInt("party");
                                        editor.putInt("partyid",user.getInt("party"));
                                        editor.commit();
                                        States.HomeState = 2;
                                        MainActivity.loadSelection(0);
                                    }
                                    else {
                                        States.party_join = false;
                                        States.party_id = -1;
                                        States.HomeState = 0;
                                        MainActivity.loadSelection(0);
                                    }
                                }
                            }
                            else {
                                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle("Erreur");
                                alertDialog.setMessage("Nom d'utilisateur ou mot de passe incorrect.");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }

                        } catch (JSONException e) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Erreur");
                            alertDialog.setMessage("Nom d'utilisateur ou mot de passe incorrect.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                }
            });

            buttonC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayoutInfo.setVisibility(View.GONE);
                    linearLayoutC.setVisibility(View.VISIBLE);
                }
            });

            buttonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayoutInfo.setVisibility(View.GONE);
                    linearLayoutI.setVisibility(View.VISIBLE);
                }
            });
        }
        else{
            final LinearLayout linearLayoutInfo = (LinearLayout)getView().findViewById(R.id.linearLayoutInfo);
            linearLayoutInfo.setVisibility(View.GONE);
            final LinearLayout linearLayoutI = (LinearLayout)getView().findViewById(R.id.linearLayoutInscription);
            linearLayoutI.setVisibility(View.GONE);
            final LinearLayout linearLayoutC = (LinearLayout)getView().findViewById(R.id.linearLayoutConnection);
            linearLayoutC.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
