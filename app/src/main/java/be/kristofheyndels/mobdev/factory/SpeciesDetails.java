package be.kristofheyndels.mobdev.factory;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import be.kristofheyndels.mobdev.helpers.BookmarkObserver;
import be.kristofheyndels.mobdev.model.JsonCallBack;
import be.kristofheyndels.mobdev.model.SWAPI;
import be.kristofheyndels.mobdev.model.Film;
import be.kristofheyndels.mobdev.model.Person;
import be.kristofheyndels.mobdev.model.Planet;
import be.kristofheyndels.mobdev.model.Species;
import be.kristofheyndels.mobdev.model.SwapiObject;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;

public class SpeciesDetails extends AbstractDetails  {
    private Species species;

    public SpeciesDetails(SwapiObject swapiObject) {
        super(swapiObject);
        species = (Species) swapiObject;
    }

    public SpeciesDetails(SwapiObject swapiObject, BookmarkObserver observer) {
        super(swapiObject, observer);
        species = (Species) swapiObject;
    }

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        super.generateLayout(detailFragment);
        super.checkIfBookmarked(species);
        mLayoutInflater.inflate(R.layout.fragment_detail_species, layout);

        ((TextView) layout.findViewById(R.id.tv_name)).setText(species.getName());
        ((TextView) layout.findViewById(R.id.tv_classification)).setText(species.getClassification());
        ((TextView) layout.findViewById(R.id.tv_designation)).setText(species.getDesignation());
        ((TextView) layout.findViewById(R.id.tv_average_height)).setText(species.getAverage_height());
        ((TextView) layout.findViewById(R.id.tv_average_lifespan)).setText(species.getAverage_lifespan());
        ((TextView) layout.findViewById(R.id.tv_eye_colours)).setText(species.getEye_colors());
        ((TextView) layout.findViewById(R.id.tv_hair_colours)).setText(species.getHair_colors());
        ((TextView) layout.findViewById(R.id.tv_skin_colours)).setText(species.getSkin_colors());
        ((TextView) layout.findViewById(R.id.tv_language)).setText(species.getLanguage());

        SWAPI.getResultsFromURL(detailFragment.getContext(), species.getHomeworld(), new JsonCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                Gson gson = new Gson();
                SwapiObject o = gson.fromJson(result.toString(), Planet.class);
                ((TextView) layout.findViewById(R.id.tv_homeworld)).append(o.getDisplayName());
            }
        });

        LinearLayout peopleListLayout = layout.findViewById(R.id.ll_people);
        createListFromUrlArray(detailFragment.getContext(), species.getPeople(), peopleListLayout, Person.class);

        LinearLayout filmsListLayout = layout.findViewById(R.id.ll_films);
        createListFromUrlArray(detailFragment.getContext(), species.getFilms(), filmsListLayout, Film.class);
    }

    @Override
    protected void onBookmarkClick(View btn) {
        super.onBookmarkClick(btn);

        if (isBookmarked) {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.speciesDao().insert(species);
                    notifyObservers();
                }
            });
        } else {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.speciesDao().delete(species);
                    notifyObservers();
                }
            });
        }
    }
}
