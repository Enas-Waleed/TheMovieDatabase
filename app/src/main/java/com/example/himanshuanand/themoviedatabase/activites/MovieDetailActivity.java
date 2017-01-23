package com.example.himanshuanand.themoviedatabase.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.himanshuanand.themoviedatabase.fragments.MovieDetailFragment;
import com.example.himanshuanand.themoviedatabase.R;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail, new MovieDetailFragment())
                .commit();
    }
}
