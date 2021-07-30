package com.example.neo_alexandria_app.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neo_alexandria_app.DataModels.User;
import com.example.neo_alexandria_app.Handlers.NSFWhandler;
import com.example.neo_alexandria_app.Interfaces.OnNSFWCompleted;
import com.example.neo_alexandria_app.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UpdateProfilePictureActivity extends AppCompatActivity implements OnNSFWCompleted {

    public static final String TAG = "UpdateProfile";

    ImageView ivProfile;
    private File photoFile;
    ParseFile photo;
    NSFWhandler nsfWhandler;
    TextView tvName;
    ImageView ivAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_picture);

        ivProfile = findViewById(R.id.ivProfile);
        tvName = findViewById(R.id.tvUsername);
        tvName.setText(ParseUser.getCurrentUser().getUsername());
        nsfWhandler = new NSFWhandler(this, this::taskCompleted);
        ivAdd = findViewById(R.id.ivAdd2);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

        if (ParseUser.getCurrentUser().containsKey("profilePicture")) {
            ParseUser.getCurrentUser().getParseFile("profilePicture").getFileInBackground(new GetFileCallback() {
                @Override
                public void done(File file, ParseException e) {
                    ivProfile.setImageURI(Uri.fromFile(file));
                }
            });
        }

        uploadPhoto();
    }

    private void uploadPhoto(){
        photoFile = null;
        photo = null;
        //This is possible thanks to https://github.com/Dhaval2404/ImagePicker
        ImagePicker.with(UpdateProfilePictureActivity.this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    private void Updateit() {
        ParseUser user = ParseUser.getCurrentUser();
        if (photoFile != null) {
            user.put(User.KEY_PROFILEIMAGE, photo);
        }
        user.saveInBackground(e -> {
            if (e == null) {
                Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Something went wrong while saving
                Toast.makeText(this, "Something went wrong while saving", Toast.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
                finish();
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

    @Override
    public void taskCompleted(double result, SweetAlertDialog dialog) {
        dialog.cancel();
        if (result > 0.9) {
            //TODO: if there is time change this kind of sweetalerdialog to this ones https://github.com/pedant/sweet-alert-dialog
            new SweetAlertDialog(UpdateProfilePictureActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("nsfw detected")
                    .setContentText("Your profile image was identified as NSFW, select another one.")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            uploadPhoto();
                            sweetAlertDialog.cancel();
                        }
                    })
                    .show();
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Picture uploaded!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Updateit();
                            finish();
                        }
                    })
                    .show();
            ivProfile.setImageURI(Uri.fromFile(photoFile));
        }
    }
}