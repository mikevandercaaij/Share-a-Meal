package com.mvandercaaij.shareameal.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.mvandercaaij.shareameal.R;
import com.mvandercaaij.shareameal.domain.Meal;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    public static boolean vegaBool;
    public static boolean veganBool;
    public static boolean takeHomeBool;
    public static boolean isActiveBool;

    private static final String LOG = FilterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);
        MainActivity.LOADER_TOAST = 1;

        //set FragmentManager
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        //set ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            //Localize switch
            Preference veganSwitch = findPreference("vegan");
            //stop potential NullPointerException from occurring
            assert veganSwitch != null;
            //when switch has changed:
            veganSwitch.setOnPreferenceChangeListener((preference, state) -> {
                //set public boolean to current state
                veganBool = (boolean) state;
                //call filter method so filtered list will be applied
                filterList(MainActivity.meals);
                return true;
            });

            //Localize switch
            Preference vegaSwitch = findPreference("vega");
            //stop potential NullPointerException from occurring
            assert vegaSwitch != null;
            //when switch has changed:
            vegaSwitch.setOnPreferenceChangeListener((preference, state) -> {
                //set public boolean to current state
                vegaBool = (boolean) state;
                //call filter method so filtered list will be applied
                filterList(MainActivity.meals);
                return true;
            });

            //Localize switch
            Preference takeHomeSwitch = findPreference("takeHome");
            //stop potential NullPointerException from occurring
            assert takeHomeSwitch != null;
            //when switch has changed:
            takeHomeSwitch.setOnPreferenceChangeListener((preference, state) -> {
                //set public boolean to current state
                takeHomeBool = (boolean) state;

                //call filter method so filtered list will be applied
                filterList(MainActivity.meals);
                return true;
            });

            //Localize switch
            Preference activeSwitch = findPreference("active");
            //stop potential NullPointerException from occurring
            assert activeSwitch != null;
            //when switch has changed:
            activeSwitch.setOnPreferenceChangeListener((preference, state) -> {

                //set public boolean to current state
                isActiveBool = (boolean) state;

                //call filter method so filtered list will be applied
                filterList(MainActivity.meals);
                return true;
            });
        }
    }

    public static List<Meal> filterList(List<Meal> list) {
        //instantiate list
        ArrayList<Meal> newList = new ArrayList<>();

        //if all booleans are false return list
        if(!vegaBool && !veganBool && !takeHomeBool && !isActiveBool) {
            //return the non-filtered list
            return list;
        }

        for(Meal meal : list) {
            //make list filled with booleans
            ArrayList<Boolean> boolList = new ArrayList<>();

            //if the filter vega switch is on
            if(vegaBool) {
                //and meal is vega
                if(meal.isVega()) {
                    //add "true" to list with booleans
                    boolList.add(true);
                } else {
                    //add "false" to list with booleans
                    boolList.add(false);
                }
            }
            //if the filter vegan switch is on
            if(veganBool) {
                //and meal is vegan
                if(meal.isVegan()) {
                    //add "true" to list with booleans
                    boolList.add(true);
                } else {
                    //add "false" to list with booleans
                    boolList.add(false);
                }
            }

            if(takeHomeBool) {
                //and meal is toTakeHome
                if(meal.isToTakeHome()) {
                    //add "true" to list with booleans
                    boolList.add(true);
                } else {
                    //add "false" to list with booleans
                    boolList.add(false);
                }
            }

            if(isActiveBool) {
                //and meal is toTakeHome
                if(meal.isActive()) {
                    //add "true" to list with booleans
                    boolList.add(true);
                } else {
                    //add "false" to list with booleans
                    boolList.add(false);
                }
            }
            //if list with booleans contains "true", meal will be added
            if(boolList.contains(true)) {
                //if list doesn't contain false
                if(!boolList.contains(false)) {
                    //add meal to list
                    newList.add(meal);
                }
            }
        }
        //log list
        Log.d(LOG, newList.toString());

        //return filtered list
        return newList;

    }
}