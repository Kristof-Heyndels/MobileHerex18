package Model;

import java.util.Date;
import java.util.HashMap;

public class Film extends SwapiObject {

    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private Date release_date;
    private HashMap<String, String> species;
    private HashMap<String, String> starships;
    private HashMap<String, String> vehicles;
    private HashMap<String, String> characters;
    private HashMap<String, String> planets;
    private String created;
    private String edited;

    public Film(String url) {
        super(url);
    }

    public String getTitle() {
        return title;
    }

    public int getEpisode_id() {
        return episode_id;
    }

    public String getOpening_crawl() {
        return opening_crawl;
    }

    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public HashMap<String, String> getSpecies() {
        return species;
    }

    public HashMap<String, String> getStarships() {
        return starships;
    }

    public HashMap<String, String> getVehicles() {
        return vehicles;
    }

    public HashMap<String, String> getCharacters() {
        return characters;
    }

    public HashMap<String, String> getPlanets() {
        return planets;
    }

    public String getCreated() {
        return created;
    }

    public String getEdited() {
        return edited;
    }
}
