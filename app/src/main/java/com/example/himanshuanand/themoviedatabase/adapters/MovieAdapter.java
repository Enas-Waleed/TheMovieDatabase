package com.example.himanshuanand.themoviedatabase.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.himanshuanand.themoviedatabase.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by himanshuanand on 8/1/16.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context mContext;
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieAdapter(Context context) {
        super(context, 0);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500, 700));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Movie currentMovie = getItem(position);

        //Build Poster path
        String posterPath = IMAGE_URL + currentMovie.getPosterPath();
        Picasso.with(getContext())
                .load(posterPath)
                .into(imageView);

        return imageView;
    }
}
