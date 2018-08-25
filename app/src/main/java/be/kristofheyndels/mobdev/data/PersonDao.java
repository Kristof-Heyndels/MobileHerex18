package be.kristofheyndels.mobdev.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import be.kristofheyndels.mobdev.model.Person;
import be.kristofheyndels.mobdev.model.Starship;

@Dao
public interface PersonDao {
    @Insert
    void insert(Person person);

    @Query("SELECT * FROM people WHERE url = :url")
    Person get(String url);

    @Query("SELECT * FROM people")
    List<Person> getAll();

    @Delete
    void delete(Person person);
}
