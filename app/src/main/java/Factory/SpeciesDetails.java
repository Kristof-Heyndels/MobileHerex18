package Factory;

import android.view.ViewGroup;

import Model.Species;
import Model.SwapiObject;
import be.kristofheyndels.mobdev.mobileherex18.DetailFragment;
import be.kristofheyndels.mobdev.mobileherex18.R;

public class SpeciesDetails extends AbstractDetails  {
    private Species species;

    public SpeciesDetails(SwapiObject swapiObject) {
        species = (Species) swapiObject;
    }

    @Override
    public void generateLayout(DetailFragment detailFragment) {
        super.generateLayout(detailFragment);
        mLayoutInflater.inflate(R.layout.fragment_detail_species, (ViewGroup) layout.findViewById(R.id.details_relative_layout));
    }
}
