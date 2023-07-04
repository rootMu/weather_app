package uk.rootmu.weatherapp.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.rootmu.weatherapp.persistance.models.MonthEntity;

@Dao
public interface MonthDao {

    @Insert
    void insertAllMonths(List<MonthEntity> months);

    @Query("SELECT * FROM MonthEntity WHERE cityId = :cityId")
    List<MonthEntity> getMonthsForCity(long cityId);
}