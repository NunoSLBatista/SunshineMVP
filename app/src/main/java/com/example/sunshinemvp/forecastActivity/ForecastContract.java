package com.example.sunshinemvp.forecastActivity;

import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherDay;
import com.example.sunshinemvp.models.WeatherResult;

import java.text.ParseException;
import java.util.ArrayList;

public class ForecastContract {

    interface presenter {

        void onDestroy();

        void setForecastArray(ArrayList<WeatherDay> weatherArrayList);

        ArrayList<ForecastResult> getForecast();

        String getTabText(int position) throws ParseException;

    }

    interface MainView {

        void setWeatherData(WeatherResult weatherResult);

    }


}
