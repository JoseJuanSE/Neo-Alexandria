package com.example.neo_alexandria_app.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Toast;

import com.example.neo_alexandria_app.Adapters.SearchAdapter;
import com.example.neo_alexandria_app.BuildConfig;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.DataModels.News;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.Handlers.Bookhandler;
import com.example.neo_alexandria_app.Handlers.Deezerhandler;
import com.example.neo_alexandria_app.Handlers.Newshandler;
import com.example.neo_alexandria_app.Interfaces.OnBooksCompleted;
import com.example.neo_alexandria_app.Interfaces.OnMusicCompleted;
import com.example.neo_alexandria_app.Interfaces.OnNewsCompleted;
import com.example.neo_alexandria_app.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements OnBooksCompleted, OnMusicCompleted, OnNewsCompleted {

    public static final String TAG = "SearchFragment";

    Toolbar toolbar;
    List<Song> songs;
    List<Book> books;
    List<String> news;
    SearchAdapter searchAdapter;
    RecyclerView recyclerView;

    Deezerhandler deezerhandler;
    Bookhandler bookhandler;
    Newshandler newshandler;

    SweetAlertDialog sweetAlertDialog;

    boolean booksRequest;
    boolean musicRequest;
    boolean newsRequest;

    public MediaPlayer mp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = view.findViewById(R.id.rvSearch);
        // Init the list of tweets and adapter
        songs = new ArrayList<>();
        books = new ArrayList<>();
        news = new ArrayList<>();
        searchAdapter = new SearchAdapter(getContext(), songs);
        // Recycler view setup: layout manager and the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(searchAdapter);

        //Set all the handlers
        deezerhandler = new Deezerhandler(getContext(), this::musicTaskCompleted);
        bookhandler = new Bookhandler(this::bookTaskCompleted);
        newshandler = new Newshandler(this::newsTaskCompleted);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.app_bar_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.clear();

                booksRequest = musicRequest = newsRequest = false;

                //The first that I call should start a SweetDialogAlert Loading.

                deezerhandler.getSongs(query);
                bookhandler.getBooks(query);
                newshandler.getNews(query);

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
    public void newsTaskCompleted(List<String> newsList) {
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
            for (Song song : songs) {
                Log.e(TAG, song.getTitle() + " song");
            }
            for (Book book : books) {
                Log.e(TAG, book.getTitle() + " book");
            }
            for (String newstitle : news) {
                Log.e(TAG, newstitle);
            }
            searchAdapter.notifyDataSetChanged();
        }
    }
}