package uk.rootmu.weatherapp;

import android.content.Context;

public enum Months {
    JANURARY(R.string.january),
    FEBRUARY(R.string.february),
    MARCH(R.string.march),
    APRIL(R.string.april),
    MAY(R.string.may),
    JUNE(R.string.june),
    JULY(R.string.july),
    AUGUST(R.string.august),
    SEPTEMBER(R.string.september),
    OCTOBER(R.string.october),
    NOVEMBER(R.string.november),
    DECEMBER(R.string.december);

    private int nameResourceId;

    Months(int nameResourceId) {
        this.nameResourceId = nameResourceId;
    }

    public static String getNameFromOrdinal(Context context, int month) {
        return context.getString(Months.values()[month].nameResourceId);
    }

}







