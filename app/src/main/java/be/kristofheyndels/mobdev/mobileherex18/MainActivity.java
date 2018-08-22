package be.kristofheyndels.mobdev.mobileherex18;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, LIST_FRAGMENT_TAG, listFragment);

        if (detailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, DETAIL_FRAGMENT_TAG, detailFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClearCacheButtonClicked(MenuItem item) {
        try {
            File cacheDir = getApplicationContext().getCacheDir();
            deleteFile(cacheDir);
            Toast.makeText(getApplicationContext(), "Cache Deleted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClearDataButtonClicked(MenuItem item) {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("Delete data")
                .setMessage("Are you sure you want to delete all your data? You will lose all bookmarks.\n\nPerforming this action will close the app.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        deleteData();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteData() {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            ((ActivityManager) Objects.requireNonNull(getApplicationContext().getSystemService(ACTIVITY_SERVICE)))
                    .clearApplicationUserData();
        } else {
            File cacheDirectory = getCacheDir();
            File applicationDirectory = new File(cacheDirectory.getParent());
            if (applicationDirectory.exists()) {
                String[] fileNames = applicationDirectory.list();
                for (String fileName : fileNames) {
                    if (!fileName.equals("lib")) {
                        deleteFile(new File(applicationDirectory, fileName));
                    }
                }
            }
        }
        Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
    }

    private boolean deleteFile(File cache) {
        if (cache != null && cache.isDirectory()) {
            String[] children = cache.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFile(new File(cache, children[i]));
                if (!success) {
                    return false;
                }
            }
            return cache.delete();
        } else if (cache != null && cache.isFile())
            return cache.delete();
        else {
            return false;
        }
    }

    @Override
    public void onUserCategorySelectionMade() {
        selectedItem = null;
        activeFragment = ActiveFragment.ListFragment;
    }

    @Override
    public void onUserListItemSelectionMade(SwapiObject selectedItem) {
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
