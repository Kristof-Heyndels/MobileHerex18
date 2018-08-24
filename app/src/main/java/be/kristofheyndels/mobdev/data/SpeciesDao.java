package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import be.kristofheyndels.mobdev.model.Species;

@Dao
public interface SpeciesDao {
    @Insert
    void insert(Species species);

    @Query("SELECT * FROM species WHERE url = :url")
    Species get(String url);

    @Delete
    void delete(Species species);
}
