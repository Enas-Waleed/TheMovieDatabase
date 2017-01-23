package com.example.himanshuanand.themoviedatabase.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.himanshuanand.themoviedatabase.fragments.MovieFragment;
import com.example.himanshuanand.themoviedatabase.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MovieFragment())
                .commit();
    }
}
