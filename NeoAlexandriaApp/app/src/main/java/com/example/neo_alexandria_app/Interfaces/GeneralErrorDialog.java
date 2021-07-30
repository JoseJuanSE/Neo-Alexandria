package com.example.neo_alexandria_app.Interfaces;

import android.view.View;

import com.example.neo_alexandria_app.Handlers.ErrorHandler;

public interface GeneralErrorDialog {
    public void general(String error, String errorDetails, @ErrorHandler.ErrorType int viewType);
}
