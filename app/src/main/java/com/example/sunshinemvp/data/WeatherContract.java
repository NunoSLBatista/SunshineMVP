package com.example.sunshinemvp.data;

import android.provider.BaseColumns;

public class WeatherContract {

    private WeatherContract(){}

    public static class WeatherEntry implements BaseColumns {
        public static final String TABLE_NAME = "weather";
        public static final String COLUMN_CITY_ID = "cityId";
        public static final String COLUMN_WEATHER_TYPE = "weatherType";
        public static final String COLUMN_MAIN_TEMP = "mainTemp";
        public static final String COLUMN_TEMP_MIN = "tempMin";
        public static final String COLUMN_TEMP_MAX = "tempMax";
        public static final String COLUMN_WIND_SPEED = "windSpeed";
        public static final String COLUMN_FEELS_LIKE_TEMP= "feelsLikeTemp";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_SUNRISE = "sunrise";
        public static final String COLUMN_SUNSET = "sunset";
    }

}
