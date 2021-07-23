package com.example.neo_alexandria_app.Adapters;

import android.content.Context;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.R;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

// Here we manage how we display the items in recycler views
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    public static final String TAG = "SongAdapter";

    Context context;
    List<Song> songs;

    //Pass in the context and list of Songs
    public SearchAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }


    // For each row, inflate the layout
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //TODO: pay attention to what to do with other styles
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent,false);
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
        RatingBar ratingBar;
        Button btnPlay;
        ProgressBar progressBar;
        TextView elapsedTimeLabel;
        TextView remainingTimeLabel;


        //Here I got all the items that I need from layout

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.ivCover);
            ivComments = itemView.findViewById(R.id.ivComment);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvNumCom = itemView.findViewById(R.id.tvNumberComments);
            tvNumSav = itemView.findViewById(R.id.tvNumberSaves);
            ratingBar = itemView.findViewById(R.id.rbStars);
            btnPlay = itemView.findViewById(R.id.playBtn);
            progressBar = itemView.findViewById(R.id.positionBar);
            elapsedTimeLabel = itemView.findViewById(R.id.elapsedTimeLabel);
            remainingTimeLabel = itemView.findViewById(R.id.remainingTimeLabel);

        }
        //Set all the information into the layout, also set the MediaPlayer
        public void bind(Song song) {
            if (!song.getImageLink().isEmpty()){
                Glide.with(context)
                        .load(song.getImageLink())
                        .transform(new MultiTransformation(new FitCenter(), new RoundedCornersTransformation(40,5)))
                        .into(ivCover);
            }

            tvTitle.setText(song.getTitle());
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
