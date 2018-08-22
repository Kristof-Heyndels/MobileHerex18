package Factory;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

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

public class PeopleDetails extends AbstractDetails {
    private Person person;

    public PeopleDetails(SwapiObject swapiObject) {
        person = (Person) swapiObject;
    }

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        super.generateLayout(detailFragment);
        mLayoutInflater.inflate(R.layout.fragment_detail_people, layout);

        ((TextView) layout.findViewById(R.id.tv_name)).setText(person.getName());
        ((TextView) layout.findViewById(R.id.tv_birth_date)).setText(person.getBirth_year());
        ((TextView) layout.findViewById(R.id.tv_eye_colour)).setText(person.getEye_color());
        ((TextView) layout.findViewById(R.id.tv_gender)).setText(person.getGender());
        ((TextView) layout.findViewById(R.id.tv_hair_colour)).setText(person.getHair_color());
        ((TextView) layout.findViewById(R.id.tv_height)).setText(person.getHeight());
        ((TextView) layout.findViewById(R.id.tv_mass)).setText(person.getMass());
        ((TextView) layout.findViewById(R.id.tv_skin_colour)).setText(person.getSkin_color());

        SWAPI.getResultsFromURL(detailFragment.getContext(), person.getHomeworld(), new JsonCallBack() {
            @Override
            public void onSuccess(JSONObject result) {
                Gson gson = new Gson();
                SwapiObject o = gson.fromJson(result.toString(), Planet.class);
                ((TextView) layout.findViewById(R.id.tv_homeworld)).append(o.getDisplayName());
            }
        });

        LinearLayout List = layout.findViewById(R.id.ll_films);
        createListFromUrlArray(detailFragment.getContext(), person.getFilms(), List, Film.class);
        ;

        LinearLayout speciesListLayout = layout.findViewById(R.id.ll_species);
        createListFromUrlArray(detailFragment.getContext(), person.getSpecies(), speciesListLayout, Species.class);

        LinearLayout starshipListLayout = layout.findViewById(R.id.ll_starships);
        createListFromUrlArray(detailFragment.getContext(), person.getStarships(), starshipListLayout, Starship.class);

        LinearLayout vehiclesList = layout.findViewById(R.id.ll_vehicles);
        createListFromUrlArray(detailFragment.getContext(), person.getVehicles(), vehiclesList, Vehicle.class);
    }
}
