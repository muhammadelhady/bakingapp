package com.example.muham.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.muham.bakingapp.RecipeObject.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {

    SimpleExoPlayer player;
    SimpleExoPlayerView simpleExoPlayerView;
    ArrayList<Step>steps;
    int position;
    TextView stepDescriptionTextView;
    Button Next,Previous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        stepDescriptionTextView=(TextView)findViewById(R.id.stepdescriptionTextView);
        simpleExoPlayerView = findViewById(R.id.simpleExoPlayer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Next= (Button)findViewById(R.id.nextButton);
        Previous=(Button)findViewById(R.id.previousButton);

steps=(ArrayList<Step>)getIntent().getSerializableExtra("steps");
position=getIntent().getIntExtra("position",0);
PreparVideo();
Buttons();
        checkOrientaion();
    }
    void PreparVideo()
    {
       simpleExoPlayerView.setVisibility(View.VISIBLE);
        if(!steps.get(position).getVideoURL().equals(""))
        {
            IntializePlayer(Uri.parse(steps.get(position).getVideoURL()));
        }
        else if(!steps.get(position).getThumbnailURL().equals("")) {
            IntializePlayer(Uri.parse(steps.get(position).getThumbnailURL()));
        }
        else {
simpleExoPlayerView.setVisibility(View.GONE);
stepDescriptionTextView.setText(steps.get(position).getDescription());
        }

    }

    public  void IntializePlayer(Uri uri)
    {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

           player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);



        simpleExoPlayerView.setPlayer(player);

           DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "BakingApp"));

           ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        stepDescriptionTextView.setText(steps.get(position).getDescription());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReleaseVideoResources();
    }



    void Buttons( )
    {
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (++position<steps.size())
                {
                    ReleaseVideoResources();
                    PreparVideo();
                }
                else {
                    position--;
                    Toast.makeText(StepActivity.this,"this is The Last Step",Toast.LENGTH_LONG).show();
                }
            }
        });

        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (--position>=0)
                {
                    ReleaseVideoResources();
                    PreparVideo();
                }
                else {
                    position=0;
                    Toast.makeText(StepActivity.this,"this is The first Step",Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    void ReleaseVideoResources()
    {
        if (player!=null)
        {
            player.stop();
            player.release();
        }

    }

    void checkOrientaion()
    {
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
        {
            stepDescriptionTextView.setVisibility(View.GONE);
            findViewById(R.id.buttonsLayout).setVisibility(View.GONE);
            getSupportActionBar().hide();

        }
    }

}
