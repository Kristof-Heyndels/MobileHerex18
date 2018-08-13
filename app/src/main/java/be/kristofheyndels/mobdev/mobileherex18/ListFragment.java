package be.kristofheyndels.mobdev.mobileherex18;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Helpers.JsonCallBack;
import Helpers.SWAPI;
import Model.Categories;
import Model.FilmResults;
import Model.Person;
import Model.PersonResults;
import Model.Planet;
import Model.PlanetResults;
import Model.Species;
import Model.SpeciesResults;
import Model.Starship;
import Model.StarshipResults;
import Model.SwapiObject;
import Model.Vehicle;
import Model.VehicleResults;

public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    private Spinner dropCategory;
    private static int selectedPosition = 0;
    private ListView lvResults;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            selectedPosition = savedInstanceState.getInt("selectedPosition");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropCategory = view.findViewById(R.id.drop_category);
        lvResults = view.findViewById(R.id.lv_results);

        //Filling Spinner with categories
        SWAPI.getResultsFromURL(getContext(), MainActivity.URL, new JsonCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                populateSpinner(result);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("selectedPosition", dropCategory.getSelectedItemPosition());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void populateSpinner(JSONObject result) {
        Iterator<String> keys = result.keys();

        try {
            while (keys.hasNext()) {
                String name = keys.next();
                Categories.addEntry(name, result.getString(name));
            }

            ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, Categories.getKeys());
            catAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            dropCategory.setAdapter(catAdapter);
            dropCategory.setSelection(selectedPosition);

            buildListAdapter((String) dropCategory.getSelectedItem());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void buildListAdapter(final String selectedCategory) {
        //Filling List with selected category items
        SWAPI.getResultsFromURL(getContext(), Categories.getValueFromKey(selectedCategory), new JsonCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                List<Map<String, String>> resultMapList = buildResultMapList(selectedCategory, result);

                android.widget.ListAdapter adapter = new SimpleAdapter(getContext(), resultMapList, R.layout.list_item,
                        new String[]{"display"},
                        new int[]{R.id.tv_list_display});

                lvResults.setAdapter(adapter);
            }
        });
    }

    private List<Map<String, String>> buildResultMapList(String category, JSONObject json) {
        List<Map<String, String>> returnList = new ArrayList<>();
        Gson gson = new Gson();

        if (category.toLowerCase().equals("films")) {
            FilmResults results = gson.fromJson(json.toString(), FilmResults.class);

            for (SwapiObject o : results.getResults()) {
                HashMap<String, String> map = new HashMap<>();
                map.put("display", o.getDisplayName());
                returnList.add(map);
            }
        } else if (category.toLowerCase().equals("people")) {
            PersonResults results = gson.fromJson(json.toString(), PersonResults.class);

            for (Person per : results.getResults()) {
                HashMap<String, String> map = new HashMap<>();

                map.put("display", per.getDisplayName());
                returnList.add(map);
            }
        } else if (category.toLowerCase().equals("planets")) {
            PlanetResults results = gson.fromJson(json.toString(), PlanetResults.class);

            for (Planet pla : results.getResults()) {
                HashMap<String, String> map = new HashMap<>();

                map.put("display", pla.getDisplayName());
                returnList.add(map);
            }
        } else if (category.toLowerCase().equals("species")) {
            SpeciesResults results = gson.fromJson(json.toString(), SpeciesResults.class);

            for (Species sp : results.getResults()) {
                HashMap<String, String> map = new HashMap<>();

                map.put("display", sp.getDisplayName());
                returnList.add(map);
            }
        } else if (category.toLowerCase().equals("starships")) {
            StarshipResults results = gson.fromJson(json.toString(), StarshipResults.class);

            for (Starship sh : results.getResults()) {
                HashMap<String, String> map = new HashMap<>();

                map.put("display", sh.getDisplayName());
                returnList.add(map);
            }
        } else if (category.toLowerCase().equals("vehicles")) {
            VehicleResults results = gson.fromJson(json.toString(), VehicleResults.class);

            for (Vehicle v : results.getResults()) {
                HashMap<String, String> map = new HashMap<>();

                map.put("display", v.getDisplayName());
                returnList.add(map);
            }
        }

        return returnList;
    }
}
