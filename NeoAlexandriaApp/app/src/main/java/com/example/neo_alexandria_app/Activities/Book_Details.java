package com.example.neo_alexandria_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.OnProgressListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.example.neo_alexandria_app.DataModels.Book;
import com.example.neo_alexandria_app.Handlers.ErrorHandler;
import com.example.neo_alexandria_app.Interfaces.GeneralErrorDialog;
import com.example.neo_alexandria_app.R;
import com.github.barteksc.pdfviewer.PDFView;

import org.parceler.Parcels;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Book_Details extends AppCompatActivity implements OnProgressBarListener, GeneralErrorDialog {
    public static final String TAG = "Book_Details";

    private PDFView pdfView;
    private File myDirectory;
    private NumberProgressBar npb;
    private ErrorHandler errorHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        npb = (NumberProgressBar) findViewById(R.id.progressBar);
        npb.setOnProgressBarListener(this::onProgressChange);

        errorHandler = new ErrorHandler(this);

        Book book = Parcels.unwrap(getIntent().getParcelableExtra("book"));

        pdfView = findViewById(R.id.pdfView);


        PRDownloader.initialize(this);

        myDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath(), "pdfs");

        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        Log.e(TAG, book.toString());
        Log.e(TAG, book.getExternalLink());
        Log.e(TAG, myDirectory.getPath());
        Log.e(TAG, book.getId() + ".pdf");

        npb.setVisibility(View.VISIBLE);
        npb.setMax(100);

        File bookFile = new File(myDirectory.getPath() + File.separator + "a" + book.getId());
        if (bookFile.exists()) {
            npb.setVisibility(View.GONE);
            npb.setProgress(0);
            showFile(bookFile);

        } else {
            PRDownloader.download(book.getExternalLink(), myDirectory.getPath(), "a" + book.getId())
                    .build()
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(Progress progress) {
                            int per =  (int)progress.currentBytes * 100 / (int)progress.totalBytes;
                            npb.setProgress(per);
                        }
                    })
                    .start(new OnDownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            File downloadedFile = new File(myDirectory.getPath() + File.separator + "a" + book.getId());
                            showFile(downloadedFile);
                            npb.setVisibility(View.GONE);
                            npb.setProgress(0);
                        }

                        @Override
                        public void onError(Error error) {
                            Log.e(TAG, String.valueOf(error.getResponseCode()));
                            int errorCode = error.getResponseCode();
                            if (errorCode == ErrorHandler.ErrorType.ERROR_401) {
                                errorHandler.error401();
                            } else if (errorCode == ErrorHandler.ErrorType.ERROR_403) {
                                errorHandler.error403();
                            } else if (errorCode == ErrorHandler.ErrorType.ERROR_404) {
                                errorHandler.error404();
                            } else if (errorCode == ErrorHandler.ErrorType.ERROR_500) {
                                errorHandler.error500();
                            } else{
                                if (error.isConnectionError()) {
                                    Log.e(TAG, "Connection error");
                                    Log.e(TAG, error.getConnectionException().getMessage());
                                    errorHandler.generalError("Connection error", error.getConnectionException().getMessage());
                                } else if (error.isServerError()) {
                                    Log.e(TAG, "server error");
                                    Log.e(TAG, error.getServerErrorMessage());
                                    errorHandler.generalError( "Server error", error.getServerErrorMessage());
                                }
                            }
                        }
                    });
        }
    }

    private void showFile(File file) {
        pdfView.fitToWidth();
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .password(null)
                .scrollHandle(null)
                .enableAntialiasing(true)
                .spacing(0)
                .load();
        file.deleteOnExit();
    }

    @Override
    public void onProgressChange(int current, int max) {
        if (current == max) {
            npb.setProgress(0);
            npb.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void general(String error, String errorDetails, int viewType) {
        View view;
        if (viewType == ErrorHandler.ErrorType.ERROR_GENERAL) {
            view = getLayoutInflater().inflate(R.layout.animated_errorgeneral, null);
        } else if (viewType == ErrorHandler.ErrorType.ERROR_500) {
            view = getLayoutInflater().inflate(R.layout.animated_error500, null);
        } else if (viewType == ErrorHandler.ErrorType.ERROR_404) {
            view = getLayoutInflater().inflate(R.layout.animated_error404, null);
        } else if (viewType == ErrorHandler.ErrorType.ERROR_403) {
            view = getLayoutInflater().inflate(R.layout.animated_error403, null);
        } else {
            view = getLayoutInflater().inflate(R.layout.animated_error401, null);
        }
        TextView tv = view.findViewById(R.id.textContent);
        tv.setText(errorDetails);
        new SweetAlertDialog(Book_Details.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(error)
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pdfView.stopFling();
                        finish();
                    }
                })
                .setCustomView(view)
                .showContentText(true)
                .setContentText(errorDetails)
                .show();
    }
}