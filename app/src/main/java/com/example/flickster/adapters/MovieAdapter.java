package com.example.flickster.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flickster.DetailActivity;
import com.example.flickster.MainActivity;
import com.example.flickster.R;
import com.example.flickster.models.Movie;

import org.parceler.Parcels;

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

//        Movie movietrans = movies.get(position);
//
//        Glide.with(holder.itemView.getContext())
//                .load(movietrans.getTitle())
//                .into(holder.tvTitle);
//
//        ViewCompat.setTransitionName(holder.tvTitle, movie.getTitle());
//
//        Glide.with(holder.itemView.getContext())
//                .load(movietrans.getOverview())
//                .into(holder.tvOverview);
//
//        ViewCompat.setTransitionName(holder.tvTitle, movie.getTitle());


    }




    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // First define an inner viewholders class
    // Viewholder is a representation of our row in the item_movie.xml layout. Get each component and bind it appropiately
    public class ViewHolders extends RecyclerView.ViewHolder {

        RelativeLayout container;
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
            container = itemView.findViewById(R.id.container);
        }

        // bind is a method for the viewholder
        public void bind(final Movie movie) {
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

            // possibly mess around with scaletype
            // TESTING GIT, again
            // .transform(new RoundedCorners(2))

            // 1. Register click listener on the full container / anywhere in each row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 2. Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie)); // using parcels dependency

                    // set up string pairs to pass into Options. Removed Compat version because it was old and depreciated as it needed android support libaries v4
                    // SUCCESS!!! But the transition is blinky and jerky. Maybe title and movie don't go well together? Stick to 1 element? Or optimize UX further?
                    // sets up a pair of views to be used in the shared element transition. Utilize current onclick listener in MovieAdapter
                    final Pair<View,String> pair1 = Pair.create((View)tvOverview,"overviewTransition");
                    final Pair<View,String> pair2 = Pair.create((View)tvTitle,"movieTitleTransition");
                    ActivityOptions options = ActivityOptions.
                            makeSceneTransitionAnimation((Activity)context, pair1, pair2);

                    context.startActivity(i, options.toBundle());


                }
            });
        }
    }
}
