package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import be.kristofheyndels.mobdev.model.SwapiObject;

@Dao
public interface SwapiDao {
    // We only need to check if entry exists, no need to select all columns
    @Query("select url " +
            "from films " +
            "where films.url = :url " +
            "union " +
            "select url " +
            "from people " +
            "where people.url = :url " +
            "union " +
            "select url " +
            "from planets " +
            "where planets.url = :url " +
            "union " +
            "select url " +
            "from species " +
            "where species.url = :url " +
            "union " +
            "select url " +
            "from starships " +
            "where starships.url = :url " +
            "union " +
            "select url " +
            "from vehicles " +
            "where vehicles.url = :url")
    SwapiObject get(String url);
}
