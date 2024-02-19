package edu.ucsd.cse110.successorator.data.db.time;
import androidx.room.Entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "time")
public class TimeEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id = null;

    @ColumnInfo(name = "year")
    public int year;

    @ColumnInfo(name = "month")
    public int month;

    @ColumnInfo(name = "day_of_month")
    public int dayOfMonth;

    @ColumnInfo(name = "hour")
    public int hour;

    TimeEntity(@NonNull int year, int month, int dayOfMonth, int hour) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.hour = hour;
    }

    // turns Goal object into GoalEntity so that it can be added to database
    public static TimeEntity fromTime(LocalDateTime localDateTime) {
        var entity = new TimeEntity(
                localDateTime.getYear(),
                localDateTime.getMonthValue(),
                localDateTime.getDayOfMonth(),
                localDateTime.getHour()
                );
        entity.id = 0;
        return entity;
    }

    // turns GoalEntity into Goal object
    public @NonNull LocalDateTime toTime() {
        return LocalDateTime.now()
                .withYear(year)
                .withMonth(month)
                .withDayOfMonth(dayOfMonth)
                .withHour(hour);
    }
}
