package uk.rootmu.weatherapp.ui.screens;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import uk.rootmu.weatherapp.Months;
import uk.rootmu.weatherapp.persistance.city.CityRepository;
import uk.rootmu.weatherapp.persistance.models.CityEntity;
import uk.rootmu.weatherapp.persistance.models.MonthEntity;


public class CityViewModel extends ViewModel {

    private final CityRepository cityRepository;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<CityEntity> selectedCity = new MutableLiveData<>();
    private final MediatorLiveData<List<MonthEntity>> selectedMonths = new MediatorLiveData<>();
    private final MutableLiveData<Integer> season = new MutableLiveData<>(0);
    public MutableLiveData<String> jan = new MutableLiveData<>();
    public MutableLiveData<String> feb = new MutableLiveData<>();
    public MutableLiveData<String> mar = new MutableLiveData<>();
    public MutableLiveData<String> april = new MutableLiveData<>();
    public MutableLiveData<String> may = new MutableLiveData<>();
    public MutableLiveData<String> june = new MutableLiveData<>();
    public MutableLiveData<String> july = new MutableLiveData<>();
    public MutableLiveData<String> aug = new MutableLiveData<>();
    public MutableLiveData<String> sep = new MutableLiveData<>();
    public MutableLiveData<String> oct = new MutableLiveData<>();
    public MutableLiveData<String> nov = new MutableLiveData<>();
    public MutableLiveData<String> dec = new MutableLiveData<>();
    public MutableLiveData<String> cityName = new MutableLiveData<>();
    public MutableLiveData<String> cityType = new MutableLiveData<>();
    public MediatorLiveData<Boolean> isValid = new MediatorLiveData<>();
    public MediatorLiveData<Float> averageTemp = new MediatorLiveData<>(0.0f);
    private LiveData<List<CityEntity>> cities;

    @Inject
    public CityViewModel(CityRepository repository) {
        this.cityRepository = repository;
        reset();
    }

    public LiveData<Integer> getSeason() {
        return season;
    }

    public void setSeason(Integer seasonId) {
        season.setValue(seasonId);
    }

    public List<MonthEntity> getSelectedMonths() {
        return cityRepository.getMonthsForCity(selectedCity.getValue().id);
    }

    public LiveData<CityEntity> getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(CityEntity city) {
        selectedCity.setValue(city);
    }

    public LiveData<List<CityEntity>> getAllCities() {
        return cities;
    }



    public LiveData<String> getCityName() {
        return cityName;
    }

    public void setCityName(MutableLiveData<String> cityName) {
        this.cityName = cityName;
    }

    public void setCityName(String cityName) {
        this.cityName.setValue(cityName);
    }

    public LiveData<String> getCityType() {
        return cityType;
    }

    public void setCityType(MutableLiveData<String> cityType) {
        this.cityType = cityType;
    }

    public void setCityType(String cityType) {
        this.cityType.setValue(cityType);
    }

    private void reset() {
        cities = cityRepository.getCities();

        cityName.setValue("");
        cityType.setValue("");

        isValid.removeSource(cityName);
        isValid.removeSource(cityType);
        isValid.addSource(cityName, name -> isValid.setValue(!name.isBlank() && !cityType.getValue().isBlank()));
        isValid.addSource(cityType, type -> isValid.setValue(!type.isBlank() && !cityName.getValue().isBlank()));

        selectedMonths.removeSource(selectedCity);
        selectedMonths.addSource(selectedCity, city -> executor.execute(() -> selectedMonths.postValue(cityRepository.getMonthsForCity(city.id))));

        averageTemp.removeSource(selectedMonths);
        averageTemp.removeSource(season);
        averageTemp.addSource(selectedMonths, months -> averageTemp.setValue(obtainAverageTemperature(months, season.getValue()).floatValue()));
        averageTemp.addSource(season, season -> averageTemp.setValue(obtainAverageTemperature(selectedMonths.getValue(), season).floatValue()));

        jan.setValue("");
        feb.setValue("");
        mar.setValue("");
        april.setValue("");
        may.setValue("");
        june.setValue("");
        july.setValue("");
        aug.setValue("");
        sep.setValue("");
        oct.setValue("");
        nov.setValue("");
        dec.setValue("");
    }

    private Double obtainAverageTemperature(List<MonthEntity> months, Integer season) {
        int startMonthIndex = season * 3;
        List<MonthEntity> selectedThreeMonths;

        if (months != null) {
            if (startMonthIndex + 2 < months.size()) {
                // If the three months fall within a single year (i.e., the last month of the season is within the list of all months),
                // get the three months from the starting month index.
                selectedThreeMonths = months.subList(startMonthIndex, startMonthIndex + 3);
            } else {
                // If the three months span two years (i.e., the last month of the season wraps around to the beginning of the year),
                // get the months from the starting month index to the end of the year and then add the remaining months from the beginning of the year.
                selectedThreeMonths = new ArrayList<>(months.subList(startMonthIndex, months.size()));
                selectedThreeMonths.addAll(months.subList(0, (startMonthIndex + 3) % months.size()));
            }
            return averageSeason(selectedThreeMonths);
        } else {
            return 0.0;
        }
    }

    private double averageSeason(List<MonthEntity> months) {
        double totalTemperature = 0.0;
        for (MonthEntity month : months) {
            totalTemperature += month.getTemperature();
        }
        return totalTemperature / months.size();
    }

    public void addCity(Context context) {
        CityEntity city = new CityEntity();
        city.name = cityName.getValue();
        city.type = cityType.getValue();

        executor.execute(() -> {
            long cityId = cityRepository.addCity(city);
            List<MonthEntity> months = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                MonthEntity month = new MonthEntity();
                month.cityId = cityId;
                month.name = Months.getNameFromOrdinal(context, i);
                month.temperature = getTemperatureForMonth(i);
                months.add(month);
            }
            cityRepository.addMonths(months);
        });

        reset();
    }

    private Float getTemperatureForMonth(int month) {
        return switch (month) {
            case 0 -> Float.parseFloat(jan.getValue());
            case 1 -> Float.parseFloat(feb.getValue());
            case 2 -> Float.parseFloat(mar.getValue());
            case 3 -> Float.parseFloat(april.getValue());
            case 4 -> Float.parseFloat(may.getValue());
            case 5 -> Float.parseFloat(june.getValue());
            case 6 -> Float.parseFloat(july.getValue());
            case 7 -> Float.parseFloat(aug.getValue());
            case 8 -> Float.parseFloat(sep.getValue());
            case 9 -> Float.parseFloat(oct.getValue());
            case 10 -> Float.parseFloat(nov.getValue());
            case 11 -> Float.parseFloat(dec.getValue());
            default -> 0.0f;
        };
    }


}
