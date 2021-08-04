package com.example.neo_alexandria_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.example.neo_alexandria_app.Activities.Book_Details;
import com.example.neo_alexandria_app.Activities.Song_Details;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.DataModels.Item;
import com.example.neo_alexandria_app.DataModels.News;
import com.example.neo_alexandria_app.DataModels.Resource;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.Handlers.StringsHandler;
import com.example.neo_alexandria_app.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static androidx.core.content.ContextCompat.startActivity;

public class MultiSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "MultiSearchAdapter";

    Context context;
    List<Item> items;
    public File myDyrectory;

    public MultiSearchAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
        this.myDyrectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), "saves");

        if (!this.myDyrectory.exists()) {
            this.myDyrectory.mkdirs();
        }
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if (viewType == Item.ItemType.SONG_TYPE) {
            return new MusicViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_song,
                            parent,
                            false
                    )
            );
        } else if (viewType == Item.ItemType.BOOK_TYPE) {
            return new BookViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_book,
                            parent,
                            false
                    )
            );
        } else {
            return new NewsViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_news,
                            parent,
                            false
                    )
            );
        }

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
        } else {
            News new1 = (News) items.get(position).getObject();
            ((NewsViewHolder) holder).bind(new1);
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
            tvAuthor = itemView.findViewById(R.id.tvNewsDescription);
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

            if (song.isSaved()) {
                ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_marked));
            } else {
                ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_unmark));
            }

            relative.setOnTouchListener(new View.OnTouchListener() {
                private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        ivSave.callOnClick();
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent event) {
                        Intent intent = new Intent(context, Song_Details.class);
                        intent.putExtra("song", Parcels.wrap(song));
                        startActivity(context, intent, new Bundle());
                        return false;
                    }
                });

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });


            // TODO: I think some of these we can do refactor to avoid duplicated code in the other kind of items
            ivSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (song.isSaved()) {
                        song.setSaved(false);
                        ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_unmark));

                        //Delete element saved from cloud
                        deleteSave(song.getId());

                        //Delete element saved from local
                        File object = new File(myDyrectory.getPath() + File.separator + song.getId() + ".txt");
                        try {
                            ObjectInputStream in = new ObjectInputStream(new FileInputStream(object.getAbsolutePath()));
                            Item itemaux = (Item) in.readObject();
                            Song songaux = (Song) itemaux.getObject();
                            Log.e(TAG, songaux.getTitle());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                        if (object.exists()) {
                            object.delete();
                        }
                    } else {
                        ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_marked));
                        song.setSaved(true);

                        //Store element on local
                        storeItemOnLocal(song.getId(), (Object) song, Item.ItemType.SONG_TYPE);
                    }
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
            tvEditorial = itemView.findViewById(R.id.tvNewsDescription);
            tvPages = itemView.findViewById(R.id.tvDate);
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
            tvTitle.setText(StringsHandler.limited(book.getTitle(), 13));
            rbStars.setRating(book.getRating());
            tvAuthor.setText(StringsHandler.limited(book.getAuthorName(), 17));
            tvEditorial.setText(StringsHandler.limited(book.getEditor(), 23));

            tvPages.setText("Pages: ?");
            if (book.getPages() != 0) {
                tvPages.setText("Pages: " + book.getPages());
            }

            tvSize.setText("Size: ?");
            if (book.getSize() != 0d) {
                tvSize.setText("Size: " + book.getSize() + " MB");
            }
            tvDescription.setText(StringsHandler.limited(book.getDescription(), 150));
            tvNumberComments.setText(String.valueOf(book.getCommentCount()));
            tvNumberSaves.setText(String.valueOf(book.getSaveCount()));
            relative.setOnTouchListener(new View.OnTouchListener() {
                private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        ivSave.callOnClick();
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent event) {
                        Intent intent = new Intent(context, Book_Details.class);
                        intent.putExtra("book", Parcels.wrap(book));
                        startActivity(context, intent, new Bundle());
                        return false;
                    }
                });

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
            if (book.isSaved()) {
                ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_marked));
            } else {
                ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_unmark));
            }

            File myDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), "saves");

            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            ivSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (book.isSaved()) {
                        book.setSaved(false);
                        ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_unmark));

                        //Delete element saved from cloud
                        deleteSave(book.getId());

                        //Delete element saved from Local
                        File object = new File(myDirectory.getPath() + File.separator + book.getId() + ".txt");
                        try {
                            ObjectInputStream in = new ObjectInputStream(new FileInputStream(object.getAbsolutePath()));
                            Item itemaux = (Item) in.readObject();
                            Book bookaux = (Book) itemaux.getObject();
                            Log.e(TAG, bookaux.getTitle());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                        if (object.exists()) {
                            object.delete();
                        }

                    } else {
                        ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_marked));
                        book.setSaved(true);

                        //Store element on Local
                        storeItemOnLocal(book.getId(), (Object) book, Item.ItemType.BOOK_TYPE);
                    }
                }
            });
        }

    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCover;
        TextView tvDate;
        TextView tvTitle;
        TextView tvNewsSource;
        TextView tvAuthor;
        TextView tvNewsDescription;
        RatingBar rbStars;
        ImageView ivSave;
        ImageView ivComment;
        TextView tvNumberComments;
        TextView tvNumberSaves;
        RelativeLayout relative;

        public NewsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivCover);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvNewsSource = itemView.findViewById(R.id.tvNewsSource);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvNewsDescription = itemView.findViewById(R.id.tvNewsDescription);
            rbStars = itemView.findViewById(R.id.rbStars);
            ivSave = itemView.findViewById(R.id.ivSave);
            ivComment = itemView.findViewById(R.id.ivComment);
            tvNumberComments = itemView.findViewById(R.id.tvNumberComments);
            tvNumberSaves = itemView.findViewById(R.id.tvNumberSaves);
            relative = itemView.findViewById(R.id.relative);
        }

        void bind(News news) {
            if (!news.getImageLink().isEmpty()) {
                Glide.with(context)
                        .load(news.getImageLink())
                        .transform(new MultiTransformation(new FitCenter(), new RoundedCornersTransformation(40, 5)))
                        .into(ivCover);
            }
            tvDate.setText(news.getDate());
            tvTitle.setText(StringsHandler.limited(news.getTitle(), 23));
            tvNewsSource.setText(news.getSourceName());
            tvAuthor.setText(news.getAuthorName());
            tvNewsDescription.setText(news.getDescription());
            rbStars.setRating(news.getRating());
            tvNumberComments.setText(String.valueOf(news.getCommentCount()));
            tvNumberSaves.setText(String.valueOf(news.getSaveCount()));
            if (news.isSaved()) {
                ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_marked));
            } else {
                ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_unmark));
            }
            relative.setOnTouchListener(new View.OnTouchListener() {
                private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        ivSave.callOnClick();
                        return super.onDoubleTap(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent event) {

                        return false;
                    }
                });

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });

            File myDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), "saves");

            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            ivSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (news.isSaved()) {
                        news.setSaved(false);
                        ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_unmark));
                        //Delete element on cloud
                        deleteSave(news.getId());

                        //Delete element saved on local
                        File object = new File(myDirectory.getPath() + File.separator + news.getId() + ".txt");
                        try {
                            ObjectInputStream in = new ObjectInputStream(new FileInputStream(object.getAbsolutePath()));
                            Item itemaux = (Item) in.readObject();
                            News newsaux = (News) itemaux.getObject();
                            Log.e(TAG, newsaux.getTitle());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                        if (object.exists()) {
                            object.delete();
                        }
                    } else {
                        ivSave.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bookmark_marked));
                        news.setSaved(true);
                        //Store element on cloud
