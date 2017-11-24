package fr.free.playmymusic.playmymusic;

/**
 * Created by mat on 23/03/2016.
 */
public class DataSpotify {
    private int vote;
    private String ID = "";
    private String nomMusic = "";
    private String h_img_big= "";
    private String h_img_mini= "";
    private String artist= "";
    private String album= "";

    public String getNomMusic() {
        return nomMusic;
    }

    public void setNomMusic(String nomMusic) {
        this.nomMusic = nomMusic;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getH_img_big() {
        return h_img_big;
    }

    public void setH_img_big(String h_img_big) {
        this.h_img_big = h_img_big;
    }

    public String getH_img_mini() {
        return h_img_mini;
    }

    public void setH_img_mini(String h_img_mini) {
        this.h_img_mini = h_img_mini;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) { this.artist = artist; }

    public DataSpotify(String ID, String nomMusic, String h_img_big, String h_img_mini, String artist, String album, int vote) {
        this.ID = ID;
        this.nomMusic = nomMusic;
        this.h_img_big = h_img_big;
        this.artist = artist;
        this.album = album;
        this.h_img_mini = h_img_mini;
        this.vote = vote;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
