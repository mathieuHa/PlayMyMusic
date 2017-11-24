package fr.free.playmymusic.playmymusic;

/**
 * Created by Alexandre on 20/03/2016.
 */
public class States {
    private static final States instance = new States();

    // trucs inutiles
    private static int app = 0;

    // informations sur la partie en cours (ou pas)
    public static boolean party_join = false;
    public static int party_id;

    // les états des différents fragments
    public static int ActualMainFragment = 0; //0=home;1=historic;2=options
    public static int HomeState = 5; // 0=home;1=listepartie

    // infos sur l'utilisateur
    public static boolean usersinfo = false;
    public static boolean connected = false;
    public static int userid = 0;
    public static String username = "";

    // info autres
    public static String url = "88.170.232.166";
    public static String file = "request.php";

    private States() {

    }

    public static States getInstance() {
        return States.instance;
    }

    public static void setApp(int value) {
        app = value;
    }

    public static int getApp() {
        return app;
    }
}
