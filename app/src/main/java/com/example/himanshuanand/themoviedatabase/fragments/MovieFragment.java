package com.example.himanshuanand.themoviedatabase.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.himanshuanand.themoviedatabase.model.Movie;
import com.example.himanshuanand.themoviedatabase.client.MovieLoader;
import com.example.himanshuanand.themoviedatabase.R;
import com.example.himanshuanand.themoviedatabase.activites.MovieDetailActivity;
import com.example.himanshuanand.themoviedatabase.activites.SettingsActivity;
import com.example.himanshuanand.themoviedatabase.adapters.MovieAdapter;
import com.example.himanshuanand.themoviedatabase.utils.ConnectivityErrorUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    private static final String STATE_MOVIES = "state_movies";
    private GridView mGridView;
    private MovieAdapter mMovieAdapter;
    ArrayList<Movie> movieList = null;
    Bundle savedState = null;
    static int loaderCount = 0;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    ProgressBar mLoadingIndicator;

    private static final int MOVIE_LOADER_ID = 1;

    public MovieFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mGridView = (GridView) rootView.findViewById(R.id.gridview);

        mMovieAdapter = new MovieAdapter(getActivity());

        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        mGridView.setEmptyView(mEmptyStateTextView);

        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.loading_spinner);

        mGridView.setAdapter(mMovieAdapter);

        savedState = savedInstanceState;


        //getSupportLoaderManager().initLoader(MOVIE_LOADER_ID,null,this);

        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(),SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        Log.d("MovieFragment","in onCreateLoader");
        return new MovieLoader(getActivity(),args);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movieArrayList) {

        movieList = movieArrayList;
        //Hide the Progress Bar
        mLoadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No movies found."
        mEmptyStateTextView.setText(R.string.no_movies);

        Log.d("Movie Fragment","onLoadFinished");
        mMovieAdapter.clear();

        if (movieList != null || !movieList.isEmpty()){
            mMovieAdapter.addAll(movieList);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        mMovieAdapter.clear();
    }

    @Override
    public void onStart() {
        super.onStart();

        //Check for network Connection
        if (!ConnectivityErrorUtils.isNetworkAvailable(getContext())) {
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_network);
        }else if (savedState != null){
            //Hide the Progress Bar
            mLoadingIndicator.setVisibility(View.GONE);
            new Runnable() {
                @Override
                public void run() {
                    movieList = savedState.getParcelableArrayList(STATE_MOVIES);
                }
            }.run();

            mMovieAdapter.addAll(movieList);
        }else {
            getActivity().getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
        }
    }

    /*public void sortMoviesByPopularity() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = prefs.getString(getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default_value));

            getActivity().getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, args, this);
        }else {
            args.putInt("loaderid",2);
            getActivity().getSupportLoaderManager().initLoader(2, args, this);
        }

    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES,movieList);
    }

}
