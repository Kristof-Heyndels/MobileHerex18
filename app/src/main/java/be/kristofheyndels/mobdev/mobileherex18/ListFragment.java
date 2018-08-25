package be.kristofheyndels.mobdev.mobileherex18;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import be.kristofheyndels.mobdev.model.JsonCallBack;
import be.kristofheyndels.mobdev.helpers.MyArrayAdapter;
import be.kristofheyndels.mobdev.model.SWAPI;
import be.kristofheyndels.mobdev.helpers.Categories;
import be.kristofheyndels.mobdev.model.Film;
import be.kristofheyndels.mobdev.model.FilmResults;
import be.kristofheyndels.mobdev.model.Person;
import be.kristofheyndels.mobdev.model.PersonResults;
import be.kristofheyndels.mobdev.model.Planet;
import be.kristofheyndels.mobdev.model.PlanetResults;
import be.kristofheyndels.mobdev.model.Species;
import be.kristofheyndels.mobdev.model.SpeciesResults;
import be.kristofheyndels.mobdev.model.Starship;
import be.kristofheyndels.mobdev.model.StarshipResults;
import be.kristofheyndels.mobdev.model.SwapiObject;
import be.kristofheyndels.mobdev.model.Vehicle;
import be.kristofheyndels.mobdev.model.VehicleResults;

public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    private Spinner dropCategory;
    private ListView lvResults;
    private HashMap<String, SwapiObject> resultMap;
    private List<String> resultList;

    private OnUserSelectionMade mListener;

    private int selectedCategory = 0;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        dropCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                buildListAdapter((String)dropCategory.getSelectedItem());
                mListener.onUserCategorySelectionMade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lvResults = view.findViewById(R.id.lv_results);
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) lvResults.getItemAtPosition(i);
                mListener.onUserListItemSelectionMade(resultMap.get(selectedItem));
            }
        });

        if (savedInstanceState == null) {
            SWAPI.getResultsFromURL(getContext(), MainActivity.URL, new JsonCallBack() {
                @Override
                public void onSuccess(JSONObject result) {
                    Iterator<String> keys = result.keys();

                    try {
                        while (keys.hasNext()) {
                            String name = keys.next();
                            Categories.addEntry(name, result.getString(name));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    populateSpinner();
                }
            });
        } else {
            selectedCategory = savedInstanceState.getInt("selectedItem");
            resultList = savedInstanceState.getStringArrayList("list");

            populateSpinner();
            populateList();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserSelectionMade) {
            mListener = (OnUserSelectionMade) context;
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

        outState.putInt("selectedItem", dropCategory.getSelectedItemPosition());
        outState.putStringArrayList("list", ((MyArrayAdapter) lvResults.getAdapter()).getStringList());
    }

    private void populateSpinner() {
        ArrayAdapter<String> catAdapter = new MyArrayAdapter(getContext(), R.layout.spinner_item, Categories.getKeys());
        catAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        dropCategory.setAdapter(catAdapter);
        dropCategory.setSelection(selectedCategory);
    }

    private void buildListAdapter(final String selectedCategory) {
        //Filling List with selected category items
        SWAPI.getResultsFromURL(getContext(), Categories.getValueFromKey(selectedCategory), new JsonCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
               resultList = buildResultList(selectedCategory, result);
                java.util.Collections.sort(resultList);
                populateList();
            }
        });
    }

    private void populateList() {
        ArrayAdapter adapter = new MyArrayAdapter(getContext(), R.layout.list_item, R.id.tv_list_display, resultList);
        lvResults.setAdapter(adapter);
    }

    private List<String> buildResultList(String category, JSONObject json) {
        List<String> returnList = new ArrayList<>();
        resultMap = new HashMap<>();
        Gson gson = new Gson();

        if (category.toLowerCase().equals("films")) {
            Categories.selected = Categories.SelectedCategory.Films;
            FilmResults results = gson.fromJson(json.toString(), FilmResults.class);

            for (Film f : results.getResults()) {
                returnList.add(f.getDisplayName());
                resultMap.put(f.getDisplayName(), f);
            }
        } else if (category.toLowerCase().equals("people")) {
            Categories.selected = Categories.SelectedCategory.People;
            PersonResults results = gson.fromJson(json.toString(), PersonResults.class);

            for (Person per : results.getResults()) {
                returnList.add(per.getDisplayName());
                resultMap.put(per.getDisplayName(), per);
            }
        } else if (category.toLowerCase().equals("planets")) {
            Categories.selected = Categories.SelectedCategory.Planets;
            PlanetResults results = gson.fromJson(json.toString(), PlanetResults.class);

            for (Planet pla : results.getResults()) {
                returnList.add(pla.getDisplayName());
                resultMap.put(pla.getDisplayName(), pla);
            }
        } else if (category.toLowerCase().equals("species")) {
            Categories.selected = Categories.SelectedCategory.Species;
            SpeciesResults results = gson.fromJson(json.toString(), SpeciesResults.class);

            for (Species sp : results.getResults()) {
                returnList.add(sp.getDisplayName());
                resultMap.put(sp.getDisplayName(), sp);
            }
        } else if (category.toLowerCase().equals("starships")) {
            Categories.selected = Categories.SelectedCategory.Starships;
            StarshipResults results = gson.fromJson(json.toString(), StarshipResults.class);

            for (Starship sh : results.getResults()) {
                returnList.add(sh.getDisplayName());
                resultMap.put(sh.getDisplayName(), sh);
            }
        } else if (category.toLowerCase().equals("vehicles")) {
            Categories.selected = Categories.SelectedCategory.Vehicles;
            VehicleResults results = gson.fromJson(json.toString(), VehicleResults.class);

            for (Vehicle v : results.getResults()) {
                returnList.add(v.getDisplayName());
                resultMap.put(v.getDisplayName(), v);
            }
        }

        return returnList;
    }

    public interface OnUserSelectionMade {
        void onUserListItemSelectionMade(SwapiObject swapiObject);
        void onUserCategorySelectionMade();
    }
}
