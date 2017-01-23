package com.example.himanshuanand.themoviedatabase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.himanshuanand.themoviedatabase.model.Movie;
import com.example.himanshuanand.themoviedatabase.client.MovieLoader;
import com.example.himanshuanand.themoviedatabase.R;
import com.example.himanshuanand.themoviedatabase.activites.MovieDetailActivity;
import com.example.himanshuanand.themoviedatabase.utils.ConnectivityErrorUtils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();
    private static final String USER_RATING_ON_SCALE_TEN = "/10";
    private static final String POSTER_PATH_URL = "http://image.tmdb.org/t/p/w185/";
    private CoordinatorLayout coordinatorLayout;

    public MovieDetailFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(LOG_TAG);
        }

        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id
                .coordinatorLayout);
        ImageView posterView = (ImageView) rootView.findViewById(R.id.imageView2);
        TextView movieTitleView = (TextView) rootView.findViewById(R.id.textView);
        TextView releaseDateView = (TextView) rootView.findViewById(R.id.textView2);
        TextView userRatingView = (TextView) rootView.findViewById(R.id.textView3);
        TextView overview = (TextView) rootView.findViewById(R.id.textView4);

        String posterPathUrl = "";
        Movie currentMovie = null;
        String userRating = "";

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("position")) {
            int position = intent.getExtras().getInt("position");

            ArrayList<Movie> movieArrayList = MovieLoader.movies;

            // Current Movie
            currentMovie = movieArrayList.get(position);

            DecimalFormat formatter = new DecimalFormat("0.0");
            userRating = formatter.format(currentMovie.getUserRating());

            //Build Poster path
            posterPathUrl = POSTER_PATH_URL + currentMovie.getPosterPath();
        }

        if (ConnectivityErrorUtils.isNetworkAvailable(getContext())) {
            Picasso.with(getActivity())
                    .load(posterPathUrl)
                    .into(posterView);

            movieTitleView.setText(currentMovie.getTitle());
            releaseDateView.setText(currentMovie.getReleaseDate());

            userRatingView.setText(userRating + USER_RATING_ON_SCALE_TEN);
            overview.setText(currentMovie.getOverview());
        }else {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, R.string.no_network, Snackbar.LENGTH_LONG);

            snackbar.show();
        }




        return rootView;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
