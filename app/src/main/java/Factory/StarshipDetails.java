package Factory;

import android.view.ViewGroup;

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
        mLayoutInflater.inflate(R.layout.fragment_detail_starships, (ViewGroup) layout.findViewById(R.id.details_relative_layout));
    }
}
