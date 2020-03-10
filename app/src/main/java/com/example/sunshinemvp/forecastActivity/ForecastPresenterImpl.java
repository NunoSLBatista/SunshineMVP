package com.example.sunshinemvp.forecastActivity;

import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherDay;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class ForecastPresenterImpl implements ForecastContract.presenter {

    private ForecastContract.MainView mainView;
    private ArrayList<ForecastResult> dayWeathers = new ArrayList<ForecastResult>();

    public ForecastPresenterImpl(ForecastContract.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setForecastArray(ArrayList<WeatherDay> weatherArrayList) {

        for(int i = 0; i < weatherArrayList.size(); i++){
            if(dayWeathers.size() > 0){
                try {
                    if(checkIfDayExists(weatherArrayList.get(i)) == -1){
                        ForecastResult forecastResult = new ForecastResult();
                        forecastResult.setListWeather(new ArrayList<WeatherDay>());
                        forecastResult.getListWeather().add(weatherArrayList.get(i));
                        forecastResult.setDate(weatherArrayList.get(i).getDate());
                        dayWeathers.add(forecastResult);
                    } else {
                        dayWeathers.get(checkIfDayExists(weatherArrayList.get(i))).getListWeather().add(weatherArrayList.get(i));
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                ForecastResult forecastResult = new ForecastResult();
                forecastResult.setListWeather(new ArrayList<WeatherDay>());
                forecastResult.getListWeather().add(weatherArrayList.get(i));
                forecastResult.setDate(weatherArrayList.get(i).getDate());
                dayWeathers.add(forecastResult);
            }

        }

    }

    @Override
    public ArrayList<ForecastResult> getForecast() {
        return dayWeathers;
    }

    @Override
    public String getTabText(int position) throws ParseException {

        Calendar cal = Calendar.getInstance();
        Calendar calTomorrow = Calendar.getInstance();
        calTomorrow.add(Calendar.DATE, +1);
        if(isDateSame(dayWeathers.get(position).getDateCalendar(), cal)){
           return "Today";
        } else if(isDateSame(dayWeathers.get(position).getDateCalendar(), calTomorrow)){
        return "Tomorrow";
        } else {
           return dayWeathers.get(position).getDateFormated();
        }
    }

    public int checkIfDayExists(WeatherDay weather) throws ParseException {
        for(int j = 0; j < dayWeathers.size(); j++){
            if(isDateSame(dayWeathers.get(j).getDateCalendar(), weather.getDateCalendar())){
                return j;
            }
        }
        return -1;
    }

    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }


}
