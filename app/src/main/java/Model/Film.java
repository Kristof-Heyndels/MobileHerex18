package Model;

import java.util.ArrayList;
import java.util.Date;

public class Film extends SwapiObject{

    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private Date release_date;
    private ArrayList<String> species;
    private ArrayList<String> starships;
    private ArrayList<String> vehicles;
    private ArrayList<String> characters;
    private ArrayList<String> planets;

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

    public ArrayList<String> getSpecies() {
        return species;
    }

    public ArrayList<String> getStarships() {
        return starships;
    }

    public ArrayList<String> getVehicles() {
        return vehicles;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public ArrayList<String> getPlanets() {
        return planets;
    }

    @Override
    public String getDisplayName(){
        return title;
    }
}
