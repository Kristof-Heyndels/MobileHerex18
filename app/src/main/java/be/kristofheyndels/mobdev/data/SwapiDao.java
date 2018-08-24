package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import be.kristofheyndels.mobdev.model.SwapiObject;

@Dao
public interface SwapiDao {
    @Query("SELECT * FROM " +
            "films " +
            "WHERE url = :url")
    SwapiObject get(String url);
}
