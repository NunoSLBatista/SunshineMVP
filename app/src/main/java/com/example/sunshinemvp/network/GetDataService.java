package com.example.sunshinemvp.network;

import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("/data/2.5/weather")
    Call<WeatherResult> getWeather(
            @Query("q") String param1, @Query("appid") String param2, @Query("units") String param3);

    @GET("/data/2.5/forecast")
    Call<ForecastResult> getForecast(
            @Query("q") String cityName, @Query("appid") String api, @Query("units") String units);

    @GET("/data/2.5/weather")
    Call<WeatherResult> getWeatherCoord(
            @Query("lat") String latitude, @Query("lon") String longitude,  @Query("appid") String api, @Query("units") String units);

    @GET("/data/2.5/forecast")
    Call<ForecastResult> getForecastCoord(
            @Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String api, @Query("units") String units);


}
