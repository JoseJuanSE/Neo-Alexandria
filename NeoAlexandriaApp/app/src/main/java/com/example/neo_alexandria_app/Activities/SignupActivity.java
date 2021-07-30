package com.example.neo_alexandria_app.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.neo_alexandria_app.DataModels.User;
import com.example.neo_alexandria_app.Handlers.NSFWhandler;
import com.example.neo_alexandria_app.Interfaces.OnNSFWCompleted;
import com.example.neo_alexandria_app.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignupActivity extends AppCompatActivity implements OnNSFWCompleted {

    public static final String TAG = "SignupActivity";

    private ImageView ivProfile;
    private ImageView ivAdd;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignup;
    private File photoFile;
    ParseFile photo;
    NSFWhandler nsfWhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ivProfile = findViewById(R.id.ivProfile);
        ivAdd = findViewById(R.id.ivAdd);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnSignup = findViewById(R.id.btnUpdateit);


        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFile = null;
                photo = null;
                //This is possible thanks to https://github.com/Dhaval2404/ImagePicker
                ImagePicker.with(SignupActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupUser(etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        etEmail.getText().toString());
            }
        });
        nsfWhandler = new NSFWhandler(this, this::taskCompleted);
    }

    //We use this method to signup our user and start the main activity
    private void signupUser(String userName, String password, String email) {
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        // if there is not image, I will display a placeholder so I can upload nothing
        if (photoFile != null) {
            user.put(User.KEY_PROFILEIMAGE, photo);
        }
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "error find: " + e);
                } else {
                    Toast.makeText(SignupActivity.this, "User successful signed up", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        photoFile = new File(uri.getPath());

        if (photoFile != null) {
            photo = new ParseFile(photoFile);
            photo.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while saving" + e);
                        return;
                    } else {
                        if (photoFile != null) {
                            nsfWhandler.get(photo.getUrl());
                        }
                    }
                }
            });
        }

    }

//    I created this function to rename internal files, but I realize I won't need it
//    private boolean rename(File from, File to) {
//        return from.getParentFile().exists() && from.exists() && from.renameTo(to);
//    }

    @Override
    public void taskCompleted(double result, SweetAlertDialog dialog) {
        dialog.cancel();
        if (result > 0.9) {
            //TODO: if there is time change this kind of sweetalerdialog to this ones https://github.com/pedant/sweet-alert-dialog
            new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("nsfw detected")
                    .setContentText("Your profile image was identified as NSFW, select another one.").show();
        } else {
            ivProfile.setImageURI(Uri.fromFile(photoFile));
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Picture uploaded!")
                    .show();
        }
    }
}