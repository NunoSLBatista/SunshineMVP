package com.example.sunshinemvp.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunshinemvp.R;
import com.example.sunshinemvp.adapters.WeatherForecastAdapter;
import com.example.sunshinemvp.forecastActivity.ForecastActivity;
import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherResult;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    TextView weatherTypeTextView;
    TextView tempTextView;
    TextView minMaxTempTextView;
    ImageView weatherIcon;
    TextView feelsLikeTextView;
    RecyclerView weatherHourRecycler;
    TextView sunriseTextView;
    TextView sunsetTextView;
    TextView humidityTextView;
    TextView todayBox;
    TextView tomorrowBox;
    TextView next5DaysBox;
    TextView windTextView;
    TextView pressureTextView;
    TextView cityTextView;
    WeatherForecastAdapter weatherForecastAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    Toolbar toolbar;
    EditText citySearch;

    private MainContract.presenter presenter;
    private final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMainData();
        initializeToolbarAndRecyclerView();
        initializeClickEvents();
        initializeGps();

        initProgressBar();

        presenter = new MainPresenterImpl(this, new GetWeatherIntractorImpl());
        presenter.requestDataFromServer("Porto");
    }


    private void initializeClickEvents(){
        todayBox.setOnClickListener(v -> {
            weatherForecastAdapter.setWeatherList(presenter.getCurrentWeatherDay());
            weatherForecastAdapter.notifyDataSetChanged();

        });

        tomorrowBox.setOnClickListener(v -> {
            weatherForecastAdapter.setWeatherList(presenter.getTomorrowWeatherArrayList());
            weatherForecastAdapter.notifyDataSetChanged();
        });

        next5DaysBox.setOnClickListener(v -> {
            Intent forecastActivity = new Intent(getApplicationContext(), ForecastActivity.class);
            forecastActivity.putExtra("listForecast", presenter.getWeatherArrayList());
            startActivity(forecastActivity);
        });
    }

    private void initializeMainData(){
        weatherTypeTextView =  findViewById(R.id.modeTextView);
        tempTextView =  findViewById(R.id.tempTextView);
        minMaxTempTextView =  findViewById(R.id.minMaxTemp);
        weatherIcon =  findViewById(R.id.weatherModeIcon);
        feelsLikeTextView =  findViewById(R.id.feelLikeTemp);
        weatherHourRecycler =  findViewById(R.id.forecastWeather);
        sunriseTextView =  findViewById(R.id.sunriseTxt);
        sunsetTextView =  findViewById(R.id.sunsetTxt);
        humidityTextView =  findViewById(R.id.humidityTextView);
        todayBox =  findViewById(R.id.todayBox);
        tomorrowBox =  findViewById(R.id.tommorowBox);
        next5DaysBox =  findViewById(R.id.next5Days);
        pressureTextView =  findViewById(R.id.pressureText);
        cityTextView = findViewById(R.id.cityTextView);
        windTextView =  findViewById(R.id.windTxt);
         toolbar = findViewById(R.id.tool_bar);
        citySearch = findViewById(R.id.autocomplete_country);
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeToolbarAndRecyclerView() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.forecastWeather);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
    }


    private void initializeGps(){
        requestLocationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("No permission", "locaiton");
            return;
        }
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert lm != null;
        lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1000, locationListener);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


    /**
     * Initializing progressbar programmatically
     * */
    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void cityNotFound() {
        citySearch.setText("");
        Toast.makeText(getApplicationContext(), "Cidade não encontrada", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDataToRecyclerView(ForecastResult forecastResult) {
        weatherForecastAdapter = new WeatherForecastAdapter(getApplicationContext());
        weatherForecastAdapter.setWeatherList(presenter.getCurrentWeatherDay());
        recyclerView.setAdapter(weatherForecastAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            presenter.requestDataFromServer(citySearch.getText().toString());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Log.d("latidue", latitude + "");

            presenter.requestDataFromSeverGps(latitude, longitude);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void setWeatherData(WeatherResult weatherResult) {
        String temp = String.format(Locale.ENGLISH, "%.0f", weatherResult.getMain().getTemp()) + "º";
        String maxTemp = weatherResult.getMain().getTempMax().toString() + "º";
        String minTemp = weatherResult.getMain().getTempMin().toString() + "º";
        String feelLikeTemp = "Feels like " + weatherResult.getMain().getFeelsLike().toString() + "º";
        String minMaxTemp = minTemp + " / " + maxTemp;
        String humidity = weatherResult.getMain().getHumidity() + "%";
        String wind = weatherResult.getWind().getSpeed().toString();
        String sunrise = weatherResult.getCity().getmSunset();
        String sunset = weatherResult.getCity().getmSunrise();
        String pressure = weatherResult.getMain().getPressure().toString();
        String cityName = weatherResult.getCityName() + ", " + weatherResult.getCity().getCountry();

        weatherTypeTextView.setText(weatherResult.getWeatherList().get(0).getMain());
        minMaxTempTextView.setText(minMaxTemp);
        feelsLikeTextView.setText(feelLikeTemp);
        tempTextView.setText(temp);
        humidityTextView.setText(humidity);
        windTextView.setText(wind);
        sunriseTextView.setText(sunrise);
        sunsetTextView.setText(sunset);
        pressureTextView.setText(pressure);
        cityTextView.setText(cityName);
        Picasso.with(getApplicationContext()).load("https://openweathermap.org/img/wn/" + weatherResult.getWeatherList().get(0).getIcon() +  "@2x.png").into(weatherIcon);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(MainActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
