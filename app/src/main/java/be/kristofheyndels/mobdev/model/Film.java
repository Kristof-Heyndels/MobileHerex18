package be.kristofheyndels.mobdev.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;

import be.kristofheyndels.mobdev.data.DetailsRoomDatabase;
import be.kristofheyndels.mobdev.helpers.RomanNumerals;

@Entity(tableName = "films")
public class Film extends SwapiObject {

    private String title;
    private int episode_id;
    private String opening_crawl;
    private String director;
    private String producer;

    @TypeConverters({DetailsRoomDatabase.class})
    private Date release_date;

    @TypeConverters({DetailsRoomDatabase.class})
    private ArrayList<String> species;

    @TypeConverters({DetailsRoomDatabase.class})
    private ArrayList<String> starships;

    @TypeConverters({DetailsRoomDatabase.class})
    private ArrayList<String> vehicles;

    @TypeConverters({DetailsRoomDatabase.class})
    private ArrayList<String> characters;

    @TypeConverters({DetailsRoomDatabase.class})
    private ArrayList<String> planets;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(int episode_id) {
        this.episode_id = episode_id;
    }

    public String getOpening_crawl() {
        return opening_crawl;
    }

    public void setOpening_crawl(String opening_crawl) {
        this.opening_crawl = opening_crawl;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public ArrayList<String> getSpecies() {
        return species;
    }

    public void setSpecies(ArrayList<String> species) {
        this.species = species;
    }

    public ArrayList<String> getStarships() {
        return starships;
    }

    public void setStarships(ArrayList<String> starships) {
        this.starships = starships;
    }

    public ArrayList<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<String> vehicles) {
        this.vehicles = vehicles;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<String> characters) {
        this.characters = characters;
    }

    public ArrayList<String> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<String> planets) {
        this.planets = planets;
    }

    @Override
    public String getDisplayName() {
        return String.format("Episode %s: %s", RomanNumerals.toRomanNumeral(episode_id), title);
    }
}
