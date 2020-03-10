package com.example.sunshinemvp.mainActivity;

import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherDay;
import com.example.sunshinemvp.models.WeatherResult;

import java.util.ArrayList;

public class MainContract {


    interface presenter{

        void onDestroy();

        void requestDataFromServer(String city);

        void requestDataFromServerCities(String city);

        void requestDataFromSeverGps(Double latitude, Double longitude);

        ArrayList<WeatherDay> getWeatherArrayList();

        ArrayList<WeatherDay> getCurrentWeatherDay();

        ArrayList<WeatherDay> getTomorrowWeatherArrayList();
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void cityNotFound();

        void setDataToRecyclerView(ForecastResult forecastResult);

        void setWeatherData(WeatherResult weatherResult);

        void onResponseFailure(Throwable throwable);

    }

    interface GetWeatherIntractor {

        interface OnFinishedListener {
            void onFinishedWeather(WeatherResult weatherResultArrayList);
            void onFinishedForecast(ForecastResult weatherResultArrayList);
            void onFailure(Throwable t);
        }

        void getWeatherResult(String city, OnFinishedListener onFinishedListener);
        void getWeatherResult(Double latitude, Double longitude, OnFinishedListener onFinishedListener);
        void getWeatherList(String city, OnFinishedListener onFinishedListener);
        void getWeatherList(Double latitude, Double longitude, OnFinishedListener onFinishedListener);
    }

}
