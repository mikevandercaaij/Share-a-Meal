package com.mvandercaaij.shareameal.presentation;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mvandercaaij.shareameal.R;
import com.mvandercaaij.shareameal.domain.Meal;
import com.squareup.picasso.Picasso;

public class MealDetailActivity extends AppCompatActivity {

    private static final String LOG = MealDetailActivity.class.getSimpleName();

    //Localize all views
    private ImageView mImageview;
    private TextView mMealName;
    private TextView mDescription;
    private TextView mMealPrice;
    private TextView mMealVega;
    private TextView mMealVegan;
    private TextView mMealDate;
    private TextView mMealAllergies;
    private TextView mCookName;
    private TextView mCookEmail;
    private TextView mCookPhone;
    private TextView mCookAddress;
    private TextView mMealTakeHome;
    private TextView mMealAvailableSlots;
    private TextView mMealCurrentParticipants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        //get value from intent and store them in local value
        Meal meal = (Meal) getIntent().getSerializableExtra("MEAL");

        //log the current meal
        Log.d(LOG, meal.toString());

        //use intent to fill all needed data
        String image = meal.getImageUrl();
        String mealName = meal.getName();
        String price = meal.getPrice();
        String description = meal.getDescription();
        String date = meal.getCreateDate();
        boolean vega = meal.isVega();
        boolean vegan = meal.isVegan();
        String cookName = meal.getCook().getFirstName() + " " + meal.getCook().getLastName();
        String cookEmail = meal.getCook().getEmail();
        String cookPhone = meal.getCook().getPhoneNumber();
        String allergies = meal.getAllergenes();
        String address = meal.getCook().getFullAddress();
        boolean takeHome = meal.isToTakeHome();
        int amountOfParticipants = meal.getAmountOfParticipants();
        int maxParticipants = meal.getMaxAmountOfParticipants();
        String participants = meal.getParticipantsNames();



        //set local view equal to layout view
        mImageview = findViewById(R.id.meal_detail_image);
        mMealName = findViewById(R.id.meal_detail_name);
        mDescription = findViewById(R.id.meal_detail_description);
        mMealPrice = findViewById(R.id.meal_detail_price);
        mMealVega = findViewById(R.id.meal_detail_vega);
        mMealVegan = findViewById(R.id.meal_detail_vegan);
        mMealDate = findViewById(R.id.meal_detail_date);
        mMealAllergies = findViewById(R.id.meal_detail_allergies);
        mCookName = findViewById(R.id.meal_detail_cookName);
        mCookEmail = findViewById(R.id.meal_detail_cookEmail);
        mCookPhone = findViewById(R.id.meal_detail_cookPhone);
        mCookAddress = findViewById(R.id.meal_detail_address);
        mMealTakeHome = findViewById(R.id.meal_takeHome);
        mMealAvailableSlots = findViewById(R.id.meal_availableSlots);
        mMealCurrentParticipants = findViewById(R.id.meal_participants);


        //set value to views, if the data is there
        if(!image.isEmpty()) {
            Picasso.get().load(image).into(mImageview);
        }
        if(!mealName.isEmpty()) {
            mMealName.setText(mealName);
        }

        if(!price.isEmpty()) {
            mMealPrice.setText(price);
        }

        if(!description.isEmpty()) {
            mDescription.setText(description);
        }

        if(!date.isEmpty()) {
            mMealDate.setText(this.getString(R.string.datumDetails) + " " +date);
        }

        if(vega == false || true) {
            if (vega) {
                mMealVega.setText(this.getString(R.string.vegaString) + " ✔️");
            } else {
                mMealVega.setText(this.getString(R.string.vegaString) + " ❌");
            }
        }

        if(vegan == false || true) {
            if(vegan) {
                mMealVegan.setText(this.getString(R.string.veganString) + " ✔️");
            } else {
                mMealVegan.setText(this.getString(R.string.veganString) +" ❌");
            }
        }

        if(takeHome == false || true) {
            if(takeHome) {
                mMealTakeHome.setText(this.getString(R.string.takeHomeString) + " ✔️");
            } else {
                mMealTakeHome.setText(this.getString(R.string.takeHomeString) +" ❌");
            }
        }

        if(!meal.getCook().getFirstName().isEmpty() && !meal.getCook().getLastName().isEmpty()) {
            mCookName.setText(cookName);
        }

        if(!cookEmail.isEmpty()) {
            mCookEmail.setText(cookEmail);
        }

        if(!cookPhone.isEmpty()) {
            mCookPhone.setText(cookPhone);
        }

        if(!allergies.isEmpty()) {
            mMealAllergies.setText(this.getString(R.string.allergies) + " " + allergies);
        } else {
            mMealAllergies.setText(this.getString(R.string.allergies) + " " + this.getString(R.string.no_allergies));
        }

        if(!address.isEmpty()) {
            mCookAddress.setText(this.getString(R.string.addressString) + " " + address);
        }

        if (maxParticipants > -1 && amountOfParticipants > -1) {
            int availableSlots = maxParticipants - amountOfParticipants;
            mMealAvailableSlots.setText(this.getString(R.string.mealSlots) + " " + availableSlots);
        }

        if(!participants.isEmpty()) {
            mMealCurrentParticipants.setText(this.getString(R.string.participantString) + " " + participants);
        }else {
            mMealCurrentParticipants.setText(this.getString(R.string.participantString) + " " + this.getString(R.string.no_participants));
        }

    }
}
