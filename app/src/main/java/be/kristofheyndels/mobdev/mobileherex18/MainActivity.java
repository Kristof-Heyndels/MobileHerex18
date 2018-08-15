package be.kristofheyndels.mobdev.mobileherex18;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import Factory.Details;
import Factory.DetailsFactory;
import Model.SwapiObject;


public class MainActivity extends AppCompatActivity implements ListFragment.OnUserSelectionMade, DetailFragment.OnFragmentInteractionListener {

    private enum ActiveFragment {
        ListFragment,
        DetailFragment
    }

    public static final String URL = "https://swapi.co/api/";
    private static final String TAG = "MainActivity";
    private static ActiveFragment activeFragment = ActiveFragment.ListFragment;

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

        if (savedInstanceState != null) {
            listFragment = (ListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "listFragment");
            detailFragment = (DetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "detailFragment");
        }

        if (listFragment == null) {
            listFragment = new ListFragment();
        }
        if (detailFragment == null) {
            detailFragment = new DetailFragment();
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
            getSupportFragmentManager().putFragment(outState, "listFragment", listFragment);

        if (detailFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, "detailFragment", detailFragment);
    }

    @Override
    public void onUserSelectedMade(SwapiObject selectedItem) {
        DetailsFactory factory = new DetailsFactory();
        Details details = factory.buildDetails(selectedItem);
        details.generateLayout(detailFragment);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
