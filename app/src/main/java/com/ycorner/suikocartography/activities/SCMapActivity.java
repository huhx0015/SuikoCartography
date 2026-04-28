package com.ycorner.suikocartography.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.huhx0015.hxaudio.audio.HXMusic;
import com.huhx0015.hxaudio.audio.HXSound;
import com.huhx0015.hxaudio.utils.HXAudioPlayerUtils;
import com.squareup.picasso.Picasso;
import com.ycorner.suikocartography.R;
import com.ycorner.suikocartography.constants.SCConstants;
import com.ycorner.suikocartography.databinding.ActivityMapBinding;
import com.ycorner.suikocartography.utils.SCGameUtility;

/**
 * Created by huhx0015 on 12/17/15.
 */
public class SCMapActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // ANIMATION VARIABLES
    private AnimationDrawable loadingAnimation;

    // GAME VARIABLES
    private String gameId;

    // LOGGING VARIABLES
    private static final String LOG_TAG = SCMapActivity.class.getSimpleName();

    // VIEW BINDING VARIABLES
    private ActivityMapBinding binding;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.suikodenMapView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onSuikodenMapViewLongClicked();
            }
        });

        if (savedInstanceState != null) {
            gameId = savedInstanceState.getString(SCConstants.EXTRA_GAME_NAME);
        } else {
            gameId = getIntent().getExtras().getString(SCConstants.EXTRA_GAME_NAME);
        }

        Log.d(LOG_TAG, "onCreate(): Game selection: " + gameId);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HXMusic.resume(this);
        HXAudioPlayerUtils.enableSystemSound(false, this); // Temporarily disables the physical button's sound effects.
    }

    @Override
    protected void onPause() {
        super.onPause();
        HXMusic.pause(); // Pauses any song that is playing in the background.
        HXAudioPlayerUtils.enableSystemSound(false, this); // Re-enables the physical button's sound effects.
    }

    /** ACTIVITY OVERRIDE METHODS ______________________________________________________________ **/

    @Override
    public void onBackPressed() {
        HXSound.sound()
                .load(R.raw.gs_menu_cancel)
                .play(this);
        HXMusic.stop();
        launchTitleIntent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SCConstants.EXTRA_GAME_NAME, gameId);
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initView() {
        int mapLoadingResource;
        int mapResource;
        int mapBackgroundResource;
        int mapTitleBackgroundResource;
        int musicResource = SCGameUtility.getRandomMusic(gameId);
        String mapTitle;

        switch (gameId) {
            case SCConstants.GENSO_SUIKODEN_1_ID:
                mapTitle = getString(R.string.suikoden_1_map_name);
                mapLoadingResource = R.drawable.gs1_loading_animation;
                mapResource = R.drawable.gs1_world_map;
                mapBackgroundResource = R.drawable.gs1_map_background;
                mapTitleBackgroundResource = R.drawable.gs1_window_unselected;
                break;
            case SCConstants.GENSO_SUIKODEN_2_ID:
                mapTitle = getString(R.string.suikoden_2_map_name);
                mapLoadingResource = R.drawable.gs2_loading_animation;
                mapResource = R.drawable.gs2_world_map;
                mapBackgroundResource = R.drawable.gs2_map_background;
                mapTitleBackgroundResource = R.drawable.gs2_window_unselected;
                break;
            default:
                mapTitle = getString(R.string.suikoden_1_map_name);
                mapLoadingResource = R.drawable.gs1_loading_animation;
                mapResource = R.drawable.gs1_world_map;
                mapBackgroundResource = R.drawable.gs1_map_background;
                mapTitleBackgroundResource = R.drawable.gs1_window_unselected;
        }

        // LOADING ANIMATION:
        binding.suikodenLoadingAnimation.setScaleType(ImageView.ScaleType.FIT_CENTER);
        binding.suikodenLoadingAnimation.setBackgroundResource(mapLoadingResource);
        loadingAnimation = (AnimationDrawable) binding.suikodenLoadingAnimation.getBackground();
        enableLoadingAnimation(true);

        // MAP TITLE:
        binding.suikodenMapTitleContainer.setBackgroundResource(mapTitleBackgroundResource);
        binding.suikodenMapTitle.setText(mapTitle);

        // MAP BACKGROUND:
        Picasso.with(this)
                .load(mapBackgroundResource)
                .config(Bitmap.Config.RGB_565)
                .into(binding.suikodenMapBackground);

        // MUSIC:
        HXMusic.music()
                .load(musicResource)
                .looped(true)
                .gapless(true)
                .play(this);

        // TOUCH MAP VIEW:
        binding.suikodenMapView.setOnImageEventListener(new SubsamplingScaleImageView.OnImageEventListener() {
            @Override
            public void onReady() {
                Log.d(LOG_TAG, "onReady(): Image is ready.");
            }

            @Override
            public void onImageLoaded() {
                Log.d(LOG_TAG, "onImageLoaded(): Image was loaded.");
                binding.suikodenMapViewContainer.setVisibility(View.VISIBLE);
                binding.suikodenLoadingContainer.setVisibility(View.GONE);
                enableLoadingAnimation(false);
            }

            @Override
            public void onPreviewLoadError(Exception e) {
                Log.e(LOG_TAG, "onPreviewError(): Image preview load failed.");
            }

            @Override
            public void onImageLoadError(Exception e) {
                Log.e(LOG_TAG, "onImageLoadError(): Image load failed.");
            }

            @Override
            public void onTileLoadError(Exception e) {
                Log.e(LOG_TAG, "onTileLoadError(): Tile load failed.");
            }

            @Override
            public void onPreviewReleased() {
                Log.d(LOG_TAG, "onPreviewReleased(): Preview was released.");
            }
        });
        binding.suikodenMapView.setImage(ImageSource.resource(mapResource));
    }

    /** ANIMATION METHODS ______________________________________________________________________ **/

    private void enableLoadingAnimation(boolean isEnabled) {
        try {
            if (isEnabled) {
                loadingAnimation.start();
            } else {
                loadingAnimation.stop();
            }
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "enableLoadingAnimation(): ERROR: Loading animation failed to start.");
        }
    }

    /** CLICK LISTENER METHODS _________________________________________________________________ **/

    public boolean onSuikodenMapViewLongClicked() {
        HXSound.sound()
                .load(R.raw.gs_menu_scroll)
                .play(SCMapActivity.this);
        return true;
    }

    /** INTENT METHODS _________________________________________________________________________ **/

    private void launchTitleIntent() {
        Intent titleIntent = new Intent(this, SCMainActivity.class);
        startActivity(titleIntent);
        finish();
    }
}