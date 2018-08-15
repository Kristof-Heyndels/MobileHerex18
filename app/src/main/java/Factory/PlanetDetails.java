package Factory;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import Model.Film;
import Model.Person;
import Model.Planet;
import Model.SwapiObject;
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
        mLayoutInflater.inflate(R.layout.fragment_detail_planets, (ViewGroup) layout.findViewById(R.id.details_relative_layout));

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
}
