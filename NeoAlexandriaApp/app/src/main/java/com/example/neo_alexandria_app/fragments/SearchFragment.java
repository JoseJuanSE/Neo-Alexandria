package com.example.neo_alexandria_app.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.neo_alexandria_app.Adapters.MultiSearchAdapter;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.DataModels.Item;
import com.example.neo_alexandria_app.DataModels.News;
import com.example.neo_alexandria_app.DataModels.Resource;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.Handlers.Bookhandler;
import com.example.neo_alexandria_app.Handlers.Deezerhandler;
import com.example.neo_alexandria_app.Handlers.Newshandler;
import com.example.neo_alexandria_app.Interfaces.OnBooksCompleted;
import com.example.neo_alexandria_app.Interfaces.OnMusicCompleted;
import com.example.neo_alexandria_app.Interfaces.OnNewsCompleted;
import com.example.neo_alexandria_app.R;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SearchFragment extends Fragment implements OnBooksCompleted, OnMusicCompleted, OnNewsCompleted {

    public static final String TAG = "SearchFragment";

    Toolbar toolbar;
    List<Song> songs;
    List<Book> books;
    List<News> news;
    List<Item> items;
    MultiSearchAdapter multiSearchAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;


    //filter
    Button btnAll, btnBooks, btnSongs, btnNews;
    boolean booksselected, songsselected, newsselected;

    String globalQuery;

    Deezerhandler deezerhandler;
    Bookhandler bookhandler;
    Newshandler newshandler;

    SweetAlertDialog sweetAlertDialog;

    boolean booksRequest, musicRequest, newsRequest;

    //Predefined
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public SearchFragment() {

    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //--------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = view.findViewById(R.id.rvSearch);

        //Set swipe refresh
        swipeRefreshLayout = view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateRecyclerView();
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
        multiSearchAdapter = new MultiSearchAdapter(getContext(), items, "search");
        // Recycler view setup: layout manager and the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(multiSearchAdapter);

        //Set all the handlers
        deezerhandler = new Deezerhandler(getContext(), this::musicTaskCompleted);
        bookhandler = new Bookhandler(this::bookTaskCompleted);
        newshandler = new Newshandler(news1 -> newsTaskCompleted(news1));

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

        globalQuery = "uZfclZEW8MNc4pbhAHbi8HeGPYxfyj";

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseSelected(1);
            }
        });

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseSelected(2);
            }
        });

        btnSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseSelected(3);
            }
        });

        btnBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseSelected(4);
            }
        });


        return view;
    }

    private void showBaseSelected(int _case) {

        turnOnTheButtonSelected(_case);

        if (_case == 1) {
            booksselected = true;
            newsselected = true;
            songsselected = true;
        } else {
            newsselected = false;
            booksselected = false;
            songsselected = false;
            if (_case == 2) {
                newsselected = true;
            }
            if (_case == 3) {
                songsselected = true;
            }
            if (_case == 4) {
                booksselected = true;
            }
        }
        checkRequestsFinished();
    }

    private void turnOnTheButtonSelected(int aCase) {

        btnBooks.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        btnNews.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        btnSongs.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));

        Button button;
        switch (aCase) {
            case 1:
                button = btnAll;
                break;
            case 2:
                button = btnNews;
                break;
            case 3:
                button = btnSongs;
                break;
            default:
                button = btnBooks;
                break;
        }

        button.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.app_bar_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                multiSearchAdapter.clear();

                globalQuery = query;

                populateRecyclerView();

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void populateRecyclerView() {
        //we use this to avoid empty strings and then infinity loops.
        if (globalQuery == "uZfclZEW8MNc4pbhAHbi8HeGPYxfyj") {
            return;
        }
        booksRequest = musicRequest = newsRequest = false;
        //The first that I call should start a SweetDialogAlert Loading.
        final View view = getLayoutInflater().inflate(R.layout.animated_loading, null);
        deezerhandler.getSongs(globalQuery, view);
        bookhandler.getBooks(globalQuery);
        newshandler.getNews(globalQuery);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Here we handle the results

    //notify adapter in the correct one

    @Override
    public void bookTaskCompleted(List<Book> queryBooks) {
        booksRequest = true;
        books.clear();
        books.addAll(queryBooks);
        checkRequestsFinished();
    }

    @Override
    public void musicTaskCompleted(List<Song> querySongs, SweetAlertDialog dialog) {
        musicRequest = true;
        songs.clear();
        songs.addAll(querySongs);
        sweetAlertDialog = dialog;
        checkRequestsFinished();
    }

    @Override
    public void newsTaskCompleted(List<News> newsList) {
        newsRequest = true;
        news.clear();
        news.addAll(newsList);
        checkRequestsFinished();
    }

    //Test code that needs to be change
    private void checkRequestsFinished() {
        if (newsRequest && musicRequest && booksRequest) {
            sweetAlertDialog.cancel();
            //Here we do what we need
            items.clear();
            if (songsselected) {
                for (Song song : songs) {
                    isSaved(song.getId(), (Resource) song, Item.ItemType.SONG_TYPE);
                    Item item = new Item(Item.ItemType.SONG_TYPE, (Resource) song, song.getRating());
                    items.add(item);
                }
            }
            if (booksselected) {
                for (Book book : books) {
                    isSaved(book.getId(), (Resource) book, Item.ItemType.BOOK_TYPE);
                    Item item = new Item(Item.ItemType.BOOK_TYPE, (Resource) book, book.getRating());
                    items.add(item);
                }
            }
            if (newsselected) {
                for (News new1 : news) {
                    isSaved(new1.getId(), (Resource) new1, Item.ItemType.NEWS_TYPE);
                    Item item = new Item(Item.ItemType.NEWS_TYPE, (Resource) new1, new1.getRating());
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
    }

    void isSaved(String itemID, Resource object, @Item.ItemType int itemType) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SavedItem");
        query.whereEqualTo("UserId", ParseUser.getCurrentUser());
        query.whereEqualTo("ItemId", itemID);


        query.countInBackground((count, e) -> {
            if (e == null) {
                if (itemType == Item.ItemType.SONG_TYPE) {
                    Song item = (Song) object;
                    item.setSaved(count > 0);
                } else if (itemType == Item.ItemType.BOOK_TYPE) {
                    Book item = (Book) object;
                    item.setSaved(count > 0);
                } else {
                    News item = (News) object;
                    item.setSaved(count > 0);
                }
            } else {
                Log.e(TAG + "isSaved", e.getMessage());
            }
        });
    }

}