package com.example.neo_alexandria_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.neo_alexandria_app.Activities.Book_Details;
import com.example.neo_alexandria_app.Activities.Song_Details;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.DataModels.Item;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.Handlers.StringsHandler;
import com.example.neo_alexandria_app.R;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static androidx.core.content.ContextCompat.startActivity;

public class MultiSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Item> items;

    public MultiSearchAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if (viewType == 0) { // Song
            return new MusicViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_song,
                            parent,
                            false
                    )
            );
        } else if (viewType == 1) { // Book
            return new BookViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_book,
                            parent,
                            false
                    )
            );
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        holder.itemView.findViewById(R.id.relative).setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        if (getItemViewType(position) == Item.ItemType.SONG_TYPE) {
            Song song = (Song) items.get(position).getObject();
            ((MusicViewHolder) holder).bind(song);
        } else if (getItemViewType(position) == Item.ItemType.BOOK_TYPE) {
            Book book = (Book) items.get(position).getObject();
            ((BookViewHolder) holder).bind(book);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    //should be static
    public class MusicViewHolder extends RecyclerView.ViewHolder {

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
        MusicViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivCover = itemView.findViewById(R.id.ivCover);
            ivComments = itemView.findViewById(R.id.ivComment);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvNumCom = itemView.findViewById(R.id.tvNumberComments);
            tvNumSav = itemView.findViewById(R.id.tvNumberSaves);
            rbStars = itemView.findViewById(R.id.rbStars);
            tvAlbum = itemView.findViewById(R.id.tvAuthor);
            tvAuthor = itemView.findViewById(R.id.tvEditorial);
            relative = itemView.findViewById(R.id.relative);
            tvExplicit = itemView.findViewById(R.id.tvExplicit);
        }

        //Set all the information into the layout, also set the MediaPlayer
        void bind(Song song) {
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

    public class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCover;
        TextView tvTitle;
        RatingBar rbStars;
        TextView tvAuthor;
        TextView tvEditorial;
        TextView tvPages;
        TextView tvSize;
        TextView tvDescription;
        ImageView ivComment;
        TextView tvNumberComments;
        ImageView ivSave;
        TextView tvNumberSaves;
        RelativeLayout relative;


        BookViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rbStars = itemView.findViewById(R.id.rbStars);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvEditorial = itemView.findViewById(R.id.tvEditorial);
            tvPages = itemView.findViewById(R.id.tvPages);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivComment = itemView.findViewById(R.id.ivComment);
            tvNumberComments = itemView.findViewById(R.id.tvNumberComments);
            ivSave = itemView.findViewById(R.id.ivSave);
            tvNumberSaves = itemView.findViewById(R.id.tvNumberSaves);
            relative = itemView.findViewById(R.id.relative);

        }

        void bind(Book book) {
            if (!book.getImageLink().isEmpty()) {
                Glide.with(context)
                        .load(book.getImageLink())
                        .transform(new MultiTransformation(new FitCenter(), new RoundedCornersTransformation(40, 5)))
                        .into(ivCover);
            }
            tvTitle.setText(StringsHandler.limited(book.getTitle(),13));
            rbStars.setRating(book.getRating());
            tvAuthor.setText(StringsHandler.limited(book.getAuthorName(),17));
            tvEditorial.setText(StringsHandler.limited(book.getEditor(),23));

            tvPages.setText("Pages: ?");
            if (book.getPages() != 0) {
                tvPages.setText("Pages: "+book.getPages());
            }

            tvSize.setText("Size: ?");
            if (book.getSize() != 0d) {
                tvSize.setText("Size: "+book.getSize()+" MB");
            }
            tvDescription.setText(StringsHandler.limited(book.getDescription(), 150));
            tvNumberComments.setText(String.valueOf(book.getCommentCount()));
            tvNumberSaves.setText(String.valueOf(book.getSaveCount()));
            relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Book_Details.class);
                    intent.putExtra("book", Parcels.wrap(book));
                    startActivity(context, intent, new Bundle());
                }
            });
        }

    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }
}
