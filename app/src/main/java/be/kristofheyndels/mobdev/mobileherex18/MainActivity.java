package be.kristofheyndels.mobdev.mobileherex18;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener{

    public static final String URL = "https://swapi.co/api/";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
