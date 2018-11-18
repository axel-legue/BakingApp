package com.legue.axel.bankingapp.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.database.ViewModel.StepViewModel;
import com.legue.axel.bankingapp.database.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepDetailFragment extends Fragment {

    private final static String TAG = StepDetailFragment.class.getName();

    @BindView(R.id.exoplayer)
    PlayerView playerView;

    private Unbinder unbinder;
    int stepId;
    private Context mContext;
    private Step stepSelected;
    private SimpleExoPlayer player;
    private StepViewModel stepViewModel;

    Player.EventListener eventListener = new Player.EventListener() {
        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.i(TAG, "onTracksChanged: ");

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(TAG, "onLoadingChanged: ");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.i(TAG, "onPlayerStateChanged: ");
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            Log.i(TAG, "onRepeatModeChanged: ");
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i(TAG, "onPlayerError: ");
        }
    };

    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        if (getArguments() != null && getArguments().containsKey(Constants.KEY_STEPS_ID)) {
            stepId = getArguments().getInt(Constants.KEY_STEPS_ID);
        } else {
            stepId = -1;
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        //TODO : reference listener for previous and next here.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        player.release();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        stepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        stepViewModel.getStepById(stepId).observe(this, step -> {
            if (step != null) {
                stepSelected = step;
                initializePlayer(Uri.parse(stepSelected.getVideoURL()));
            }
        });


        playerView.setDefaultArtwork(mContext.getDrawable(R.drawable.placeholder_image));
//        initializePlayer(Uri.parse(stepSelected.getVideoURL()));

    }

    private void initializePlayer(Uri videoURI) {
        player = ExoPlayerFactory.newSimpleInstance(mContext);
        playerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, mContext.getString(R.string.app_name)));

        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoURI);

        // Add a listener to receive events from the player.
        player.addListener(eventListener);

        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

        /**
         * That how we create a PlayList
         */
//        MediaSource firstSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoURI);
//        MediaSource secondSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoURI);
//
//        ConcatenatingMediaSource concatenatedSource = new ConcatenatingMediaSource(firstSource,secondSource);


    }


}
