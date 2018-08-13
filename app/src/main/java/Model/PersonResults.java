package Model;

import java.util.ArrayList;
import java.util.List;

public class PersonResults extends Results{
    private List<Person> results = new ArrayList<>();

    public List<Person> getResults() {
        return results;
    }

    public void setResults(List<Person> results) {
        this.results = results;
    }
}
