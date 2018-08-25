package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import be.kristofheyndels.mobdev.model.Film;
import be.kristofheyndels.mobdev.model.Starship;

@Dao
public interface FilmsDao{
    @Insert
    void insert(Film film);

    @Query("SELECT * FROM films WHERE url = :url")
    Film get(String url);

    @Query("SELECT * FROM films")
    List<Film> getAll();

    @Delete
    void delete(Film film);
}
