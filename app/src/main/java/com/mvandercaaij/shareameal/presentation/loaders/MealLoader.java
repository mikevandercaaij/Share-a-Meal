package com.mvandercaaij.shareameal.presentation.loaders;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;

import com.mvandercaaij.shareameal.datastorage.NetworkUtils;

public class MealLoader extends AsyncTaskLoader<String> {
    private final static String LOG_TAG = MealLoader.class.getSimpleName();

    public MealLoader(@NonNull Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public String loadInBackground() {
        //log status
        Log.i(LOG_TAG, "loadInBackground: json response given to onLoadFinished (data) -> MainActivity");

        //instantiate NetworkUtils object
        NetworkUtils n = new NetworkUtils();

        //get data string from NetworkUtils and give it to the onLoadFinished
        return n.getMeals();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        //force loader to load
        forceLoad();
        Log.i(LOG_TAG, "onStartLoading");
    }
}