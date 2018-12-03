package com.legue.axel.bakingapp.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.legue.axel.bakingapp.Constants;
import com.legue.axel.bakingapp.R;
import com.legue.axel.bakingapp.database.ViewModel.RecipeViewModel;
import com.legue.axel.bakingapp.database.ViewModel.StepViewModel;
import com.legue.axel.bakingapp.database.model.Recipe;
import com.legue.axel.bakingapp.database.model.Step;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepDetailFragment extends Fragment {

    private final static String TAG = StepDetailFragment.class.getName();

    @Nullable
    @BindView(R.id.tv_title_recipe)
    TextView recipeTitle;
    @Nullable
    @BindView(R.id.tv_step_indication)
    TextView stepIndication;
    @Nullable
    @BindView(R.id.iv_thumbnail)
    ImageView thumbnail;
    @Nullable
    @BindView(R.id.tv_content_description)
    TextView longDescription;
    @BindView(R.id.exoplayer)
    PlayerView playerView;
    @Nullable
    @BindView(R.id.btn_next)
    Button nextStepButton;
    @Nullable
    @BindView(R.id.btn_previous)
    Button previousStepButton;
    @BindView(R.id.ll_no_source)
    LinearLayout noVideoSource;
    @BindView(R.id.pb_mediaSource)
    ProgressBar loadingMedia;

    private int mFirsStepId;
    private int mLastStepId;

    private Unbinder unbinder;

    private Context mContext;
    private Step stepSelected;
    private SimpleExoPlayer player;
    private StepViewModel stepViewModel;
    private Recipe recipeSelected;

    private boolean isPortrait;

    private int stepSelectedId = -1;

    private Player.EventListener eventListener = new Player.EventListener() {
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
            switch (playbackState) {
                case Player.STATE_BUFFERING:
                    Log.i(TAG, "onPlayerStateChanged STATE_BUFFERING: ");
                    loadingMedia.setVisibility(View.VISIBLE);
                    noVideoSource.setVisibility(View.GONE);
                    playerView.setVisibility(View.VISIBLE);
                    break;
                case Player.STATE_ENDED:
                    loadingMedia.setVisibility(View.GONE);
                    Log.i(TAG, "onPlayerStateChanged STATE_ENDED: ");
                    break;
                case Player.STATE_IDLE:
                    noVideoSource.setVisibility(View.VISIBLE);
                    playerView.setVisibility(View.GONE);
                    Log.i(TAG, "onPlayerStateChanged STATE_IDLE: ");
                    break;
                case Player.STATE_READY:
                    loadingMedia.setVisibility(View.GONE);
                    noVideoSource.setVisibility(View.GONE);
//                    playerView.setVisibility(View.VISIBLE);
                    Log.i(TAG, "onPlayerStateChanged STATE_READY: ");
                    break;
                default:
                    Log.i(TAG, "onPlayerStateChanged default : " + playbackState);
                    break;

            }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        if (getArguments() != null) {
            if (getArguments().containsKey(Constants.KEY_STEPS_ID)) {
                stepSelectedId = getArguments().getInt(Constants.KEY_STEPS_ID);
            }
            if (getArguments().containsKey(Constants.KEY_FIRST_STEP_ID)) {
                mFirsStepId = getArguments().getInt(Constants.KEY_FIRST_STEP_ID);
            }
            if (getArguments().containsKey(Constants.KEY_LAST_STEP_ID)) {
                mLastStepId = getArguments().getInt(Constants.KEY_LAST_STEP_ID);
            }
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.KEY_STEPS_ID)) {
                stepSelectedId = savedInstanceState.getInt(Constants.KEY_STEPS_ID);
            }
            if (savedInstanceState.containsKey(Constants.KEY_FIRST_STEP_ID)) {
                mFirsStepId = savedInstanceState.getInt(Constants.KEY_FIRST_STEP_ID);
            }
            if (savedInstanceState.containsKey(Constants.KEY_LAST_STEP_ID)) {
                mLastStepId = savedInstanceState.getInt(Constants.KEY_LAST_STEP_ID);
            }
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkConfiguration();
        initData();
        if (isPortrait) {
            clickListener();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        checkConfiguration();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (player != null) {
            player.release();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constants.KEY_STEPS_ID, stepSelectedId);
        outState.putInt(Constants.KEY_FIRST_STEP_ID, mFirsStepId);
        outState.putInt(Constants.KEY_LAST_STEP_ID, mLastStepId);

        super.onSaveInstanceState(outState);
    }

    public void updateDetails(int firstStepId, int lastStepId, int stepId) {
        Log.i(TAG, "updateDetails stepId : " + stepId);
        if (player != null) {
            player.stop();
        }

        if (stepId != -1) {
            mFirsStepId = firstStepId;
            mLastStepId = lastStepId;
            stepSelectedId = stepId;

            displayStepIndicator();

            stepViewModel.getStepById(stepSelectedId).observe(this, step -> {
                if (step != null) {
                    stepSelected = step;
                    initializePlayer(stepSelected.getVideoURL());
                    getRecipe(stepSelected.getRecipeId());
                    changeUiInfo(stepSelected);
                }
            });
        }
    }

    private void initData() {

        if (stepSelectedId == mFirsStepId && previousStepButton != null) {
            previousStepButton.setVisibility(View.GONE);
        }
        if (stepSelectedId == mLastStepId && nextStepButton != null) {
            nextStepButton.setVisibility(View.GONE);
        }
        stepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);

        if (stepSelectedId != -1) {
            displayStepIndicator();
            stepViewModel.getStepById(stepSelectedId).observe(this, step -> {
                if (step != null) {
                    stepSelected = step;
                    initializePlayer(stepSelected.getVideoURL());
                    getRecipe(stepSelected.getRecipeId());
                    changeUiInfo(stepSelected);
                }
            });
        }
    }

    private void clickListener() {
        nextStepButton.setOnClickListener(view -> {
            if (player != null) {
                player.stop();
            }
            Log.i(TAG, "onClick nextStepButton ");
            if (stepSelectedId < mLastStepId && mLastStepId != -1) {
                nextStepButton.setVisibility(View.VISIBLE);
                stepSelectedId += 1;
                displayStepIndicator();
                loadStep(stepSelectedId);
            }
            if (stepSelectedId == mLastStepId || mLastStepId == -1) {
                nextStepButton.setVisibility(View.GONE);
            }

            if (!(stepSelectedId == mFirsStepId) || !(mFirsStepId == -1)) {
                previousStepButton.setVisibility(View.VISIBLE);
            }
        });

        previousStepButton.setOnClickListener(view -> {
            if (player != null) {
                player.stop();
            }
            Log.i(TAG, "onClick previousStepButton ");
            if (stepSelectedId > mFirsStepId && mFirsStepId != -1) {
                previousStepButton.setVisibility(View.VISIBLE);
                stepSelectedId -= 1;
                displayStepIndicator();
                loadStep(stepSelectedId);
            }
            if (stepSelectedId == mFirsStepId || mFirsStepId == -1) {
                previousStepButton.setVisibility(View.GONE);
                // Do nothing
            }

            if (!(stepSelectedId == mLastStepId) || !(mLastStepId == -1)) {
                // Do nothing
                nextStepButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getRecipe(int recipeId) {
        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipeById(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                recipeSelected = recipe;
                changeUiInfo(stepSelected);
            }
        });
    }

    /**
     * Only available for Mobile
     *
     */
    private void loadStep(int stepSelectedId) {
        stepViewModel.getStepById(stepSelectedId).observe(this, step -> {
            if (step != null) {
                stepSelected = step;
                initializePlayer(stepSelected.getVideoURL());
                changeUiInfo(stepSelected);
            }
        });
    }

    private boolean isUrlValid(String URL) {
        return stepSelected.getVideoURL() != null && Patterns.WEB_URL.matcher(URL).matches();
    }

    /**
     * available for Mobile and Tablet
     */
    private void changeUiInfo(Step stepSelected) {

        Log.i(TAG, "changeUiInfo: ");
        if (recipeSelected != null && recipeTitle != null) {
            recipeTitle.setText(recipeSelected.getTitle());
        }

        if (longDescription != null) {
            longDescription.setText(stepSelected.getDescription());
        }
        if (isUrlValid(stepSelected.getThumbnailURL()) && thumbnail != null) {
            Picasso.get()
                    .load(stepSelected.getThumbnailURL())
                    .error(R.drawable.placeholder_image)
                    .placeholder(R.drawable.placeholder_image)
                    .into(thumbnail);
        }

    }

    /**
     * Initialise player
     */
    private void initializePlayer(String videoURI) {
        loadingMedia.setVisibility(View.VISIBLE);
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(mContext);
        }

        if (playerView.getPlayer() == null) {
            playerView.setPlayer(player);
        }

        Uri uri;

        if (isUrlValid(videoURI)) {
            uri = Uri.parse(videoURI);
            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                    Util.getUserAgent(mContext, mContext.getString(R.string.app_name)));

            MediaSource videoSource;
            // This is the MediaSource representing the media to be played.
            videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);

            // Add a listener to receive events from the player.
            player.addListener(eventListener);

            // Prepare the player with the source.
            player.prepare(videoSource);
            player.setPlayWhenReady(true);
        } else {
            noVideoSource.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
            loadingMedia.setVisibility(View.GONE);

        }
    }

    private void checkConfiguration() {
        boolean isTablet;
        boolean isLandscape;
        if (recipeTitle == null) {
            isLandscape = true;
            isTablet = false;
            isPortrait = false;
        } else if (previousStepButton == null) {
            isLandscape = false;
            isTablet = true;
            isPortrait = false;
        } else {
            isLandscape = false;
            isTablet = false;
            isPortrait = true;
        }
        if (isPortrait) {
            showSystemUi();
        } else if (isTablet) {
            showSystemUi();
        } else {
            hideSystemUi();
        }

    }

    /**
     * available for mobile
     */
    private void displayStepIndicator() {
        int totalStep = mLastStepId - mFirsStepId;
        int currentStep = stepSelectedId - mFirsStepId;
        if (stepIndication != null) {
            stepIndication.setText(mContext.getResources().getString(R.string.step_indication, currentStep, totalStep));
        }
    }


    private void hideSystemUi() {
        Log.i(TAG, "hideSystemUi: ");
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    private void showSystemUi() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);

    }

}
