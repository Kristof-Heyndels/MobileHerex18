package be.kristofheyndels.mobdev.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SWAPI {
    private static final String TAG = "SWAPI";

    public static void getResultsFromURL(final Context mContext, final String url, final JsonCallBack jsonCallBack) {
        getResultsFromUrl(mContext, url, true, jsonCallBack);
    }

    public static void getResultsFromUrl(final Context mContext, final String url, final boolean cacheResults, final JsonCallBack jsonCallBack) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);

        System.out.println("trying to query with url: " + url);
        if (cacheResults && checkCachedFile(mContext, url)) {
            try {
                String responseString = "";
                String fileName = url.replace('/', '-');

                FileInputStream cachedFile = mContext.openFileInput(fileName);
                InputStreamReader reader = new InputStreamReader(cachedFile);
                BufferedReader bReader = new BufferedReader(reader);
                StringBuffer stringBuffer = new StringBuffer();

                String readString = bReader.readLine();
                while (readString != null) {
                    stringBuffer.append(readString);
                    readString = bReader.readLine();
                }

                responseString = stringBuffer.toString();
                JSONObject jsonResponse = new JSONObject(responseString);
                jsonCallBack.onSuccess(jsonResponse);

                cachedFile.close();
                reader.close();
                bReader.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (cacheResults) {
                                    cacheJsonString(mContext, url, response);
                                }

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

            //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions.
            //Volley does retry for you if you have specified the policy.
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                    20,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    private static void cacheJsonString(Context mContext, String url, String jsonString) {
        try {
            url = url.replace('/', '-');

            File dir = mContext.getCacheDir();
            File cacheFile = new File(dir, url);

            FileOutputStream outputStream = mContext.openFileOutput(cacheFile.getName(), Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (IOException e) {
            // Error while creating file
            Log.wtf(TAG, e.toString());
        }
    }

    private static boolean checkCachedFile(Context mContext, String url) {
        final long DAYINMS = 86400000;
        url = url.replace('/', '-');

        for (String f : mContext.fileList()) {
            if (f.equals(url)) {
                File file = new File(url);
                return true;

                /*
                long lastModDate = file.lastModified();
                // If current time has exceeded a day since file modification
                if (lastModDate + DAYINMS < System.currentTimeMillis()) {
                    file.delete();
                    return false;
                }
                */
            }
        }
        return false;
    }
}
