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

import java.util.ArrayList;

public class StepFragment extends Fragment {
    SimpleExoPlayer player;
    SimpleExoPlayerView simpleExoPlayerView;
    ArrayList<Step> steps;
    int position;
    TextView stepDescriptionTextView;
    Button Next,Previous;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.step_fragment,container,false);;
        Bundle bundle=getArguments();
        position=bundle.getInt("position");
        steps=(ArrayList<Step>)bundle.getSerializable("steps");
        stepDescriptionTextView=(TextView)rootView.findViewById(R.id.stepdescriptionTextView);
        simpleExoPlayerView = rootView.findViewById(R.id.simpleExoPlayer);

        Next= (Button)rootView.findViewById(R.id.nextButton);
        Previous=(Button)rootView.findViewById(R.id.previousButton);
        PreparVideo();
        Buttons();
        checkOrientaion(rootView);
        return rootView;
    }

    public StepFragment()
    {

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

        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);



        simpleExoPlayerView.setPlayer(player);

        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getContext(), "BakingApp"));

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource videoSource = new ExtractorMediaSource(uri,
                dataSourceFactory, extractorsFactory, null, null);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        stepDescriptionTextView.setText(steps.get(position).getDescription());

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

    void checkOrientaion(View view)
    {
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE&&GetDiagonalInches()<6.5)
        {
            stepDescriptionTextView.setVisibility(View.GONE);
            view.findViewById(R.id.buttonsLayout).setVisibility(View.GONE);


        }
    }
}
