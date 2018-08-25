package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import be.kristofheyndels.mobdev.model.Starship;

@Dao
public interface StarshipDao {
    @Insert
    void insert(Starship starship);

    @Query("SELECT * FROM starships WHERE url = :url")
    Starship get(String url);

    @Query("SELECT * FROM starships")
    List<Starship> getAll();

    @Delete
    void delete(Starship starship);
}
