package com.example.sunshinemvp.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.sunshinemvp.forecastActivity.ForecastFragment;
import com.example.sunshinemvp.models.ForecastResult;

import java.util.ArrayList;

public class FragmentForecastAdapter extends FragmentStateAdapter {

    ArrayList<ForecastResult> weatherArrayList = new ArrayList<>();

    public FragmentForecastAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<ForecastResult> weatherArrayList) {
        super(fragmentActivity);
        this.weatherArrayList = weatherArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       Fragment fragment = new ForecastFragment();
       Bundle args = new Bundle();
       args.putSerializable("weather", weatherArrayList.get(position));
       fragment.setArguments(args);
       return fragment;
    }

    @Override
    public int getItemCount() {
        return weatherArrayList.size();
    }


}
