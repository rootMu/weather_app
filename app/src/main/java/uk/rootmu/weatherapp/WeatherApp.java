package uk.rootmu.weatherapp;

import android.app.Application;

import uk.rootmu.weatherapp.di.AppComponent;
import uk.rootmu.weatherapp.di.AppModule;
import uk.rootmu.weatherapp.di.DaggerAppComponent;
import uk.rootmu.weatherapp.di.MonthModule;

public class WeatherApp extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        AppModule appModule = new AppModule(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .monthModule(new MonthModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}