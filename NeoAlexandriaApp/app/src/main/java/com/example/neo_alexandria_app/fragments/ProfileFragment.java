package com.example.neo_alexandria_app.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.neo_alexandria_app.Activities.LoginActivity;
import com.example.neo_alexandria_app.Activities.SignupActivity;
import com.example.neo_alexandria_app.Activities.UpdateProfilePictureActivity;
import com.example.neo_alexandria_app.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    ImageView ivProfile;
    ImageView ivAdd;
    TextView tvName;
    TextView tvLogout;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvLogout = view.findViewById(R.id.tvLogout);
        tvName = view.findViewById(R.id.tvUsername);
        ivProfile = view.findViewById(R.id.ivProfile);
        ivAdd = view.findViewById(R.id.ivAdd2);

        tvName.setText(ParseUser.getCurrentUser().getUsername());

        checkCurrentPicture();

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateProfilePictureActivity.class);
                startActivity(intent);

            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Are you sure?")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                ParseUser.logOut();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkCurrentPicture();
    }

    private void checkCurrentPicture() {
        if (ParseUser.getCurrentUser().containsKey("profilePicture")) {
            ParseUser.getCurrentUser().getParseFile("profilePicture").getFileInBackground(new GetFileCallback() {
                @Override
                public void done(File file, ParseException e) {
                    ivProfile.setImageURI(Uri.fromFile(file));
                }
            });
        }
    }
}