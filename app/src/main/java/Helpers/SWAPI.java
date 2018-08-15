package Helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Model.SwapiObject;
import be.kristofheyndels.mobdev.mobileherex18.MainActivity;

public class SWAPI {
    private static final String TAG = "SWAPI";

    public static void getResultsFromURL(final Context myContext, String url, final JsonCallBack jsonCallBack) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(myContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            jsonCallBack.onSuccess(jsonResponse);

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
