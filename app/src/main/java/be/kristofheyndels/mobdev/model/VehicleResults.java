package be.kristofheyndels.mobdev.model;

import java.util.ArrayList;
import java.util.List;

public class VehicleResults {
    private List<Vehicle> results = new ArrayList<>();

    public List<Vehicle> getResults() {
        return results;
    }

    public void setResults(List<Vehicle> results) {
        this.results = results;
    }
}
