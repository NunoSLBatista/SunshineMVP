package com.example.sunshinemvp.forecastActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunshinemvp.R;
import com.example.sunshinemvp.adapters.ForecastAdapter;
import com.example.sunshinemvp.models.ForecastResult;

public class ForecastFragment extends Fragment {

    RecyclerView forecastRecycler;
    ForecastAdapter forecastAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_forecast, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forecastRecycler = view.findViewById(R.id.forecastRecycler2);
        assert getArguments() != null;
        ForecastResult weatherArrayList = (ForecastResult) getArguments().getSerializable("weather");
        forecastAdapter = new ForecastAdapter(weatherArrayList.getListWeather(), getContext());
        forecastRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        forecastRecycler.setAdapter(forecastAdapter);

    }

}

