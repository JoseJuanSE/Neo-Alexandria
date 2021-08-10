package com.example.neo_alexandria_app.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.neo_alexandria_app.Adapters.MultiSearchAdapter;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.DataModels.Item;
import com.example.neo_alexandria_app.DataModels.News;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SaveFragment extends Fragment {

    public static final String TAG = "SaveFragment";

    Toolbar toolbar;
    List<Song> songs;
    List<Book> books;
    List<News> news;
    List<Item> items;

    MultiSearchAdapter multiSearchAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    //filter
    Button btnAll;
    Button btnBooks;
    Button btnSongs;
    Button btnNews;
    boolean booksselected;
    boolean songsselected;
    boolean newsselected;

    SweetAlertDialog sweetAlertDialog;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SaveFragment() {

    }

    public static SaveFragment newInstance(String param1, String param2) {
        SaveFragment fragment = new SaveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //Here we ask for permission to modified storage
        if (!Environment.isExternalStorageManager()) {
            Intent permissionIntent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            SweetAlertDialog alert = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(permissionIntent);
                            sweetAlertDialog.cancel();
                        }
                    })
                    .setTitleText("Please let us manage your storage")
                    .setConfirmText("Go to permissions")
                    .setContentText("We need access to your storage to be able to save and erase the files that your are saving on the app");
            alert.show();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        //setting toolbar
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Setting swipe refresh
        swipeRefreshLayout = view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    populateRecyclerView();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        // Init the lists and adapter
        songs = new ArrayList<>();
        books = new ArrayList<>();
        news = new ArrayList<>();
        items = new ArrayList<>();
        multiSearchAdapter = new MultiSearchAdapter(getContext(), items, "saved");
        // Recycler view setup: layout manager and the adapter
        recyclerView = view.findViewById(R.id.rvSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(multiSearchAdapter);

        //filters
        btnAll = (Button) view.findViewById(R.id.allButton);
        btnBooks = (Button) view.findViewById(R.id.booksButton);
        btnNews = (Button) view.findViewById(R.id.newsButton);
        btnSongs = (Button) view.findViewById(R.id.musicButton);
        btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        btnBooks.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        btnNews.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        btnSongs.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        booksselected = true;
        newsselected = true;
        songsselected = true;


        //Here we do the necessary to show all the items
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(booksselected && songsselected && newsselected)) {
                    btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    btnBooks.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnNews.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnSongs.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    booksselected = true;
                    newsselected = true;
                    songsselected = true;
                    checkRequestsFinished();
                }
            }
        });

        //Here we do the necessary to show just news items
        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newsselected || (booksselected && songsselected && newsselected)) {
                    btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnBooks.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnNews.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    btnSongs.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    booksselected = false;
                    newsselected = true;
                    songsselected = false;
                    checkRequestsFinished();
                }
            }
        });

        //Here we do the necessary to show just songs items
        btnSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!songsselected || (booksselected && songsselected && newsselected)) {
                    btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnBooks.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnNews.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnSongs.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    booksselected = false;
                    newsselected = false;
                    songsselected = true;
                    checkRequestsFinished();
                }
            }
        });

        //Here we do the necessary to show just books items
        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!booksselected || (booksselected && songsselected && newsselected)) {
                    btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnBooks.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                    btnNews.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    btnSongs.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                    booksselected = true;
                    newsselected = false;
                    songsselected = false;
                    checkRequestsFinished();
                }
            }
        });

        try {
            populateRecyclerView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void populateRecyclerView() throws IOException, ClassNotFoundException {
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        //We check what to display depending on connectivity status: online or offline status
        if (activeNetwork != null) {
            //If there is connection, we delete all the items saved to update the list with the new items that may be saved in other devices.
            DeleteLocal();
        } else {
            try {
                DisplayData();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
        LoadSavedContent();
    }

    private void DeleteLocal() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "saves");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    private void LoadSavedContent() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SavedItem");
        query.whereEqualTo("UserId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : objects) {
                        ParseQuery<ParseObject> getItemSaved = ParseQuery.getQuery("Item");
                        getItemSaved.whereEqualTo("LocalId", parseObject.get("ItemId"));
                        getItemSaved.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                File myDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), "saves");
                                if (!myDirectory.exists()) {
                                    myDirectory.mkdir();
                                }
                                ParseFile parseFile = object.getParseFile("ItemFile");
                                parseFile.getFileInBackground(new GetFileCallback() {
                                    @Override
                                    public void done(File file, ParseException e) {
                                        File finalFile = new File(myDirectory + File.separator + file.getName());
                                        try {
                                            copy(file, finalFile);
                                            file.setReadable(true);
                                            finalFile.setWritable(true);
                                            try {
                                                DisplayData();
                                            } catch (IOException ioException) {
                                                ioException.printStackTrace();
                                            } catch (ClassNotFoundException classNotFoundException) {
                                                classNotFoundException.printStackTrace();
                                            }
                                        } catch (IOException ioException) {
                                            ioException.printStackTrace();
                                            Log.e(TAG, ioException.getMessage());
                                        }
                                    }
                                });
                            }
                        });
                    }
                } else {
                    Log.e(TAG, e.getMessage());
                }

            }
        });
    }

    private void DisplayData() throws IOException, ClassNotFoundException {
        songs.clear();
        books.clear();
        news.clear();

        File myDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), "saves");

        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        if (myDirectory.isDirectory()) {
            String[] children = myDirectory.list();
            for (int i = 0; i < children.length; i++) {

                File object = new File(myDirectory, children[i]);
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(object.getAbsolutePath()));

                Item itemaux = (Item) in.readObject();

                if (itemaux.getType() == Item.ItemType.SONG_TYPE) {
                    songs.add((Song) itemaux.getObject());
                } else if (itemaux.getType() == Item.ItemType.BOOK_TYPE) {
                    books.add((Book) itemaux.getObject());
                } else {
                    news.add((News) itemaux.getObject());
                }
            }
        }
        checkRequestsFinished();
    }

    private void checkRequestsFinished() {
        items.clear();
        if (songsselected) {
            for (Song song : songs) {
                Item item = new Item(Item.ItemType.SONG_TYPE, song, song.getRating());
                items.add(item);
            }
        }
        if (booksselected) {
            for (Book book : books) {
                Item item = new Item(Item.ItemType.BOOK_TYPE, book, book.getRating());
                items.add(item);
            }
        }
        if (newsselected) {
            for (News new1 : news) {
                Item item = new Item(Item.ItemType.NEWS_TYPE, new1, new1.getRating());
                items.add(item);
            }
        }
        // if empty, I display a 404 animation
        if (items.isEmpty()) {
            final View view = getLayoutInflater().inflate(R.layout.animated_doggy_error, null);
            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setTitleText("Sorry we couldn't find anything for this query")
                    .setConfirmText("Ok :C")
                    .setCustomView(view)
                    .show();
        }
        //Sort by rating higher to smaller
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            items.sort((o1, o2) -> {
                if (o1.getRating() > o2.getRating()) {
                    return -1;
                }
                if (o1.getRating() < o2.getRating()) {
                    return 1;
                }
                return 0;
            });
        }
        multiSearchAdapter.notifyDataSetChanged();
    }

    //We use this function to copy all the content in a file to another one
    public void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
}