package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import be.kristofheyndels.mobdev.model.Film;

@Dao
public interface FilmsDao {
    @Insert
    void insertFilm(Film film);

    @Delete
    void deleteFilm(Film film);
}
