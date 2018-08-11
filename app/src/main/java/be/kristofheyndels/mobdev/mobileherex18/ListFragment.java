package be.kristofheyndels.mobdev.mobileherex18;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Data.Categories;

public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";

    private Spinner dropCategory;
    private static int selectedPosition = 0;

    private Map<String, String> catMap = new HashMap<>();
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

        //Filling Spinner with categories
        dropCategory = view.findViewById(R.id.drop_category);
        populateCategorySpinner(dropCategory);
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

    private void populateCategorySpinner(final Spinner spinner) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MainActivity.URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Iterator<String> keys = jsonResponse.keys();

                            while (keys.hasNext()) {
                                String name = keys.next();
                                Categories.addEntry(name, jsonResponse.getString(name));
                            }

                            ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, Categories.getKeys());
                            catAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                            spinner.setAdapter(catAdapter);
                            spinner.setSelection(selectedPosition);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf(TAG, "Response: " + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
