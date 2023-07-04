package uk.rootmu.weatherapp.persistance.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = CityEntity.class,
        parentColumns = "id",
        childColumns = "cityId",
        onDelete = ForeignKey.CASCADE
))
public class MonthEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(index = true)
    public long cityId;
    public String name;
    public float temperature;
}
