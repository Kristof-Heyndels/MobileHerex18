package be.kristofheyndels.mobdev.factory;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import be.kristofheyndels.mobdev.helpers.BookmarkObserver;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;
import be.kristofheyndels.mobdev.model.Film;
import be.kristofheyndels.mobdev.model.Person;
import be.kristofheyndels.mobdev.model.Planet;
import be.kristofheyndels.mobdev.model.Species;
import be.kristofheyndels.mobdev.model.Starship;
import be.kristofheyndels.mobdev.model.SwapiObject;
import be.kristofheyndels.mobdev.model.Vehicle;

public class FilmDetails extends AbstractDetails {
    private Film film;

    public FilmDetails(SwapiObject swapiObject) {
        super(swapiObject);
        film = (Film) swapiObject;
    }

    public FilmDetails(SwapiObject swapiObject, BookmarkObserver observer) {
        super(swapiObject, observer);
        film = (Film) swapiObject;
    }

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        super.generateLayout(detailFragment);
        super.checkIfBookmarked(film);
        mLayoutInflater.inflate(R.layout.fragment_detail_films, layout);

        ((TextView) layout.findViewById(R.id.tv_title)).setText(film.getTitle());
        ((TextView) layout.findViewById(R.id.tv_episode_id)).setText(Integer.toString(film.getEpisode_id()));
        ((TextView) layout.findViewById(R.id.tv_director)).setText(film.getDirector());
        ((TextView) layout.findViewById(R.id.tv_producer)).setText(film.getProducer());

        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        ((TextView) layout.findViewById(R.id.tv_release_date)).setText(df.format(film.getRelease_date()));

        LinearLayout speciesListLayout = layout.findViewById(R.id.ll_species);
        createListFromUrlArray(detailFragment.getContext(), film.getSpecies(), speciesListLayout, Species.class);

        LinearLayout starshipListLayout = layout.findViewById(R.id.ll_starships);
        createListFromUrlArray(detailFragment.getContext(), film.getStarships(), starshipListLayout, Starship.class);

        LinearLayout vehiclesListLayout = layout.findViewById(R.id.ll_vehicles);
        createListFromUrlArray(detailFragment.getContext(), film.getVehicles(), vehiclesListLayout, Vehicle.class);

        LinearLayout characterListLayout = layout.findViewById(R.id.ll_characters);
        createListFromUrlArray(detailFragment.getContext(), film.getCharacters(), characterListLayout, Person.class);

        LinearLayout planetListLayout = layout.findViewById(R.id.ll_planets);
        createListFromUrlArray(detailFragment.getContext(), film.getPlanets(), planetListLayout, Planet.class);

        ((TextView) layout.findViewById(R.id.tv_opening_crawl)).setText(film.getOpening_crawl());
    }

    @Override
    protected void onBookmarkClick(View btn) {
        super.onBookmarkClick(btn);

        if (isBookmarked) {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.filmsDao().insert(film);
                }
            });
        } else {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.filmsDao().delete(film);
                }
            });
        }

        notifyObservers();
    }
}
