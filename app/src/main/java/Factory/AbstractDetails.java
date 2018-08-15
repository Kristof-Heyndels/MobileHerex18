package Factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Helpers.JsonCallBack;
import Helpers.SWAPI;
import Model.Film;
import Model.Person;
import Model.Planet;
import Model.Species;
import Model.Starship;
import Model.SwapiObject;
import Model.Vehicle;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AbstractDetails implements Details {
    protected ScrollView layout;
    protected LayoutInflater mLayoutInflater;

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        layout = detailFragment.getView().findViewById(R.id.details_relative_layout);
        layout.removeView(layout.findViewById(R.id.inflated_details));

        mLayoutInflater = (LayoutInflater) detailFragment.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
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

    private void buildTextViews(Context c, LinearLayout parent, List<String> stringList) {

        for (String item : stringList) {
            TextView tv = new TextView(c);
            tv.setText(item);
            parent.addView(tv);
        }
    }
}
