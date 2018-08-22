package Factory;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import Model.Film;
import Model.Person;
import Model.Starship;
import Model.SwapiObject;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;

public class StarshipDetails extends AbstractDetails {
    private Starship starship;

    public StarshipDetails(SwapiObject swapiObject) {
        starship = (Starship) swapiObject;
    }

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        super.generateLayout(detailFragment);
        mLayoutInflater.inflate(R.layout.fragment_detail_starships, layout);

        ((TextView) layout.findViewById(R.id.tv_name)).setText(starship.getName());
        ((TextView) layout.findViewById(R.id.tv_model)).setText(starship.getModel());
        ((TextView) layout.findViewById(R.id.tv_starship_class)).setText(starship.getStarship_class());
        ((TextView) layout.findViewById(R.id.tv_manufacturer)).setText(starship.getManufacturer());
        ((TextView) layout.findViewById(R.id.tv_cost_in_credits)).setText(starship.getCost_in_credits());
        ((TextView) layout.findViewById(R.id.tv_length)).setText(starship.getLength());
        ((TextView) layout.findViewById(R.id.tv_crew)).setText(starship.getCrew());
        ((TextView) layout.findViewById(R.id.tv_passengers)).setText(starship.getPassengers());
        ((TextView) layout.findViewById(R.id.tv_max_atmosphering_speed)).setText(starship.getMax_atmosphering_speed());
        ((TextView) layout.findViewById(R.id.tv_hyperdrive_rating)).setText(starship.getHyperdrive_rating());
        ((TextView) layout.findViewById(R.id.tv_mglt)).setText(starship.getMGLT());
        ((TextView) layout.findViewById(R.id.tv_cargo_capacity)).setText(starship.getCargo_capacity());
        ((TextView) layout.findViewById(R.id.tv_consumables)).setText(starship.getConsumables());

        LinearLayout filmsListLayout = layout.findViewById(R.id.ll_films);
        createListFromUrlArray(detailFragment.getContext(), starship.getFilms(), filmsListLayout, Film.class);

        LinearLayout pilotListLayout = layout.findViewById(R.id.ll_pilots);
        createListFromUrlArray(detailFragment.getContext(), starship.getPilots(), pilotListLayout, Person.class);
    }
}
