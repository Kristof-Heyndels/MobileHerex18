package be.kristofheyndels.mobdev.mobileherex18;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

import be.kristofheyndels.mobdev.helpers.MyPagerAdapter;
import be.kristofheyndels.mobdev.model.JsonCallBack;
import be.kristofheyndels.mobdev.model.Person;
import be.kristofheyndels.mobdev.model.SWAPI;
import be.kristofheyndels.mobdev.model.SwapiObject;


public class MainActivity extends AppCompatActivity implements ListFragment.OnUserSelectionMade, DetailFragment.OnFragmentInteractionListener {

    private enum ActiveFragment {
        ListFragment,
        DetailFragment
    }

    public static final String URL = "https://swapi.co/api/";
    private static final String TAG = "MainActivity";

    public static Context appContext;
    public static Activity appActivity;

    public static SwapiTab swapiTab;
    public static BookmarkTab bookmarkTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        appContext = getApplicationContext();
        appActivity = this;

        // Setting up ActionBar
        {
            Toolbar mToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Setting up ViewPager
        {
            ViewPager vpTabs = findViewById(R.id.vp_tabs);
            FragmentPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
            vpTabs.setAdapter(adapterViewPager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem action_search = menu.findItem(R.id.action_search);
        SearchView swapi_search = (SearchView) action_search.getActionView();

        swapi_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                swapiTab.parseSearchResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return true;
            }
        });
        swapi_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                swapiTab.closeSearch();
                return false;
            }
        });

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
        swapiTab.onUserCategorySelectionMade();
    }

    @Override
    public void onUserListItemSelectionMade(SwapiObject selectedItem) {
        swapiTab.onUserListItemSelectionMade(selectedItem);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
