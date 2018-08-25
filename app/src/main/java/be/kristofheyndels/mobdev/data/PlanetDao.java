package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import be.kristofheyndels.mobdev.model.Planet;
import be.kristofheyndels.mobdev.model.Starship;

@Dao
public interface PlanetDao {
    @Insert
    void insert(Planet planet);

    @Query("SELECT * FROM planets WHERE url = :url")
    Planet get(String url);

    @Query("SELECT * FROM planets")
    List<Planet> getAll();

    @Delete
    void delete(Planet planet);
}
