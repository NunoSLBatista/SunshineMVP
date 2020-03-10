package com.example.sunshinemvp.mainActivity;

import androidx.annotation.NonNull;

import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherResult;
import com.example.sunshinemvp.network.GetDataService;
import com.example.sunshinemvp.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetWeatherIntractorImpl implements MainContract.GetWeatherIntractor{

    public static final String API_KEY = "9d7443ba0f8ff7df7afafd12f4006dca";

    @Override
    public void getWeatherResult(String city, final OnFinishedListener onFinishedListener) {
        // Create handle for the RetrofitInstance interface*/
        GetDataService retrofitInterface = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        // Call the method with parameter in the interface to get the notice data
        Call<WeatherResult> call = retrofitInterface.getWeather(city, API_KEY, "Metric");

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResult> call, @NonNull Response<WeatherResult> response) {
                if(response.raw().isSuccessful()){
                    onFinishedListener.onFinishedWeather(response.body());
                } else {
                    onFinishedListener.onFinishedWeather(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResult> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void getWeatherResult(Double latitude, Double longitude, OnFinishedListener onFinishedListener) {
        GetDataService retrofitInterface = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        // Call the method with parameter in the interface to get the notice data
        Call<WeatherResult> call = retrofitInterface.getWeatherCoord(latitude.toString(), longitude.toString(), API_KEY, "Metric");

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResult> call, @NonNull Response<WeatherResult> response) {
                if(response.isSuccessful()){
                    onFinishedListener.onFinishedWeather(response.body());
                } else {
                    onFinishedListener.onFinishedWeather(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResult> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }


    @Override
    public void getWeatherList(String city, OnFinishedListener onFinishedListener) {
        // Create handle for the RetrofitInstance interface*/
        GetDataService retrofitInterface = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        // Call the method with parameter in the interface to get the notice data
        Call<ForecastResult> call = retrofitInterface.getForecast(city, API_KEY, "Metric");

        call.enqueue(new Callback<ForecastResult>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResult> call, @NonNull Response<ForecastResult> response) {
                if(response.raw().isSuccessful()){
                    onFinishedListener.onFinishedForecast(response.body());
                }else {
                    onFinishedListener.onFinishedWeather(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ForecastResult> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getWeatherList(Double latitude, Double longitude, OnFinishedListener onFinishedListener) {
        // Create handle for the RetrofitInstance interface*/
        GetDataService retrofitInterface = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        // Call the method with parameter in the interface to get the notice data
        Call<ForecastResult> call = retrofitInterface.getForecastCoord(latitude.toString(), longitude.toString(), API_KEY, "Metric");

        call.enqueue(new Callback<ForecastResult>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResult> call, @NonNull Response<ForecastResult> response) {
                if(response.raw().code() == 200) {
                    onFinishedListener.onFinishedForecast(response.body());
                }else {
                    onFinishedListener.onFinishedWeather(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<ForecastResult> call, @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

}
