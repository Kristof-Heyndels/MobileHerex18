package be.kristofheyndels.mobdev.mobileherex18;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import Data.ActiveFragment;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    public static final String URL = "https://swapi.co/api/";
    private static final String TAG = "MainActivity";
    private static ActiveFragment activeFragment = ActiveFragment.ListFragment;

    private ListFragment listFragment;
    private int listFragmenId;
    private DetailFragment detailFragment;
    private int detailFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            listFragmenId = R.id.frag_container;
            detailFragmentId = R.id.frag_container;

            if (activeFragment == ActiveFragment.ListFragment) {
                listFragment = new ListFragment();
                trans.replace(listFragmenId, listFragment);
            } else {
                detailFragment = new DetailFragment();
                trans.replace(detailFragmentId, detailFragment);
            }
            trans.addToBackStack(null);
            trans.commit();
        } else {
            listFragmenId = R.id.frag_list;
            detailFragmentId = R.id.frag_detail;

            listFragment = new ListFragment();
            trans.replace(listFragmenId, listFragment);
            trans.addToBackStack(null);

            detailFragment = new DetailFragment();
            trans.replace(detailFragmentId, detailFragment);
            trans.addToBackStack(null);

            trans.commit();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
