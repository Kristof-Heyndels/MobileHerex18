package be.kristofheyndels.mobdev.factory;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import be.kristofheyndels.mobdev.model.Film;
import be.kristofheyndels.mobdev.model.Person;
import be.kristofheyndels.mobdev.model.Planet;
import be.kristofheyndels.mobdev.model.SwapiObject;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;

public class PlanetDetails extends AbstractDetails {
    private Planet planet;

    public PlanetDetails(SwapiObject swapiObject) {
        planet = (Planet) swapiObject;
    }

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        super.generateLayout(detailFragment);
        super.checkIfBookmarked(planet);
        mLayoutInflater.inflate(R.layout.fragment_detail_planets, layout);

        ((TextView) layout.findViewById(R.id.tv_name)).setText(planet.getName());
        ((TextView) layout.findViewById(R.id.tv_diameter)).setText(planet.getDiameter());
        ((TextView) layout.findViewById(R.id.tv_rotation_period)).setText(planet.getRotation_period());
        ((TextView) layout.findViewById(R.id.tv_orbital_period)).setText(planet.getOrbital_period());
        ((TextView) layout.findViewById(R.id.tv_gravity)).setText(planet.getGravity());
        ((TextView) layout.findViewById(R.id.tv_population)).setText(planet.getPopulation());
        ((TextView) layout.findViewById(R.id.tv_climate)).setText(planet.getClimate());
        ((TextView) layout.findViewById(R.id.tv_terrain)).setText(planet.getTerrain());
        ((TextView) layout.findViewById(R.id.tv_surface_water)).setText(planet.getSurface_water());

        LinearLayout residentListLayout = layout.findViewById(R.id.ll_residents);
        createListFromUrlArray(detailFragment.getContext(), planet.getResidents(), residentListLayout, Person.class);

        LinearLayout filmsListLayout = layout.findViewById(R.id.ll_films);
        createListFromUrlArray(detailFragment.getContext(), planet.getFilms(), filmsListLayout, Film.class);
    }

    @Override
    protected void onBookmarkClick(View btn) {
        super.onBookmarkClick(btn);

        if (isBookmarked) {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.planetDao().insert(planet);
                }
            });
        } else {
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    db.planetDao().delete(planet);
                }
            });
        }
    }
}
