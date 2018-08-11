package com.example.muham.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muham.bakingapp.RecipeObject.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StepFragment extends Fragment {
    SimpleExoPlayer player;
    SimpleExoPlayerView simpleExoPlayerView;
    ArrayList<Step> steps;
    int position;
    TextView stepDescriptionTextView;
    Button Next,Previous;
    ImageView imageView;
    Long playerPosition= Long.valueOf(0);
    boolean readyState=true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView=inflater.inflate(R.layout.step_fragment,container,false);;
        Bundle bundle=getArguments();
        position=bundle.getInt("position");
        steps=(ArrayList<Step>)bundle.getSerializable("steps");
        stepDescriptionTextView=(TextView)rootView.findViewById(R.id.stepdescriptionTextView);
        simpleExoPlayerView = rootView.findViewById(R.id.simpleExoPlayer);
imageView=(ImageView)rootView.findViewById(R.id.imageView);
        Next= (Button)rootView.findViewById(R.id.nextButton);
        Previous=(Button)rootView.findViewById(R.id.previousButton);

        checkOrientaion(rootView);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        PreparVideo();
        Buttons();
    }

    public StepFragment()
    {

    }


    void PreparVideo()
    {
        simpleExoPlayerView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        if(!steps.get(position).getVideoURL().equals(""))
        {
            IntializePlayer(Uri.parse(steps.get(position).getVideoURL()));

        }
        else{
            simpleExoPlayerView.setVisibility(View.GONE);
        }

         if(!steps.get(position).getThumbnailURL().equals("")) {

            Picasso.get().load(steps.get(position).getThumbnailURL()).into(imageView);
        }
        else {

           imageView.setVisibility(View.GONE);
        }
        stepDescriptionTextView.setText(steps.get(position).getDescription());

    }

    public  void IntializePlayer(Uri uri)
    {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        player=null;
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        player.seekTo(playerPosition);
        player.setPlayWhenReady(readyState);

        simpleExoPlayerView.setPlayer(player);


        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getContext(), "BakingApp"));

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);
        player.seekTo(playerPosition);
        player.setPlayWhenReady(readyState);
        player.prepare(videoSource,false,false);


        stepDescriptionTextView.setText(steps.get(position).getDescription());

    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
      playerPosition=      savedInstanceState.getLong("playerposition");
      readyState=savedInstanceState.getBoolean("state");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("playerposition", player.getCurrentPosition() );
        outState.putBoolean("state",player.getPlayWhenReady());
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
                    Toast.makeText(getActivity(),"this is The Last Step",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(),"this is The first Step",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public  Double GetDiagonalInches()
    {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        return  Math.sqrt(xInches*xInches + yInches*yInches);

    }


    void ReleaseVideoResources()
    {
        if (player!=null)
        {
            player.stop();
            player.release();
        }

    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();

        }
    }
    void checkOrientaion(View view)
    {
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE&&GetDiagonalInches()<6.5)
        {
            stepDescriptionTextView.setVisibility(View.GONE);
            view.findViewById(R.id.buttonsLayout).setVisibility(View.GONE);


        }
    }
}