//                        storeItem(news.getId());
                        //Store element on local
                        storeItemOnLocal(news.getId(), (Object) news, Item.ItemType.NEWS_TYPE);
                    }
                }
            });
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void deleteSave(String itemId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SavedItem");
        query.whereEqualTo("UserId", ParseUser.getCurrentUser());
        query.whereEqualTo("ItemId", itemId);

        query.getFirstInBackground((object, e) -> {
            if (e == null) {
                try {
                    object.delete();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            } else {
                Log.e(TAG, e.getMessage());
            }
        });
    }

    public void storeItem(String itemId) {
        ParseObject saveSong = new ParseObject("SavedItem");
        saveSong.put("ItemId", itemId);
        saveSong.put("UserId", ParseUser.getCurrentUser());

        saveSong.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving song ", e);
                }
            }
        });
    }

    private void storeItemOnLocal(String itemID, Object object, @Item.ItemType int itemType) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(myDyrectory.getPath() + File.separator + itemID + ".txt"));

            Item auxItem;
            ParseObject objectItem = new ParseObject("Item");

            if (itemType == Item.ItemType.SONG_TYPE) {
                Song item = (Song) object;
                auxItem = new Item(itemType, item, item.getRating());
            } else if (itemType == Item.ItemType.BOOK_TYPE) {
                Book item = (Book) object;
                auxItem = new Item(itemType, item, item.getRating());
            } else {
                News item = (News) object;
                auxItem = new Item(itemType, item, item.getRating());
            }
            out.writeObject(auxItem);
            //Add this to Items if doesn't exist
            File file = new File(myDyrectory.getPath() + File.separator + itemID + ".txt");
            ParseFile parseFile = new ParseFile(file);
            parseFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Success
                        objectItem.put("ItemFile", parseFile);
                        objectItem.put("LocalId", itemID);
                        objectItem.saveInBackground(ex -> {
                            if (ex == null) {
                                Log.e(TAG, "Item uploaded");
                                //Store element on cloud
                                storeItem(itemID);
                            } else {
                                Log.e(TAG, "here " + ex.getMessage());
                            }
                        });
                    } else {
                        Log.e("saving parsefile", e.getMessage());
                    }
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
