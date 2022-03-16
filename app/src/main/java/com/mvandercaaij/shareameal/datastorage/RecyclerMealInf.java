package com.mvandercaaij.shareameal.datastorage;

public interface RecyclerMealInf {
    //method used for onclick on CardView, the int position is used to get items position in adapter
    void onMealClick(int position);
}