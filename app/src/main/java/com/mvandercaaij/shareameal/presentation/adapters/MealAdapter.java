package com.mvandercaaij.shareameal.presentation.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mvandercaaij.shareameal.R;
import com.mvandercaaij.shareameal.datastorage.RecyclerMealInf;
import com.mvandercaaij.shareameal.domain.Meal;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private final RecyclerMealInf recyclerViewInterface;
    private final static String LOG_TAG = MealAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater mInflater;
    private List<Meal> meals;

    public MealAdapter(List<Meal> meals, Context context, RecyclerMealInf recyclerViewInterface) {
        this.meals = meals;
        this.recyclerViewInterface = recyclerViewInterface;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //log method
        Log.d(LOG_TAG, "onCreateViewHolder");

        //make view, which all the data will be stored in per object
        View view = mInflater.inflate(R.layout.meal_list_item, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)  {
        Log.d(LOG_TAG, "onBindViewHolder position = " + position);
        //fill all views with objects data, given by the list
        Meal Meal = this.meals.get(position);
        Glide.with(holder.mMealImage)
                .load(Meal.getImageUrl())
                .centerCrop()
                .into(holder.mMealImage);
        holder.mMealName.setText(Meal.getName());
        holder.mMealDate.setText(Meal.getCreateDate());
        holder.mMealCity.setText(Meal.getCook().getCity());
        holder.mMealPrice.setText(Meal.getPrice());
    }

    //change the content of list for list made in MainActivity
    public void setData(List<Meal> newMeals) {
        this.meals = newMeals;
    }

    //return items in list
    @Override
    public int getItemCount() {
        if (this.meals == null) return 0;
        return this.meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Localize views
        public final ImageView mMealImage;
        public final TextView mMealName;
        public final TextView mMealDate;
        public final TextView mMealCity;
        public final TextView mMealPrice;

        public ViewHolder(@NonNull View itemView, RecyclerMealInf recyclerViewInterface) {
            super(itemView);
            //set views equal to views in layout (meal_list_item)
            mMealImage = itemView.findViewById(R.id.Meal_list_item_image);
            mMealName = itemView.findViewById(R.id.Meal_list_item_name);
            mMealDate = itemView.findViewById(R.id.Meal_list_item_date);
            mMealCity = itemView.findViewById(R.id.Meal_list_item_city);
            mMealPrice = itemView.findViewById(R.id.Meal_list_item_price);

            itemView.setOnClickListener(view -> {
                //when meal is clicked see, if it is valid
                Log.d(LOG_TAG, "Meal clicked");
                if(recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onMealClick(position);
                    }
                }
            });
        }
    }
}