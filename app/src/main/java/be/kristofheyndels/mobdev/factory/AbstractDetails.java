package be.kristofheyndels.mobdev.factory;

import android.arch.persistence.room.TypeConverter;
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
import java.util.Date;
import java.util.List;

import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;
import be.kristofheyndels.mobdev.model.JsonCallBack;
import be.kristofheyndels.mobdev.model.SWAPI;
import be.kristofheyndels.mobdev.model.SwapiObject;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public abstract class AbstractDetails implements Details {
    protected ScrollView layout;

    protected LayoutInflater mLayoutInflater;
    protected Button btnBookmark;
    protected Boolean isBookmarked = false;

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

    protected void createListFromUrlArray(final Context c, final List<String> urlList, final LinearLayout ll, final Type type) {
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

    protected void onBookmarkClick(View btn) {
        isBookmarked = !isBookmarked;
        if (isBookmarked) {
            btn.setBackgroundResource(R.mipmap.bookmark_selected);
        } else {
            btn.setBackgroundResource(R.mipmap.bookmark_unselected);
        }
    }

    private void buildTextViews(Context c, LinearLayout parent, List<String> stringList) {
        for (String item : stringList) {
            TextView tv = new TextView(c);
            tv.setText(item);
            parent.addView(tv);
        }
    }
}