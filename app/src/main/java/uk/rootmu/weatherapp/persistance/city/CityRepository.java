package uk.rootmu.weatherapp.persistance.city;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.time.Month;
import java.util.List;

import uk.rootmu.weatherapp.persistance.CityDao;
import uk.rootmu.weatherapp.persistance.MonthDao;
import uk.rootmu.weatherapp.persistance.models.CityEntity;
import uk.rootmu.weatherapp.persistance.models.MonthEntity;

public class CityRepository {
    private MonthDao monthDao;
    private CityDao cityDao;

    public CityRepository(MonthDao monthDao, CityDao cityDao) {
        this.monthDao = monthDao;
        this.cityDao = cityDao;
    }

    public long addCity(CityEntity city) {
        return cityDao.insertCity(city);
    }

    public void addMonths(List<MonthEntity> months) {
        monthDao.insertAllMonths(months);
    }

    public List<MonthEntity> getMonthsForCity(long cityId) {
        return monthDao.getMonthsForCity(cityId);
    }

    public LiveData<List<CityEntity>> getCities() {
        return cityDao.getAllCities();
    }

}
