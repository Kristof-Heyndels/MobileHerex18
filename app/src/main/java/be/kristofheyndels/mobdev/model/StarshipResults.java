package be.kristofheyndels.mobdev.model;

import java.util.ArrayList;
import java.util.List;

public class StarshipResults {
    private List<Starship> results = new ArrayList<>();

    public List<Starship> getResults() {
        return results;
    }

    public void setResults(List<Starship> results) {
        this.results = results;
    }
}
