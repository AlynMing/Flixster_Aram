package com.example.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flickster.R;
import com.example.flickster.models.Movie;

import java.util.List;

// Adapter extends base recycler view adapter. This is parameterized by viewholders (input), so we define that input first). Import custom viewholder you jut defined
// Base recycler veiw adapter class is an abstract class. There are methods we need to fill out when we extend this class. (Generalized method that is applied to our case)
// Those 3 methods are below -> ViewHolders OnCreateViewHolder, void OnBindViewHolder, int getItemCount
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolders> {

    // Pieces of key data that we need in order to fill out these 3 methods below

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "OnCreateViewHolder");
        // use a static method on the layout inflator class which takes in a contact why is why we passed in the context
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolders(movieView);
    }

    // Involves populating data into the view through the viewholder (or general -> item through the holder)
    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        Log.d("MovieAdapter", "OnBlindViewHolder" + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the viewholder
        holder.bind(movie);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // First define an inner viewholders class
    // Viewholder is a representation of our row in the item_movie.xml layout. Get each component and bind it appropiately
    public class ViewHolders extends RecyclerView.ViewHolder {

        // define member variables for each view
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        // view passed into the constructor is the design view we created (poster, title ,text)
        // This is the constructor
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        // bind is a method for the viewholder
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl = new String();
            // if phone is in landscape, then we set imageURL to be backdrop. Else -> imageUrl = poster image
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).into(ivPoster);
        }
    }
}
