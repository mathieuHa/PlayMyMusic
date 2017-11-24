package fr.free.playmymusic.playmymusic;

/**
 * Created by Alexandre on 20/03/2016.
 */
public class DataParty {

    private String name;
    private String author;
    private int dp_id;

    @Override
    public String toString() {
        return this.name + " créée par " + this.author;
    }

    public int getDp_id() {
        return dp_id;
    }

    DataParty(String name, String author, String dp_id) {
        this.name = name;
        this.author = author;
        this.dp_id = Integer.valueOf(dp_id);
    }

}
