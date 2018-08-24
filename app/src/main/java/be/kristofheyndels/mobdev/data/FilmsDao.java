package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import be.kristofheyndels.mobdev.model.Film;

@Dao
public interface FilmsDao {
    @Insert
    void insert(Film film);

    @Query("SELECT * FROM films WHERE url = :url")
    Film get(String url);

    @Delete
    void delete(Film film);
}
