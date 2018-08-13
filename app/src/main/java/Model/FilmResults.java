package Model;

import java.util.ArrayList;
import java.util.List;

public class FilmResults extends Results {

    private List<Film> results = new ArrayList<>();

    public List<Film> getResults() {
        return results;
    }

    public void setResults(List<Film> resultList) {
        this.results = resultList;
    }
}
