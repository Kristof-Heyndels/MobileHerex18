package be.kristofheyndels.mobdev.factory;

import be.kristofheyndels.mobdev.helpers.Categories;
import be.kristofheyndels.mobdev.model.SwapiObject;

public class DetailsFactory {
    public Details buildDetails(SwapiObject swapiObject) {
        if (Categories.selected == Categories.SelectedCategory.Films) {
            return new FilmDetails(swapiObject);
        } else if (Categories.selected == Categories.SelectedCategory.People) {
            return new PeopleDetails(swapiObject);
        } else if (Categories.selected == Categories.SelectedCategory.Planets) {
            return new PlanetDetails(swapiObject);
        }else if (Categories.selected == Categories.SelectedCategory.Species) {
            return new SpeciesDetails(swapiObject);
        }else if (Categories.selected == Categories.SelectedCategory.Starships) {
            return new StarshipDetails(swapiObject);
        }else if (Categories.selected == Categories.SelectedCategory.Vehicles) {
            return new VehicleDetails(swapiObject);
        }
            return null;
    }
}
