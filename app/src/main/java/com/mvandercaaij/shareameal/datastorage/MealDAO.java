package com.mvandercaaij.shareameal.datastorage;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.mvandercaaij.shareameal.domain.Meal;

import java.util.ArrayList;

public interface MealDAO {
    //method getMeals that has to return a (json) string
     String getMeals();
}