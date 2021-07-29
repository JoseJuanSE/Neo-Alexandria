package com.example.neo_alexandria_app.Handlers;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.neo_alexandria_app.Interfaces.GeneralErrorDialog;
import com.example.neo_alexandria_app.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ErrorHandler {

    public static final int ERROR_401 = 401;
    public static final int ERROR_403 = 403;
    public static final int ERROR_404 = 404;
    public static final int ERROR_500 = 500;
    public static final int ERROR_GENERAL = 0;

    GeneralErrorDialog generalErrorDialog;

    public ErrorHandler(GeneralErrorDialog generalErrorDialog) {
        this.generalErrorDialog = generalErrorDialog;
    }

    public void error401() {
        String error = "UnAuthorized";
        String errorDetails = "Sorry, this resource is not authorized by author";
        generalErrorDialog.general(error, errorDetails, ERROR_401);
    }

    public void error403() {
        String error = "Access Denied";
        String errorDetails = "No login opportunity is available";
        generalErrorDialog.general(error, errorDetails, ERROR_403);
    }

    public void error404() {
        String error = "Not Found";
        String errorDetails = "The resource couldn't be found";
        generalErrorDialog.general(error, errorDetails, ERROR_404);
    }

    public void error500() {
        String error = "Internal Server Error";
        String errorDetails = "This resource couldn't be reach because of servers";
        generalErrorDialog.general(error, errorDetails, ERROR_500);
    }

    public void generalError(String error, String errorDetails) {
        generalErrorDialog.general(error, errorDetails, ERROR_GENERAL);
    }
}
