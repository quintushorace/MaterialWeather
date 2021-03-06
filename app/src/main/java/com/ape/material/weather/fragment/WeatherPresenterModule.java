package com.ape.material.weather.fragment;

import com.ape.material.weather.util.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by android on 16-11-25.
 */
@Module
public class WeatherPresenterModule {
    private WeatherContract.View mView;

    WeatherPresenterModule(WeatherContract.View view) {
        mView = view;
    }

    @Provides
    @FragmentScope
    WeatherContract.View getView() {
        return mView;
    }

    @Provides
    @FragmentScope
    WeatherContract.Model provideModel(WeatherModel model) {
        return model;
    }
}
