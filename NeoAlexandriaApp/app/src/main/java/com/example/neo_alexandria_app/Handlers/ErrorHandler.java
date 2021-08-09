package com.example.neo_alexandria_app.Handlers;

import androidx.annotation.IntDef;

import com.example.neo_alexandria_app.Interfaces.GeneralErrorDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ErrorHandler {

    @IntDef({ErrorType.ERROR_401, ErrorType.ERROR_403, ErrorType.ERROR_404, ErrorType.ERROR_500, ErrorType.ERROR_GENERAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorType {
        int ERROR_401 = 401;
        int ERROR_403 = 403;
        int ERROR_404 = 404;
        int ERROR_500 = 500;
        int ERROR_GENERAL = 0;
    }

    GeneralErrorDialog generalErrorDialog;

    public ErrorHandler(GeneralErrorDialog generalErrorDialog) {
        this.generalErrorDialog = generalErrorDialog;
    }

    public void error401() {
        String error = "UnAuthorized";
        String errorDetails = "Sorry, this resource is not authorized by author";
        generalErrorDialog.general(error, errorDetails,  ErrorType.ERROR_401);
    }

    public void error403() {
        String error = "Access Denied";
        String errorDetails = "No login opportunity is available";
        generalErrorDialog.general(error, errorDetails, ErrorType.ERROR_403);
    }

    public void error404() {
        String error = "Not Found";
        String errorDetails = "The resource couldn't be found";
        generalErrorDialog.general(error, errorDetails, ErrorType.ERROR_404);
    }

    public void error500() {
        String error = "Internal Server Error";
        String errorDetails = "This resource couldn't be reach because of servers";
        generalErrorDialog.general(error, errorDetails, ErrorType.ERROR_500);
    }

    public void generalError(String error, String errorDetails) {
        generalErrorDialog.general(error, errorDetails, ErrorType.ERROR_GENERAL);
    }
}
