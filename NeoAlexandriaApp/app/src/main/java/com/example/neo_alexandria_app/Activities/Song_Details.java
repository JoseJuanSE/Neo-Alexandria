package com.example.neo_alexandria_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.Handlers.StringsHandler;
import com.example.neo_alexandria_app.R;

import org.parceler.Parcels;

import de.hdodenhof.circleimageview.CircleImageView;

public class Song_Details extends AppCompatActivity {

    public static final String TAG = "Song_Details";

    String externalLink;

    //TODO: get this stuff from data base when ready
    int commentCount;
    int saveCount;
    boolean isSaved;
    float rating;
    String id;
    String albumTracksLinkAPIQuery;

    Song song;

    ImageView ivCover;
    TextView tvExplicit;
    TextView tvTitle;
    TextView tvAlbumD;
    TextView tvAuthorD;
    SeekBar positionBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    Button playBtn;
    RatingBar rbStars;
    ImageView ivComment;
    TextView tvNumberComments;
    ImageView ivSave;
    TextView tvNumberSaves;
    TextView tvSource;
    CircleImageView ivAuthor;
    SeekBar volumeBar;
    ImageView repeat;

    //MediaPlayer
    MediaPlayer mp;
    int totalTime;
    boolean isRepetitive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        ivCover = findViewById(R.id.ivCover);
        tvExplicit = findViewById(R.id.tvExplicitD);
        tvTitle = findViewById(R.id.tvTitle);
        tvAlbumD = findViewById(R.id.tvAlbumD);
        tvAuthorD = findViewById(R.id.tvAuthorD);
        rbStars = findViewById(R.id.rbStars);
        tvNumberComments = findViewById(R.id.tvNumberComments);
        tvNumberSaves = findViewById(R.id.tvNumberSaves);
        ivAuthor = findViewById(R.id.ivAuthor);
        ivComment = findViewById(R.id.ivComment);
        ivSave = findViewById(R.id.ivSave);
        tvSource = findViewById(R.id.tvSource);
        repeat = findViewById(R.id.ivRepeat);
        isRepetitive = false;

        //Here we set all what we are going to need on this activity
        song = Parcels.unwrap(getIntent().getParcelableExtra("song"));

        if (!song.getImageLink().isEmpty()) {
            Glide.with(this).load(song.getCoverBig())
                    .into(ivCover);
        }

        tvTitle.setText(StringsHandler.limited(song.getTitle(), 30));
        tvAlbumD.setText(StringsHandler.limited(song.getAlbumTitle(), 42));
        tvAuthorD.setText(StringsHandler.limited(song.getAuthorName(), 50));
        tvNumberComments.setText(String.valueOf(song.getCommentCount()));
        tvNumberSaves.setText(String.valueOf(song.getSaveCount()));
        rbStars.setRating(song.getRating());

        if (song.isExplicitContent() == true) {
            tvExplicit.setText("Explicit");
        } else {
            tvExplicit.setText("");
        }
        //ivComment and ivSave set on click listener here when available
        if (!song.getArtistPicture().isEmpty()) {
            Glide.with(this).load(song.getArtistPicture())
                    .into(ivAuthor);
        }

        tvSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(song.getExternalLink()));
                startActivity(browserIntent);
            }
        });

        tvAuthorD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + song.getAuthorName()));
                startActivity(browserIntent);
            }
        });

        // Media Player
        positionBar = findViewById(R.id.positionBar);
        elapsedTimeLabel = findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = findViewById(R.id.remainingTimeLabel);
        playBtn = findViewById(R.id.playBtn);

        //Here we get the song
        Uri myUri = Uri.parse(song.getMP3Link());
        mp = MediaPlayer.create(this, myUri);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();

        // Position Bar
        positionBar = findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        // Volume Bar
        volumeBar = findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float volumeNum = progress / 100f;
                        mp.setVolume(volumeNum, volumeNum);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        //Repeat
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepetitive == false) {
                    isRepetitive = true;
                    repeat.setColorFilter(ContextCompat.getColor(Song_Details.this, R.color.blue));
                } else {
                    isRepetitive = false;
                    repeat.setColorFilter(ContextCompat.getColor(Song_Details.this, R.color.black));
                }
            }
        });

        // Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        //When the song ends it stops.
                        if (totalTime - mp.getCurrentPosition() < 1000 && !isRepetitive) {
                            mp.pause();
                            mp.seekTo(0);
                            playBtn.setBackgroundResource(R.drawable.play);
                        }
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();

    }

    //Media player auxiliary functions
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = StringsHandler.createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = "- " + StringsHandler.createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText(remainingTime);


            return true;
        }
    });

    public void playBtnClick(View view) {
        if (!mp.isPlaying()) {
            // Stopping
            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
        }
    }
}