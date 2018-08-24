package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import be.kristofheyndels.mobdev.model.Vehicle;

@Dao
public interface VehicleDao {
    @Insert
    void insert(Vehicle vehicle);

    @Query("SELECT * FROM vehicles WHERE url = :url")
    Vehicle get(String url);

    @Delete
    void delete(Vehicle vehicle);
}
