package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import be.kristofheyndels.mobdev.model.Planet;

@Dao
public interface PlanetDao {
    @Insert
    void insert(Planet planet);

    @Query("SELECT * FROM planets WHERE url = :url")
    Planet get(String url);

    @Delete
    void delete(Planet planet);
}
