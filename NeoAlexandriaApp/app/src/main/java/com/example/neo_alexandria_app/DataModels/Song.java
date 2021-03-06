package com.example.neo_alexandria_app.DataModels;

import android.util.Log;

import com.parse.CountCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Parcel
public class Song extends Resource implements Serializable {

    public static final String TAG = "Song";

    public static final String ID = "id";
    public static final String TITLE = "title_short";
    public static final String ARTIST = "artist";
    public static final String NAME = "name";
    public static final String ALBUM = "album";
    public static final String COVER = "cover_medium";
    public static final String COVER_BIG = "cover_big";
    public static final String LINK = "link";
    public static final String EXPLICIT = "explicit_lyrics";
    public static final String PREVIEW = "preview";
    public static final String ARTIST_PICTURE = "picture_medium";
    public static final String ALBUM_TITLE = "title";
    public static final String TRACK_LIST = "tracklist";

    boolean explicitContent;
    String MP3Link;
    String coverBig;
    String artistPicture;
    String albumTitle;
    String albumTracksLinkAPIQuery;

    public Song() {

    }

    //Here we recived the jsonObject that API send us, and we push all the needed date
    //into the properties of this class (body, createdAt, user (another custom object), etc...)
    public static Song fromJson(JSONObject jsonObject) throws JSONException, ParseException {

        Song song = new Song();

        song.id = String.valueOf(jsonObject.getLong(Song.ID));
        song.title = jsonObject.getString(Song.TITLE);

        song.externalLink = jsonObject.getString(Song.LINK);
        song.explicitContent = jsonObject.getBoolean(Song.EXPLICIT);
        song.MP3Link = jsonObject.getString(Song.PREVIEW);

        JSONObject artist = jsonObject.getJSONObject(Song.ARTIST);
        song.authorName = artist.getString(Song.NAME);
        song.artistPicture = artist.getString(Song.ARTIST_PICTURE);

        JSONObject album = jsonObject.getJSONObject(Song.ALBUM);
        song.imageLink = album.getString(Song.COVER);
        song.albumTitle = album.getString(Song.ALBUM_TITLE);
        song.albumTracksLinkAPIQuery = album.getString(Song.TRACK_LIST);
        song.coverBig = album.getString(Song.COVER_BIG);

        //This have to be fill with parse
        song.commentCount = 0;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Comments");
        query.whereEqualTo("ItemId", song.id);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, com.parse.ParseException e) {
                if (e == null) {
                    song.commentCount = count;
                }
            }
        });

        song.saveCount = (int) Math.max(3, 100 * Math.random());
        song.isSaved = false;
        song.rating = Math.max(1f, 5 * (float) Math.random());

        return song;
    }

    public static List<Song> fromJsonArray(JSONArray jsonArray) throws JSONException, ParseException {

        List<Song> songs = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            songs.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return songs;
    }

    public boolean isExplicitContent() {
        return explicitContent;
    }

    public String getMP3Link() {
        return MP3Link;
    }

    public String getArtistPicture() {
        return artistPicture;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getAlbumTracksLinkAPIQuery() {
        return albumTracksLinkAPIQuery;
    }

    public String getCoverBig() {
        return coverBig;
    }
}

