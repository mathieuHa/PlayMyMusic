package fr.free.playmymusic.playmymusic;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alexandre on 25/03/2016.
 */
public class MusicDisplayAdapter extends ArrayAdapter<DataSpotify> {

    public MusicDisplayAdapter(Activity activity, List<DataSpotify> dataSpotifyList) {
        super(activity,0,dataSpotifyList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity)getContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.item_display, null);

        DataSpotify dataSpotify = getItem(position);

        final ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        ImageFromURL imageFromURL = new ImageFromURL(new OnTaskCompleted<Drawable>() {
            @Override
            public void onTaskCompleted(Drawable object, int i) {
                imageView.setImageDrawable(object);
            }
        }, dataSpotify.getH_img_mini(), position);
        imageFromURL.execute();

        TextView nomMusic = (TextView) rowView.findViewById(R.id.nomMusic);
        TextView nomArtist = (TextView) rowView.findViewById(R.id.nomArtist);
        TextView nombreVote = (TextView) rowView.findViewById(R.id.nombreVote);

        nomMusic.setText(dataSpotify.getNomMusic());
        nomArtist.setText(dataSpotify.getArtist() + " #"+dataSpotify.getAlbum());
        nombreVote.setText(String.valueOf(dataSpotify.getVote()));

        return rowView;
    }
}
