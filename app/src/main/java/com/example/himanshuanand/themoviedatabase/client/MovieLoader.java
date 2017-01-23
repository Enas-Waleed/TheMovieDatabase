package com.example.himanshuanand.themoviedatabase.client;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.himanshuanand.themoviedatabase.model.Movie;
import com.example.himanshuanand.themoviedatabase.utils.QueryUtils;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by himanshuanand on 8/1/16.
 */
public class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    public static ArrayList<Movie> movieArrayList;
    public static ArrayList<Movie> movies;
    public static Bundle mSort;
    Context mContext;

    public MovieLoader(Context context,Bundle sort) {
        super(context);
        mContext = context;
        mSort = sort;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        URL movieUrl;
        movies = new ArrayList<Movie>();
        Log.d("Movie Loader","loadInBackground");
        for (int page = 1;page <= 10;page++) {
            movieUrl = QueryUtils.createUrl(mContext);

            String jsonResponse = "";
            jsonResponse = QueryUtils.establishHttpUrlConnection(movieUrl);

            movieArrayList = QueryUtils.extractMovies(jsonResponse);

            movies.addAll(movieArrayList);

        }
        Log.d("Movie Loader","" + movies.size());
        return movies;
    }
}
