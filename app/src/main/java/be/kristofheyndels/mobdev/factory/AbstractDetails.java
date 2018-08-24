package be.kristofheyndels.mobdev.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import be.kristofheyndels.mobdev.data.DetailsRoomDatabase;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.MainActivity;
import be.kristofheyndels.mobdev.mobileherex18.R;
import be.kristofheyndels.mobdev.model.JsonCallBack;
import be.kristofheyndels.mobdev.model.SWAPI;
import be.kristofheyndels.mobdev.model.SwapiObject;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public abstract class AbstractDetails implements Details {
    protected ScrollView layout;

    LayoutInflater mLayoutInflater;

    Button btnBookmark;
    Boolean isBookmarked = false;

    // Vars for Room queries
    DetailsRoomDatabase db = DetailsRoomDatabase.getDatabase(MainActivity.appContext);
    Executor mExecutor = Executors.newSingleThreadExecutor();

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        layout = detailFragment.getView().findViewById(R.id.details_scroll_view);
        layout.removeView(layout.findViewById(R.id.inflated_details));

        mLayoutInflater = (LayoutInflater) detailFragment.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        btnBookmark = ((ViewGroup) layout.getParent()).findViewById(R.id.btn_bookmark);
        btnBookmark.setVisibility(View.VISIBLE);
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn) {
                onBookmarkClick(btn);
            }
        });
    }

    void createListFromUrlArray(final Context c, final List<String> urlList, final LinearLayout ll, final Type type) {
        final ArrayList<String> displayList = new ArrayList<>();

        for (String s : urlList) {
            SWAPI.getResultsFromURL(c, s, new JsonCallBack() {
                @Override
                public void onSuccess(JSONObject result) {
                    Gson gson = new Gson();
                    SwapiObject o = gson.fromJson(result.toString(), type);
                    displayList.add(o.getDisplayName());

                    if (displayList.size() == urlList.size()) {
                        Collections.sort(displayList);
                        buildTextViews(c, ll, displayList);
                    }
                }
            });
        }
    }

    private void buildTextViews(Context c, LinearLayout parent, List<String> stringList) {
        for (String item : stringList) {
            TextView tv = new TextView(c);
            tv.setText(item);
            parent.addView(tv);
        }
    }

    protected void onBookmarkClick(View view) {
        isBookmarked = !isBookmarked;
        updateBookmarkButton();
    }

    protected void checkIfBookmarked(final SwapiObject swo) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                SwapiObject s = db.swapiDao().get(swo.getUrl());

                if (s != null) {
                    isBookmarked = true;
                } else {
                    isBookmarked = false;
                }
                updateBookmarkButton();
            }
        });
    }

    private void updateBookmarkButton() {
        MainActivity.appActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isBookmarked) {
                    btnBookmark.setBackgroundResource(R.mipmap.bookmark_selected);
                } else {
                    btnBookmark.setBackgroundResource(R.mipmap.bookmark_unselected);
                }
            }
        });
    }
}