package com.mvandercaaij.shareameal.datastorage;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetworkUtils implements MealDAO {

    public static final String MEAL_BASE_URL = "https://shareameal-api.herokuapp.com/api/meal";
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getMeals() {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String response = null;
        BufferedReader bufferedReader = null;

        try {
            // Uri is used for the Books API - all results have the same base URI and then rest changes due to the search
            Uri builtUri = Uri.parse(MEAL_BASE_URL).buildUpon().build();
            // convert URI to URL for the www
            URL requestURL = new URL(builtUri.toString());

            // use HttpURLConnection to connect to the internet
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // get the input from the url connection
            inputStream = urlConnection.getInputStream();
            // buffered reader for the input stream
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // string builder to hold the JSON response
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            //add all lines to stringbuilder
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);

                stringBuilder.append("\n");
            }

            //if theres no data, return null
            if (stringBuilder.length() == 0){
                return null;
            }

            //set response string equals to the text appended in the StringBuilder
            response = stringBuilder.toString();

            Log.i(LOG_TAG, "JSON Response: " + response);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e("TAG", "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        //if all went well, return string of all data
        return response;
    }
}