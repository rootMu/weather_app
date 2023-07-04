package uk.rootmu.weatherapp.persistance.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String type;

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
