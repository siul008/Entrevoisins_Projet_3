package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new NeighbourFragment();
                break;
            case 1:
                fragment = new FavouriteNeighbourFragment();
                break;

            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    /**
     * get the number of pages
     *
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}