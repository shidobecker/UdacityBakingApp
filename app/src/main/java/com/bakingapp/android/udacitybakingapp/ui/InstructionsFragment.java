package com.bakingapp.android.udacitybakingapp.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakingapp.android.udacitybakingapp.R;
import com.bakingapp.android.udacitybakingapp.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsFragment extends Fragment {

    public static String TAG = InstructionsFragment.class.getCanonicalName();

    enum ThumbnailFormats {
        PNG("PNG"), JPG("JPG");

        String format;

        ThumbnailFormats(String format) {
            this.format = format;
        }
    }

    @BindView(R.id.step_player_view)
    PlayerView playerView;

    @BindView(R.id.step_image_no_video)
    ImageView noVideoImage;

    @BindView(R.id.step_short_description)
    TextView shortDescription;

    @BindView(R.id.step_description)
    TextView description;

    SimpleExoPlayer simpleExoPlayer;

    private static final String THUMBNAIL_ARG = "THUMBNAIL_ARG";

    private static final String VIDEO_ARG = "VIDEO_ARG";

    private static final String SHORT_DESCRIPTION_ARG = "SHORT_DESCRIPTION_ARG";

    private static final String DESCRIPTION_ARG = "DESCRIPTION_ARG";


    //Saved instance related variables
    private static final String STATE_STEP = "STATE_STEP";

    private static final String STATE_VIDEO_POSITION = "STATE_VIDEO_POSITION";

    private static final String STATE_SHOULD_PLAY_WHEN_READY = "STATE_SHOULD_PLAY_WHEN_READY";

    private long videoPosition = 0L;

    private boolean shouldPlayWhenReady = true;


    Step step;

    public InstructionsFragment() {

    }

    public static InstructionsFragment newInstance(
            String stepShortDescription, String stepDescription,
            String thumbnail, String videoUrl) {

        InstructionsFragment instructionsFragment = new InstructionsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(THUMBNAIL_ARG, thumbnail);
        bundle.putString(VIDEO_ARG, videoUrl);
        bundle.putString(SHORT_DESCRIPTION_ARG, stepShortDescription);
        bundle.putString(DESCRIPTION_ARG, stepDescription);

        instructionsFragment.setArguments(bundle);

        return instructionsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);

        ButterKnife.bind(this, view);

        //Returning Step and Video from savedInstance first
        if (savedInstanceState != null) {
            String stepString = savedInstanceState.getString(STATE_STEP);

            step = new Gson().fromJson(stepString, Step.class);
            videoPosition = savedInstanceState.getLong(STATE_VIDEO_POSITION);
            shouldPlayWhenReady = savedInstanceState.getBoolean(STATE_SHOULD_PLAY_WHEN_READY);

        } else {
            if (getArguments() != null) {
                step = new Step(getArguments().getString(SHORT_DESCRIPTION_ARG),
                        getArguments().getString(DESCRIPTION_ARG),
                        getArguments().getString(THUMBNAIL_ARG),
                        getArguments().getString(VIDEO_ARG));
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDescription();
    }


    private void setDescription() {
        shortDescription.setText(step.getShortDescription());
        description.setText(step.getDescription());
    }

    private void initializePlayer() {
        setThumbnail();

        if (!step.getVideoURL().equals("")) {

            if (simpleExoPlayer == null) {

                playerView.setVisibility(View.VISIBLE);
                noVideoImage.setVisibility(View.GONE);

                MediaSource mediaSource = prepareMediaSource();

                simpleExoPlayer.prepare(mediaSource);
                simpleExoPlayer.setPlayWhenReady(shouldPlayWhenReady);

                if (videoPosition != C.TIME_UNSET ) {
                    simpleExoPlayer.seekTo(videoPosition);
                }
            }
        } else {
            playerView.setVisibility(View.GONE);
            noVideoImage.setVisibility(View.VISIBLE);
        }
    }

    private MediaSource prepareMediaSource() {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                trackSelector, loadControl);
        playerView.setPlayer(simpleExoPlayer);

        //Preparing media source
        String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));

        DefaultDataSourceFactory factory = new DefaultDataSourceFactory(getContext(), userAgent);

        return new ExtractorMediaSource.Factory(factory)
                .createMediaSource(Uri.parse(step.getVideoURL()), null, null);
    }


    private void setThumbnail() {
        if (step.getThumbnailURL() != null && !step.getThumbnailURL().equals("")) {
            String mimetype = getMimeType(step.getThumbnailURL());

            if (mimetype.equals(ThumbnailFormats.PNG.format) || mimetype.equals(ThumbnailFormats.JPG.format)) {
                try {
                    Bitmap imageInBitmap = Picasso.get().load(step.getThumbnailURL()).get();
                    playerView.setDefaultArtwork(imageInBitmap);

                } catch (IOException e) {
                    playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.thumbnail_unavailable));
                }
            } else {
                playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.thumbnail_unavailable));
            }
        } else {
            playerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.thumbnail_unavailable));
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
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer != null)
            setVideoLastPosition();

        if (Util.SDK_INT <= 23) {

            releasePlayer();

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23 && simpleExoPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 && simpleExoPlayer == null)) {
            initializePlayer();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        String stepInJson = new Gson().toJson(step);
        outState.putString(STATE_STEP, stepInJson);
        outState.putLong(STATE_VIDEO_POSITION, videoPosition);
        outState.putBoolean(STATE_SHOULD_PLAY_WHEN_READY, shouldPlayWhenReady);

        super.onSaveInstanceState(outState);
    }

    //To guarantee that thumbnail is an image
    private static String getMimeType(String url) {
        String type = "";
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    private void setVideoLastPosition() {
        if (simpleExoPlayer != null) {
            shouldPlayWhenReady = simpleExoPlayer.getPlayWhenReady();
            videoPosition = simpleExoPlayer.getCurrentPosition();
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
        simpleExoPlayer = null;
    }


}
