package com.example.neo_alexandria_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ThemedSpinnerAdapter;

import com.bumptech.glide.Glide;
import com.example.neo_alexandria_app.DataModels.Song;
import com.example.neo_alexandria_app.R;

import org.parceler.Parcels;

import de.hdodenhof.circleimageview.CircleImageView;

public class Song_Details extends AppCompatActivity {

    public static final String TAG = "Song_Details";

    String externalLink;

    //Not yet
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

    //MediaPlayer
    MediaPlayer mp;
    int totalTime;

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

        song = Parcels.unwrap(getIntent().getParcelableExtra("song"));

        if ( !song.getImageLink().isEmpty() ) {
            Glide.with(this).load(song.getImageLink())
                    .into(ivCover);
        }
        tvTitle.setText(limited(song.getTitle(),13));
        tvAlbumD.setText(limited(song.getAlbumTitle(),22));
        tvAuthorD.setText(limited(song.getAuthorName(),25));
        tvNumberComments.setText(String.valueOf(song.getCommentCount()));
        tvNumberSaves.setText(String.valueOf(song.getSaveCount()));
        rbStars.setRating(song.getRating());

        if ( song.isExplicitContent() == true) {
            tvExplicit.setText("Explicit");
        } else {
            tvExplicit.setText("");
        }
        //ivComment and ivSave set on click listener
        if ( !song.getArtistPicture().isEmpty() ) {
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

        // Media Player
        positionBar = findViewById(R.id.positionBar);
        elapsedTimeLabel = findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = findViewById(R.id.remainingTimeLabel);
        playBtn = findViewById(R.id.playBtn);

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

        // Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                }
            }
        }).start();

    }
    // TODO: I can use just one time this instead of searchadapter and here
    private String limited(String title, int lit) {
        String ans="";

        for (int i=0; i < Math.min(lit,title.length()); i++) {
            ans += title.charAt(i);
        }
        if (title.length() > lit) {
            ans += "...";
        }
        return ans;
    }

    //Media player auxiliary functions
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int currentPosition = msg.what;
            // Update positionBar.
            positionBar.setProgress(currentPosition);

            // Update Labels.
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = "- " + createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText(remainingTime);


            return true;
        }
    });

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

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