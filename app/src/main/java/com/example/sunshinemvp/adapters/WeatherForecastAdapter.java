package com.example.sunshinemvp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshinemvp.R;
import com.example.sunshinemvp.models.Weather;
import com.example.sunshinemvp.models.WeatherDay;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherHolder> {

    // List to store all the contact details
    private ArrayList<WeatherDay> weatherList;
    private Context mContext;

    // Counstructor for the Class
    public WeatherForecastAdapter(Context context) {
        this.mContext = context;
    }

    public void setWeatherList(ArrayList<WeatherDay> weatherList){
        this.weatherList = weatherList;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.weather_forecast_item, parent, false);
        return new WeatherHolder(view);
    }

    @Override
    public int getItemCount() {
        return weatherList == null? 0: weatherList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, final int position) {
        final WeatherDay weather = weatherList.get(position);

        String mainTemp = String.format("%.0f", weather.getMain().getTemp()) + "º";

        holder.txtTemp.setText(mainTemp);
        try {
            Calendar cal = weather.getDateCalendar();
            holder.setWeatherTime(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", cal.get(Calendar.MINUTE)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.with(mContext).load("https://openweathermap.org/img/wn/" + weather.getWeatherList().get(0).getIcon() +  "@2x.png").into(holder.iconWeather);

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class WeatherHolder extends RecyclerView.ViewHolder {

        private TextView txtTime;
        private TextView txtTemp;
        private ImageView iconWeather;

        public WeatherHolder(View itemView) {
            super(itemView);

            txtTime = itemView.findViewById(R.id.timeTextView);
            txtTemp = itemView.findViewById(R.id.tempTextView);
            iconWeather = itemView.findViewById(R.id.iconWeather);

        }

        public void setWeatherTime(String time) {
            txtTime.setText(time);
        }

        public void setWeatherTemp(String temp) {
            txtTemp.setText(temp);
        }
    }
}