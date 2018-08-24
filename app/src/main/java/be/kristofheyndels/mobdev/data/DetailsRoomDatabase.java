package be.kristofheyndels.mobdev.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import be.kristofheyndels.mobdev.model.Film;
import be.kristofheyndels.mobdev.model.Person;
import be.kristofheyndels.mobdev.model.Planet;
import be.kristofheyndels.mobdev.model.Species;
import be.kristofheyndels.mobdev.model.Starship;
import be.kristofheyndels.mobdev.model.Vehicle;

@Database(entities = {Film.class, Person.class, Planet.class, Species.class, Starship.class, Vehicle.class}, version = 3)
public abstract class DetailsRoomDatabase extends RoomDatabase {
    private static DetailsRoomDatabase INSTANCE;

    public abstract SwapiDao swapiDao();

    public abstract FilmsDao filmsDao();
    public abstract PersonDao personDao();
    public abstract PlanetDao planetDao();
    public abstract SpeciesDao speciesDao();
    public abstract StarshipDao starshipDao();
    public abstract VehicleDao vehicleDao();

    public static DetailsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DetailsRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DetailsRoomDatabase.class, "details_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    static public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    static public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
