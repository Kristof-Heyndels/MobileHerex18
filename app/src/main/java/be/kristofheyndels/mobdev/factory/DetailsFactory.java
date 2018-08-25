package be.kristofheyndels.mobdev.factory;

import be.kristofheyndels.mobdev.helpers.BookmarkObserver;
import be.kristofheyndels.mobdev.helpers.Categories;
import be.kristofheyndels.mobdev.model.SwapiObject;

public class DetailsFactory {
    public Details buildDetails(SwapiObject swapiObject, Categories.SelectedCategory selectedCategory) {
        if (selectedCategory == Categories.SelectedCategory.Films) {
            return new FilmDetails(swapiObject);
        } else if (selectedCategory == Categories.SelectedCategory.People) {
            return new PeopleDetails(swapiObject);
        } else if (selectedCategory == Categories.SelectedCategory.Planets) {
            return new PlanetDetails(swapiObject);
        }else if (selectedCategory == Categories.SelectedCategory.Species) {
            return new SpeciesDetails(swapiObject);
        }else if (selectedCategory == Categories.SelectedCategory.Starships) {
            return new StarshipDetails(swapiObject);
        }else if (selectedCategory == Categories.SelectedCategory.Vehicles) {
            return new VehicleDetails(swapiObject);
        }
            return null;
    }
}
