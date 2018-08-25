package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import be.kristofheyndels.mobdev.model.Species;
import be.kristofheyndels.mobdev.model.Starship;

@Dao
public interface SpeciesDao {
    @Insert
    void insert(Species species);

    @Query("SELECT * FROM species WHERE url = :url")
    Species get(String url);

    @Query("SELECT * FROM species")
    List<Species> getAll();

    @Delete
    void delete(Species species);
}
