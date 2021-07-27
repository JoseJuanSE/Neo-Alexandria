package com.example.neo_alexandria_app.DataModels;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Parcel
public class Book extends Resource {

    public static final String TAG = "Book";

    public static final String ID = "_id";
    public static final String SOURCE = "_source";
    public static final String TITLE = "title";
    public static final String COVER = "coverurl";
    public static final String PAGES = "pages";
    public static final String SIZE = "size";
    public static final String METADATA = "metadata";
    public static final String FORMAT = "format";
    public static final String AUTHOR = "author";
    public static final String SUBJECT = "subject";
    public static final String DESCRIPTION = "keywords";
    public static final String PRODUCER = "producer";
    public static final String LINK = "external_link";

    public static final String LOCAL_SERVER = "https://cdn.bookmeth.com/";

    int pages;
    Double size;
    String format;
    String subject;
    String description;
    String editor;

    public Book() {
        super();
    }

    //Here we received the jsonObject that API send us, and we push all the needed date
    //into the properties of this class (body, createdAt, user (another custom object), etc...)
    public static Book fromJson(JSONObject jsonObject) throws JSONException, ParseException {

        Book book = new Book();

        book.id = jsonObject.getString(Book.ID);

        JSONObject source = jsonObject.getJSONObject(Book.SOURCE);
        book.title = source.getString(Book.TITLE);

        if (source.has(Book.AUTHOR)) {
            book.authorName = source.getString(Book.AUTHOR);
        }

//        book.imageLink = Book.LOCAL_SERVER + source.getString(Book.COVER);
//        book.externalLink = source.getString(Book.LINK);
//        book.pages = source.getInt(Book.PAGES);
//        //round to a decimal
//        book.size = Math.ceil(source.getDouble(Book.SIZE) / 1e5) / 10;
//
//        JSONObject metadata = source.getJSONObject(Book.METADATA);
////        book.authorName = metadata.getString(Book.AUTHOR);
//        book.authorName = "JK rowlling";
//        book.format = metadata.getString(Book.FORMAT);
////        book.subject = metadata.getString(Book.SUBJECT);
////        book.description = metadata.getString(Book.DESCRIPTION);
//        book.description = "";
//        book.editor = metadata.getString(Book.PRODUCER);
//
//        //This have to be fill with parse
//        book.commentCount = 0;
//        book.saveCount = 0;
//        book.isSaved = false;
//        book.rating = Math.max(3.5f, 5 * (float) Math.random());

        return book;
    }

    public static List<Book> fromJsonArray(JSONArray jsonArray) throws JSONException, ParseException {

        List<Book> books = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            books.add(fromJson(jsonArray.getJSONObject(i)));
        }

        return books;
    }

    public int getPages() {
        return pages;
    }

    public Double getSize() {
        return size;
    }

    public String getFormat() {
        return format;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getEditor() {
        return editor;
    }
}
