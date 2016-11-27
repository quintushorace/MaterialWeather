package com.ape.material.weather.data;

import android.util.Log;

import com.ape.material.weather.App;
import com.ape.material.weather.BuildConfig;
import com.ape.material.weather.api.Api;
import com.ape.material.weather.bean.HeWeather;
import com.ape.material.weather.util.DeviceUtil;
import com.ape.material.weather.util.DiskLruCacheUtil;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static com.ape.material.weather.util.DeviceUtil.hasInternet;

/**
 * Created by way on 2016/11/26.
 */

public class WeatherUtil {
    private static final String TAG = "WeatherUtil";
    // wifi缓存时间为5分钟
    private static final long WIFI_CACHE_TIME = 5 * 60 * 1000;
    // 其他网络环境为1小时
    private static final long OTHER_CACHE_TIME = 60 * 60 * 1000;
    private static final String LANG = "zh-cn";

    public static Observable<HeWeather> getLocalWeather(final String city, final boolean force) {
        return Observable.create(new Observable.OnSubscribe<HeWeather>() {

            @Override
            public void call(Subscriber<? super HeWeather> subscriber) {
                HeWeather weather = (HeWeather) DiskLruCacheUtil.getInstance(App.getContext())
                        .readObject(city);
                if (weather != null && weather.isOK())
                    subscriber.onNext(weather);
                subscriber.onCompleted();
            }
        }).filter(new Func1<HeWeather, Boolean>() {
            @Override
            public Boolean call(HeWeather weather) {
                if (!hasInternet()) return true;//如果没有网，直接使用缓存
                if (force) return false;//如果强制刷新数据
                //判断是否缓存超时
                return !isCacheFailure(weather);
            }
        });
    }

    public static Observable<HeWeather> getRemoteWeather(final String city) {
        return Api.getInstance().getWeather(BuildConfig.HEWEATHER_KEY, city, LANG)
                .filter(new Func1<HeWeather, Boolean>() {
                    @Override
                    public Boolean call(HeWeather weather) {
                        return weather != null && weather.isOK();
                    }
                }).map(new Func1<HeWeather, HeWeather>() {
                    @Override
                    public HeWeather call(HeWeather weather) {
                        weather.setUpdateTime(System.currentTimeMillis());
                        DiskLruCacheUtil.getInstance(App.getContext()).saveObject(city, weather);
                        return weather;
                    }
                });
    }

    public static boolean isCacheFailure(HeWeather weather) {
        boolean isWifi = DeviceUtil.isWifiOpen();
        long cacheTime = weather.getUpdateTime();

        long existTime = System.currentTimeMillis() - cacheTime;
        boolean failure;
        if (isWifi) {
            failure = existTime > WIFI_CACHE_TIME;
        } else {
            failure = existTime > OTHER_CACHE_TIME;
        }
        Log.i(TAG, "existTime = " + existTime / 1000 + "s, isOverTime = " + failure);
        return failure;
    }
}