package be.kristofheyndels.mobdev.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import be.kristofheyndels.mobdev.mobileherex18.BookmarkTab;
import be.kristofheyndels.mobdev.mobileherex18.SwapiTab;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int AMOUNT_OF_TABS = 2;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new SwapiTab();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new BookmarkTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return AMOUNT_OF_TABS;
    }

    @Override
    public CharSequence getPageTitle(int i) {
        switch (i) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return "SWAPI";
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return "Bookmarks";
            default:
                return "";
        }
    }
}
