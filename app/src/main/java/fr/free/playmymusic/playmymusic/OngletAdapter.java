package fr.free.playmymusic.playmymusic;


import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;

/**
 * Created by mat on 26/03/2016.
 */
public class OngletAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public OngletAdapter(android.support.v4.app.FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabHomeFragment OngletActualMusic = new TabHomeFragment();
                return OngletActualMusic;
            case 1:
                TabSearchFragment OngletSearchMusic = new TabSearchFragment();
                return OngletSearchMusic;
            case 2:
                TabPlaylistFragment OngletAffichePlaylist = new TabPlaylistFragment();
                return OngletAffichePlaylist;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
