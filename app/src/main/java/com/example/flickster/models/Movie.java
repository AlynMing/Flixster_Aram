package com.example.flickster.models;

// Plane old java object -> POJO
// Make the movie parsing a separate model as an app can have many and you want to categorize to organize it

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    int movieID;
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;
    String release_date;
    double popularity;

    // empty constructor needed by Parceler Library
    public Movie() {}


    // A constructor that takes in a JSON object, and it will construct a Movie Object. tAKE IN JSON object and read out the fields specified.
    public Movie(JSONObject jsonObject) throws JSONException { // if any getString fails, the constructor throws JSONException. this is method signature not try/catch.
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
        release_date = jsonObject.getString("release_date");
        popularity = jsonObject.getDouble("popularity");

    }

    // Static method to return a list of movies. Input is Json Array. Iterates through json array and constructs a movie for each element
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) { // iterate through and add movie for each element
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    // To get data out of these objects, we generate getters for all 3 fields that will be in our movie app
    /* Make posterpath usable because currently it is just a relative path (not a full URL). We have to combine it with base URL to get a FULL url
        We go to Configuration path response and read API documentation around returning a poster with specific sizes. 342 will be the postersize we want.
        1. Make API call to Configurations API to see what image sizes are available. 2. Append that to base URL. 3. Append relative path. For sake of example, we hardcode.*/

    public String getPosterPath() { // My question is what is the part after the /w342/ in the hardcoded URL? Before the %s
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath); // %s means replace that part of the string with 2nd parameter (posterpath)
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getPopularity() {
        return popularity;
    }


}
