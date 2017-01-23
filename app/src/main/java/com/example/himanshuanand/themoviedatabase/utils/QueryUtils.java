package com.example.himanshuanand.themoviedatabase.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.himanshuanand.themoviedatabase.R;
import com.example.himanshuanand.themoviedatabase.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by himanshuanand on 8/1/16.
 */
public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static URL createUrl(Context context){
        String apiKey = "880de687173a6d4d651a07016e76a49d";


        URL movieUrlLink = null;

        try {
            final String Movie_SORT_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String API_KEY = "api_key";
            final String PAGE = "page";
            final String SORT_BY = "sort_by";

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String sortCriteria = prefs.getString(context.getString(R.string.pref_sort_key),
                    context.getString(R.string.pref_sort_default_value));

            Uri builtUri = null;
        if (sortCriteria == "popularity.desc") {
            builtUri = Uri.parse(Movie_SORT_URL).buildUpon()
                    .appendQueryParameter(API_KEY, apiKey)
                    .appendQueryParameter(SORT_BY, "popularity.desc")
                    .build();
        } else {
            builtUri = Uri.parse(Movie_SORT_URL).buildUpon()
                    .appendQueryParameter(API_KEY, apiKey)
                    .appendQueryParameter(SORT_BY, "vote_average.desc")
                    .build();
        }

            movieUrlLink = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return movieUrlLink;
    }

    public static String establishHttpUrlConnection(URL url){
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        String jsonResponse = "";

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000 /*milliseconds*/);
            httpURLConnection.setConnectTimeout(15000 /*milliseconds*/);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = getJsonResponse(inputStream);
            } else {
                Log.e(LOG_TAG,"Error Response Code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    public static String getJsonResponse(InputStream inputStream){
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        try {
            line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static ArrayList<Movie> extractMovies(String url){

        // Create an empty ArrayList that we can start adding movies to
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        if (TextUtils.isEmpty(url)){
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(url);
            JSONArray jsonArray = jsonObject.optJSONArray("results");

            for (int index = 0;index < jsonArray.length();index++){
                JSONObject resultObject = jsonArray.getJSONObject(index);
                String originalTitle = resultObject.getString("original_title");
                String posterPath = resultObject.getString("poster_path");
                String synopsis = resultObject.getString("overview");
                double userRating = resultObject.getDouble("vote_average");
                String releaseDate = resultObject.getString("release_date");
                int movieId = resultObject.getInt("id");
                movieArrayList.add(new Movie(originalTitle,posterPath,synopsis,userRating,releaseDate,movieId));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the movie JSON results", e);
        }
        return movieArrayList;
    }
}
