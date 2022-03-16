package com.mvandercaaij.shareameal.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mvandercaaij.shareameal.R;
import com.mvandercaaij.shareameal.presentation.adapters.MealAdapter;
import com.mvandercaaij.shareameal.datastorage.RecyclerMealInf;
import com.mvandercaaij.shareameal.domain.Cook;
import com.mvandercaaij.shareameal.domain.Participant;
import com.mvandercaaij.shareameal.domain.Meal;
import com.mvandercaaij.shareameal.presentation.loaders.MealLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecyclerMealInf {

    private static final String TAG = "MainActivity";
    public static int LOADER_TOAST = 1;

    private MealAdapter mMealAdapter;
    public static List<Meal> meals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        link recyclerview to recyclerview in layout
        //Attributes
        RecyclerView mRecyclerViewMeals = findViewById(R.id.activity_main_recyclerview_meals);

        //instantiate adapter
        this.mMealAdapter = new MealAdapter(meals , MainActivity.this, this);

        //link adapter to recyclerview
        mRecyclerViewMeals.setAdapter(mMealAdapter);

        //make it so the items made by the adapter (shown by recyclerview) are in a grid with 2 besides each other
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        //when the device is horizontal show 3 items besides each other
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        }

        //set the layout of the recyclerview
        mRecyclerViewMeals.setLayoutManager(layoutManager);

        LoaderManager.getInstance(this).initLoader(0,null,this);

        //set the shared preferences
        setSharedPreferences();

    }

    public void setSharedPreferences() {
        //set default values filter (first time launch or when you've never used the filters same thing)
        androidx.preference.PreferenceManager
                .setDefaultValues(this,
                        R.xml.root_preferences, false);

        // Read the settings from the shared preferences
        SharedPreferences sharedPref =
                androidx.preference.PreferenceManager
                        .getDefaultSharedPreferences(this);

        //set booleans for filtering in FilterActivity equals to booleans saved in shared preferences, if their are none, set boolean to false
        FilterActivity.veganBool = sharedPref.getBoolean
                ("vegan", false);

        FilterActivity.vegaBool = sharedPref.getBoolean
                ("vega", false);

        FilterActivity.takeHomeBool = sharedPref.getBoolean
                ("takeHome", false);

        FilterActivity.isActiveBool = sharedPref.getBoolean
                ("active", false);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MealLoader( MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        //fill meals list with meals
        meals = jsonParseResponse(data);

        //filter list
        meals = FilterActivity.filterList(meals);

        //update adapters list
        mMealAdapter.setData(meals);

        //tell adapter something has changed
        mMealAdapter.notifyDataSetChanged();

        //display amount of loaded meals
        displayAmountOfMealsToast();

        //log end of onLoadFinished
        Log.i(TAG, "onLoadFinished");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

    public void displayAmountOfMealsToast() {
        if(LOADER_TOAST == 1) {
            LOADER_TOAST++;
            Toast toast = Toast.makeText(getApplicationContext(),
                    meals.size() + " items have been loaded.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public ArrayList<Meal> jsonParseResponse(String response){
        ArrayList<Meal> FilledListMeals = new ArrayList<>();

        // Parse response.
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray results = jsonObject.getJSONArray("result");
            for (int i = 0; i < results.length(); i++) {
                JSONObject mealJson = results.getJSONObject(i);

                //get participants array
                JSONArray participants = mealJson.getJSONArray("participants");
                //instantiate list
                ArrayList<Participant> participantsList = new ArrayList<>();
                //for every participant ...
                for(int k = 0; k < participants.length(); k++) {
                    JSONObject tempParticipant =  participants.getJSONObject(k);
                    //data for object
                    int pID = tempParticipant.getInt("id");
                    String pFirstName = tempParticipant.getString("firstName");
                    String pCity = tempParticipant.getString("city");
                    String pStreet = tempParticipant.getString("street");
                    String pLastName = tempParticipant.getString("lastName");
                    String pEmail = tempParticipant.getString("emailAdress");
                    String pPhoneNumber = tempParticipant.getString("phoneNumber");
                    boolean pIsActive = tempParticipant.getBoolean("isActive");
                    //instantiate list for roles
                    ArrayList<String> pRoles = new ArrayList<>();

                    //get roles array
                    JSONArray pRolesArray = tempParticipant.getJSONArray("roles");
                    //for every role
                    for(int l = 0; l < pRolesArray.length(); l++) {
                        //add to roleList
                        pRoles.add(pRolesArray.get(k).toString());
                    }

                    //add all info to list of participants, which is later passed into the meal object
                    participantsList.add(new Participant(pID, pFirstName, pCity, pStreet, pLastName, pEmail, pPhoneNumber, pRoles, pIsActive));
                }

                //get cook object
                JSONObject cookObject = mealJson.getJSONObject("cook");

                int cookID = cookObject.getInt("id");
                String firstName = cookObject.getString("firstName");
                String lastName = cookObject.getString("lastName");
                String emailAddress = cookObject.getString("emailAdress");
                String phoneNumber = cookObject.getString("phoneNumber");
                String street = cookObject.getString("street");
                String city = cookObject.getString("city");
                boolean CookIsActive = cookObject.getBoolean("isActive");

                //create JSONArray for cook roles
                JSONArray roles = cookObject.getJSONArray("roles");
                //make temp array to store roles
                ArrayList<String> tempRoles = new ArrayList<>();

                //put all roles from jsonarray in arraylist
                for (int j = 0; j < roles.length(); j++) {
                    tempRoles.add(roles.get(j).toString());
                }

                //fill values with meal data
                int id = mealJson.getInt("id");
                String name = mealJson.getString("name");
                String description = mealJson.getString("description");
                boolean isActive = mealJson.getBoolean("isActive");
                boolean isVega = mealJson.getBoolean("isVega");
                boolean isVegan = mealJson.getBoolean("isVegan");
                boolean isToTakeHome = mealJson.getBoolean("isToTakeHome");
                String dateTime = mealJson.getString("dateTime");
                String createDate = mealJson.getString("createDate");
                String updateDate = mealJson.getString("updateDate");
                int maxAmountOfParticipants = mealJson.getInt("maxAmountOfParticipants");
                Double price = mealJson.getDouble("price");
                String imageUrl = mealJson.getString("imageUrl");

                //create JSONArray for meal allergies
                JSONArray allergies = mealJson.getJSONArray("allergenes");
                //make temp array to store roles
                ArrayList<String> tempAllergies = new ArrayList<>();

                //put all allergies from jsonarray in arraylist
                for (int j = 0; j < allergies.length(); j++) {
                    tempAllergies.add(allergies.get(j).toString());
                }

                FilledListMeals.add(new Meal(id, name, description, isActive, isVega, isVegan, isToTakeHome,
                        dateTime, createDate, updateDate, maxAmountOfParticipants, price, imageUrl, tempAllergies, new Cook(cookID, firstName,
                        city, street, lastName, emailAddress, phoneNumber, tempRoles, CookIsActive), participantsList));
                Log.d(TAG, FilledListMeals.toString());
                Log.i(TAG, "JSON string parsed");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //if list isn't empty
        if (FilledListMeals.size() > 0) {
            Log.i("LOG", "List filled.");
        }

        //log FilledListMeals
        Log.d("LOG", FilledListMeals.toString());

        //return the list
        return FilledListMeals;
    }

    @Override
    public void onMealClick(int position) {
        //make intent
        Intent intent = new Intent(MainActivity.this, MealDetailActivity.class);

        //give meal object to detail activity
        intent.putExtra("MEAL", meals.get(position));

        //start intent
        startActivity(intent);

        //log intent
        Log.d(TAG, intent.toString());
    }

    //set menu (dots)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //if menu item is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //and id is equal to Filter
        if (id == R.id.Filter) {
            //switch activity
            Intent intent = new Intent(MainActivity.this, FilterActivity.class);
            startActivity(intent);
        }
        return true;
    }
}