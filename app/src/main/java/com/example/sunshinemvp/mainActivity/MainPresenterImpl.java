package com.example.sunshinemvp.mainActivity;

import com.example.sunshinemvp.data.WeatherDbHelper;
import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherDay;
import com.example.sunshinemvp.models.WeatherResult;
import com.example.sunshinemvp.network.MyApplication;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainPresenterImpl implements MainContract.presenter, MainContract.GetWeatherIntractor.OnFinishedListener {

    private MainContract.MainView mainView;
    private MainContract.GetWeatherIntractor getNoticeIntractor;
    private ArrayList<WeatherDay> currentWeatherArrayList = new ArrayList<WeatherDay>();
    private ArrayList<WeatherDay> tomorrowWeatherArrayList = new ArrayList<WeatherDay>();
    private ArrayList<WeatherDay> weatherDayArrayList = new ArrayList<WeatherDay>();

    public MainPresenterImpl(MainContract.MainView mainView, MainContract.GetWeatherIntractor getNoticeIntractor) {
        this.mainView = mainView;
        this.getNoticeIntractor = getNoticeIntractor;
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void requestDataFromServer(String city) {
        if(mainView != null){
            mainView.showProgress();
        }
        getNoticeIntractor.getWeatherList(city, this);
        getNoticeIntractor.getWeatherResult(city, this);
    }

    @Override
    public void requestDataFromServerCities(String city) {
        if(mainView != null){
            mainView.showProgress();
        }
        getNoticeIntractor.getWeatherList(city, this);
        getNoticeIntractor.getWeatherResult(city, this);
    }

    @Override
    public void requestDataFromSeverGps(Double latitude, Double longitude) {
        if(mainView != null){
            mainView.showProgress();
        }
        getNoticeIntractor.getWeatherList(latitude, longitude, this);
        getNoticeIntractor.getWeatherResult(latitude, longitude, this);
    }

    @Override
    public void onFinishedWeather(WeatherResult weatherResult) {
        if(mainView != null && weatherResult != null){
            mainView.setWeatherData(weatherResult);
            WeatherDbHelper dh = new WeatherDbHelper(MyApplication.getAppContext());
            dh.checkWeatherType(weatherResult.getWeatherList().get(0));
            dh.checkCity(weatherResult);
            try {
                if(!dh.checkDates(weatherResult)){
                    dh.addWeather(weatherResult);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            mainView.hideProgress();
            mainView.cityNotFound();
        }
    }

    @Override
    public void onFinishedForecast(ForecastResult forecastResult) { ;
        if(mainView != null && forecastResult != null){
            this.tomorrowWeatherArrayList = new ArrayList<WeatherDay>();
            this.currentWeatherArrayList = new ArrayList<WeatherDay>();
            this.weatherDayArrayList = forecastResult.getListWeather();
            organizeArrays(forecastResult.getCity().getId().toString());
            mainView.setDataToRecyclerView(forecastResult);
            mainView.hideProgress();
        } else {
            mainView.hideProgress();
            mainView.cityNotFound();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(mainView != null){
            mainView.onResponseFailure(t);
            mainView.hideProgress();
        }
    }

    private void organizeArrays(String cityId){

        Calendar cal = getCurrentCalendar();
        for(int i = 0; i < weatherDayArrayList.size(); i++){

            WeatherDbHelper dh = new WeatherDbHelper(MyApplication.getAppContext());
             weatherDayArrayList.get(i).setCityId(Integer.valueOf(cityId));
            try {
                if (!dh.checkDates(weatherDayArrayList.get(i))) {
                    dh.addWeather2(weatherDayArrayList.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


            try {
                if (isDateSame(weatherDayArrayList.get(i).getDateCalendar(), cal)) {
                    currentWeatherArrayList.add(weatherDayArrayList.get(i));
                }

                cal.add(Calendar.DATE, +1);
                if (isDateSame(weatherDayArrayList.get(i).getDateCalendar(), cal)) {
                    tomorrowWeatherArrayList.add(weatherDayArrayList.get(i));
                }
                cal.add(Calendar.DATE, -1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    private Calendar getCurrentCalendar(){
        return Calendar.getInstance();
    }

    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public ArrayList<WeatherDay> getCurrentWeatherDay(){
        return currentWeatherArrayList;
    }

    @Override
    public ArrayList<WeatherDay> getWeatherArrayList(){
        return weatherDayArrayList;
    }

    public ArrayList<WeatherDay> getTomorrowWeatherArrayList(){
        return tomorrowWeatherArrayList;
    }

}
