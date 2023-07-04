package uk.rootmu.weatherapp.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import uk.rootmu.weatherapp.WeatherApp;

@Module
public class AppModule {
    private final Context context;
    private final WeatherApp application;

    public AppModule(WeatherApp application) {
        this.context = application;
        this.application = application;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    WeatherApp provideApplication() {
        return application;
    }
}
