package com.example.himanshuanand.themoviedatabase.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by himanshuanand on 8/1/16.
 */
public class Movie implements Parcelable{

    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private double mUserRating;
    private String mReleaseDate;
    private int mMovieId;

    public Movie(String mTitle, String mPosterPath, String mOverview, double mUserRating, String mReleaseDate,int mMovieId) {
        this.mTitle = mTitle;
        this.mPosterPath = mPosterPath;
        this.mOverview = mOverview;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
        this.mMovieId = mMovieId;
    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mUserRating = in.readDouble();
        mReleaseDate = in.readString();
        mMovieId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeDouble(mUserRating);
        dest.writeString(mReleaseDate);
        dest.writeInt(mMovieId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int getMovieId() {
        return mMovieId;
    }
}
