package com.example.neo_alexandria_app.fragments;

import android.content.Intent;
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
import com.example.neo_alexandria_app.Handlers.Bookhandler;
import com.example.neo_alexandria_app.Handlers.Deezerhandler;
import com.example.neo_alexandria_app.Handlers.Newshandler;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if(!Environment.isExternalStorageManager())
        {
            Intent permissionIntent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(permissionIntent);
                        }
                    })
                    .setTitleText("Please let us manage your storage")
                    .setConfirmText("Go to permissions")
                    .setContentText("We need access to your storage to be able to save and erase the files that your are saving on the app").show();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = view.findViewById(R.id.rvSearch);
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
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        // Init the list of tweets and adapter
        songs = new ArrayList<>();
        books = new ArrayList<>();
        news = new ArrayList<>();
        items = new ArrayList<>();
        multiSearchAdapter = new MultiSearchAdapter(getContext(), items);
        // Recycler view setup: layout manager and the adapter
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
        LoadSavedContent();
        songs.clear();
        books.clear();
        news.clear();

        File myDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), "saves");
//        File myDirectory = new File(Environment.getDataDirectory(Environment.).getAbsolutePath()+ File.separator + "user/0/com.example.neo_alexandria_app/cache/com.parse/files/");

        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        if (myDirectory.isDirectory()) {
            String[] children = myDirectory.list();
            for (int i = 0; i < children.length; i++) {
                File object = new File(myDirectory, children[i]);
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(object.getAbsolutePath()));
                Item itemaux = (Item) in.readObject();
                Log.e(TAG, String.valueOf(itemaux.getType()));
                if (itemaux.getType() == Item.ItemType.SONG_TYPE) {
                    songs.add((Song) itemaux.getObject());
                } else if (itemaux.getType() == Item.ItemType.BOOK_TYPE) {
                    books.add((Book) itemaux.getObject());
                } else {
                    news.add((News) itemaux.getObject());
                }
            }
        }
        for (Song song: songs){
            Log.e(TAG, song.getTitle());
        }
        checkRequestsFinished();
    }

    private void LoadSavedContent() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SavedItem");
        query.whereEqualTo("UserId", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject: objects) {
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
                                            Log.e(TAG, String.valueOf(file.renameTo(finalFile)));
                                            Log.e(TAG, finalFile.getAbsolutePath());
                                        } catch (IOException ioException) {
                                            ioException.printStackTrace();
                                            Log.e(TAG, ioException.getMessage());
                                        }
                                        Log.e(TAG + "file name: ", file.getAbsolutePath());
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

    private void checkRequestsFinished() {
        //Here we do what we need
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
        // if empty, sorry we couldn't find anything for this query
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