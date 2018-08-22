package Factory;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import Model.Film;
import Model.Person;
import Model.SwapiObject;
import Model.Vehicle;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;

public class VehicleDetails extends AbstractDetails {
    private Vehicle vehicle;

    public VehicleDetails(SwapiObject swapiObject) {
        vehicle = (Vehicle) swapiObject;
    }

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        super.generateLayout(detailFragment);
        mLayoutInflater.inflate(R.layout.fragment_detail_vehicles, layout);

        ((TextView) layout.findViewById(R.id.tv_name)).setText(vehicle.getName());
        ((TextView) layout.findViewById(R.id.tv_model)).setText(vehicle.getModel());
        ((TextView) layout.findViewById(R.id.tv_vehicle_class)).setText(vehicle.getVehicle_class());
        ((TextView) layout.findViewById(R.id.tv_manufacturer)).setText(vehicle.getManufacturer());
        ((TextView) layout.findViewById(R.id.tv_cost_in_credits)).setText(vehicle.getCost_in_credits());
        ((TextView) layout.findViewById(R.id.tv_length)).setText(vehicle.getLength());
        ((TextView) layout.findViewById(R.id.tv_crew)).setText(vehicle.getCrew());
        ((TextView) layout.findViewById(R.id.tv_passengers)).setText(vehicle.getPassengers());
        ((TextView) layout.findViewById(R.id.tv_max_atmosphering_speed)).setText(vehicle.getMax_atmosphering_speed());
        ((TextView) layout.findViewById(R.id.tv_cargo_capacity)).setText(vehicle.getCargo_capacity());
        ((TextView) layout.findViewById(R.id.tv_consumables)).setText(vehicle.getConsumable());

        LinearLayout filmsListLayout = layout.findViewById(R.id.ll_films);
        createListFromUrlArray(detailFragment.getContext(), vehicle.getFilms(), filmsListLayout, Film.class);

        LinearLayout pilotListLayout = layout.findViewById(R.id.ll_pilots);
        createListFromUrlArray(detailFragment.getContext(), vehicle.getPilots(), pilotListLayout, Person.class);
    }
}
