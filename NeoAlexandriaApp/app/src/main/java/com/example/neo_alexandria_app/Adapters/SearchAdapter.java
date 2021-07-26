package com.example.neo_alexandria_app.Adapters;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.Handlers.StringsHandler;
import com.example.neo_alexandria_app.R;
import com.example.neo_alexandria_app.Activities.Song_Details;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;


import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static androidx.core.content.ContextCompat.startActivity;

// Here we manage how we display the items in recycler views
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public static final String TAG = "SongAdapter";

    Context context;
    List<Song> songs;
    List<Book> books;

    //Pass in the context and list of Songs
    //TODO: get books here is an error, I have to do this following the multiitem adapter way
    public SearchAdapter(Context context, List<Song> songs, List<Book> books) {
        this.context = context;
        this.songs = songs;
        this.books = books;
    }


    // For each row, inflate the layout
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //TODO: pay attention to what to do with other styles
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data at position
        Song song = songs.get(position);
        //Bind the tweet with view holder
        holder.bind(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCover;
        ImageView ivComments;
        ImageView ivSave;
        TextView tvTitle;
        TextView tvNumCom;
        TextView tvNumSav;
        RatingBar rbStars;
        TextView tvAlbum;
        TextView tvAuthor;
        TextView tvExplicit;
        RelativeLayout relative;


        //Here I got all the items that I need from layout

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.ivCover);
            ivComments = itemView.findViewById(R.id.ivComment);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvNumCom = itemView.findViewById(R.id.tvNumberComments);
            tvNumSav = itemView.findViewById(R.id.tvNumberSaves);
            rbStars = itemView.findViewById(R.id.rbStars);
            tvAlbum = itemView.findViewById(R.id.tvAlbum);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            relative = itemView.findViewById(R.id.relative);
            tvExplicit = itemView.findViewById(R.id.tvExplicit);

        }

        //Set all the information into the layout, also set the MediaPlayer
        public void bind(Song song) {
            if (!song.getImageLink().isEmpty()) {
                Glide.with(context)
                        .load(song.getImageLink())
                        .transform(new MultiTransformation(new FitCenter(), new RoundedCornersTransformation(40, 5)))
                        .into(ivCover);
            }
            tvTitle.setText(StringsHandler.limited(song.getTitle(), 13));
            tvAlbum.setText(StringsHandler.limited(song.getAlbumTitle(), 22));
            tvAuthor.setText(StringsHandler.limited(song.getAuthorName(), 25));
            tvNumCom.setText(String.valueOf(song.getCommentCount()));
            tvNumSav.setText(String.valueOf(song.getSaveCount()));
            rbStars.setRating(song.getRating());

            if (song.isExplicitContent() == true) {
                tvExplicit.setText("Explicit");
            } else {
                tvExplicit.setText("");
            }

            relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Song_Details.class);
                    intent.putExtra("song", Parcels.wrap(song));
                    startActivity(context, intent, new Bundle());
                }
            });
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        songs.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Song> list) {
        songs.addAll(list);
        notifyDataSetChanged();
    }

}
