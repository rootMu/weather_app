package uk.rootmu.weatherapp.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import uk.rootmu.weatherapp.persistance.models.CityEntity;
import uk.rootmu.weatherapp.persistance.models.MonthEntity;

@Database(entities = {CityEntity.class, MonthEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract CityDao cityDao();

    public abstract MonthDao monthDao();
}