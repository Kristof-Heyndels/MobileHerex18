package Factory;

import android.view.ViewGroup;

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
        mLayoutInflater.inflate(R.layout.fragment_detail_vehicles, (ViewGroup) layout.findViewById(R.id.details_relative_layout));
    }
}
