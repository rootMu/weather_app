package uk.rootmu.weatherapp.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.rootmu.weatherapp.persistance.models.CityEntity;

@Dao
public interface CityDao {
    @Insert
    long insertCity(CityEntity city);

    @Query("SELECT * FROM CityEntity")
    LiveData<List<CityEntity>> getAllCities();
}