package be.kristofheyndels.mobdev.mobileherex18;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import Model.SwapiObject;


public class MainActivity extends AppCompatActivity implements ListFragment.OnUserSelectionMade, DetailFragment.OnFragmentInteractionListener {

    private enum ActiveFragment {
        ListFragment,
        DetailFragment
    }

    public static final String URL = "https://swapi.co/api/";
    private static final String TAG = "MainActivity";
    private static final String LIST_FRAGMENT_TAG = "listFragment";
    private static final String DETAIL_FRAGMENT_TAG = "detailFragment";
    private static ActiveFragment activeFragment = ActiveFragment.ListFragment;
    private static SwapiObject selectedItem;

    private ListFragment listFragment;
    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

        // Always create new detailsFragment, since you cannot change fragment container id (fragContainer <-> frag_detail)
        detailFragment = new DetailFragment();

        if (savedInstanceState != null) {
            listFragment = (ListFragment) getSupportFragmentManager().getFragment(savedInstanceState, LIST_FRAGMENT_TAG);
            detailFragment.setSelectedItem(selectedItem);
        }

        if (listFragment == null) {
            listFragment = new ListFragment();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (activeFragment == ActiveFragment.ListFragment) {
                trans.replace(R.id.frag_container, listFragment);
            } else {
                trans.replace(R.id.frag_container, detailFragment);
            }
            trans.addToBackStack(null);
            trans.commit();
        } else {
            trans.replace(R.id.frag_container, listFragment);
            trans.addToBackStack(null);

            trans.replace(R.id.frag_detail, detailFragment);
            trans.addToBackStack(null);

            trans.commit();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, LIST_FRAGMENT_TAG, listFragment);

        if (detailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, DETAIL_FRAGMENT_TAG, detailFragment);
        }
    }

    @Override
    public void onUserSelectedMade(SwapiObject selectedItem) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

            trans.replace(R.id.frag_container, detailFragment);
            trans.addToBackStack(null);
            trans.commit();
        }

        activeFragment = ActiveFragment.DetailFragment;
        MainActivity.selectedItem = selectedItem;
        detailFragment.setSelectedItem(selectedItem);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
