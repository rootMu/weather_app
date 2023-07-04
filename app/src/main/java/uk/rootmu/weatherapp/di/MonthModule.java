package uk.rootmu.weatherapp.di;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import uk.rootmu.weatherapp.persistance.AppDatabase;
import uk.rootmu.weatherapp.persistance.CityDao;
import uk.rootmu.weatherapp.persistance.MonthDao;
import uk.rootmu.weatherapp.persistance.city.CityRepository;


@Module
public class MonthModule {

    @Provides
    AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }

    @Provides
    MonthDao provideMonthDao(AppDatabase appDatabase) {
        return appDatabase.monthDao();
    }

    @Provides
    CityDao provideCityDao(AppDatabase appDatabase) {
        return appDatabase.cityDao();
    }

    @Provides
    CityRepository provideMonthRepository(MonthDao monthDao, CityDao cityDao) {
        return new CityRepository(monthDao, cityDao);
    }

}
