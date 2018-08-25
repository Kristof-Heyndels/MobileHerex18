package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import be.kristofheyndels.mobdev.model.Starship;
import be.kristofheyndels.mobdev.model.Vehicle;

@Dao
public interface VehicleDao {
    @Insert
    void insert(Vehicle vehicle);

    @Query("SELECT * FROM vehicles WHERE url = :url")
    Vehicle get(String url);

    @Query("SELECT * FROM vehicles")
    List<Vehicle> getAll();

    @Delete
    void delete(Vehicle vehicle);
}
