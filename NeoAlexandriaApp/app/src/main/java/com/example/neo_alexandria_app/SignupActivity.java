package com.example.neo_alexandria_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neo_alexandria_app.DataModels.User;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";

    private ImageView ivProfile;
    private ImageView ivAdd;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ivProfile = findViewById(R.id.ivProfile);
        ivAdd = findViewById(R.id.ivAdd);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        btnSignup = findViewById(R.id.btnSignup);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrir imagenes y escojer una, tambien camara.
                //ponerla en ivProfile
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
    }

    //We use this method to signup our user and start the main activity
    private void signupUser(String userName, String password, String email) {
        //TODO: Here we need image
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
//        ParseFile profileImage = new ParseFile(ivProfile.get);
//        user.put(User.KEY_PROFILEIMAGE,);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignupActivity.this, "User successful signed up", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "error find: "+e);
                }
            }
        });

    }
}