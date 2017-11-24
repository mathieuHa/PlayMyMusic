package fr.free.playmymusic.playmymusic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment {

    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.playlist));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.loup));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.people));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadtab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fragmentManager = getActivity().getSupportFragmentManager();

        loadtab(0);
    }

    public void loadtab(int tab) {
        if(MainActivity.menuItem != null) MainActivity.menuItem.setVisible(true);
        switch(tab) {
            case 0:
                TabHomeFragment tabHomeFragment = new TabHomeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_tab_container, tabHomeFragment, "TabHomeFragment");
                fragmentTransaction.commit();
                break;
            case 1:
                TabPlaylistFragment tabPlaylistFragment = new TabPlaylistFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_tab_container, tabPlaylistFragment, "TabPlaylistFragmentTag");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case 2:
                TabMusicSearchFragment searchFragment = new TabMusicSearchFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_tab_container, searchFragment, "TabMusicSearchFragmentTag");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_container, container, false);
    }
}
