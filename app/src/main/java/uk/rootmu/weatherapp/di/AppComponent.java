package uk.rootmu.weatherapp.di;

import javax.inject.Singleton;

import dagger.Component;
import uk.rootmu.weatherapp.ui.screens.home.HomeFragment;
import uk.rootmu.weatherapp.ui.screens.settings.SettingsFragment;

@Singleton
@Component(modules={AppModule.class, MonthModule.class, ViewModelModule.class})
public interface AppComponent {
    void inject(HomeFragment homeFragment);
    void inject(SettingsFragment settingsFragment);
}
