package com.example.sunshinemvp.forecastActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.example.sunshinemvp.R;
import com.example.sunshinemvp.adapters.FragmentForecastAdapter;
import com.example.sunshinemvp.adapters.RotationPageTransformer;
import com.example.sunshinemvp.models.ForecastResult;
import com.example.sunshinemvp.models.WeatherDay;
import com.example.sunshinemvp.models.WeatherResult;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.ParseException;
import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity implements ForecastContract.MainView {

    private ViewPager2 viewPager;
    TabLayout tabLayout;
    private FragmentStateAdapter pagerAdapter;

    private ForecastContract.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        presenter = new ForecastPresenterImpl(this);
        ArrayList<WeatherDay> weatherArrayList = (ArrayList<WeatherDay>) getIntent().getSerializableExtra("listForecast");
        presenter.setForecastArray(weatherArrayList);
        initializeViewPagerAndTabLayout();

    }

    public void initializeViewPagerAndTabLayout(){
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        ArrayList<ForecastResult> forecastResults = presenter.getForecast();
        pagerAdapter = new FragmentForecastAdapter(this, forecastResults);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(new RotationPageTransformer());

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, true, new TabLayoutMediator.OnConfigureTabCallback() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                try {
                    tab.setText(presenter.getTabText(position));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        tabLayoutMediator.attach();

    }

    @Override
    public void setWeatherData(WeatherResult weatherResult) {

    }
}
