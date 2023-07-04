package uk.rootmu.weatherapp.persistance.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String type;
}
