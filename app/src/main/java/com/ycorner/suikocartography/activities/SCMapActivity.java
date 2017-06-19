package com.ycorner.suikocartography.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.huhx0015.hxaudio.audio.HXMusic;
import com.huhx0015.hxaudio.audio.HXSound;
import com.huhx0015.hxaudio.utils.HXAudioPlayerUtils;
import com.ycorner.suikocartography.R;
import com.ycorner.suikocartography.constants.SCConstants;
import java.io.InputStream;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * Created by huhx0015 on 12/17/15.
 */
public class SCMapActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // GAME VARIABLES
    private String suikodenGameId;

    // VIEW INJECTION VARIABLES
    @BindView(R.id.suikoden_map_view) TouchImageView worldMapView;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            suikodenGameId = savedInstanceState.getString(SCConstants.EXTRA_GAME_NAME);
        } else {
            suikodenGameId = getIntent().getExtras().getString(SCConstants.EXTRA_GAME_NAME);
        }

        initAudio();
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
        outState.putString(SCConstants.EXTRA_GAME_NAME, suikodenGameId);
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initView() {
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initMapImage();
    }

    private void initAudio() {
        HXMusic.music()
                .load(R.raw.gs1_tiny_characters_in_a_huge_world)
                .looped(true)
                .gapless(true)
                .play(this);
    }

    private void initMapImage() {
        InputStream inputStream;
        Drawable loadingResource;

        switch (suikodenGameId) {
            case SCConstants.GENSO_SUIKODEN_1_ID:
                inputStream = getResources().openRawResource(R.raw.gs1_world_map);
                loadingResource = getResources().getDrawable(R.drawable.gs1_loading);
                break;
            case SCConstants.GENSO_SUIKODEN_2_ID:
                inputStream = getResources().openRawResource(R.raw.gs2_world_map);
                loadingResource = getResources().getDrawable(R.drawable.gs1_loading);
                break;
            default:
                inputStream = getResources().openRawResource(R.raw.gs1_world_map);
                loadingResource = getResources().getDrawable(R.drawable.gs1_loading);
        }

        TileBitmapDrawable.attachTileBitmapDrawable(worldMapView, inputStream, loadingResource, null);
        worldMapView.setMaxScale(8); // Sets the maximum zoom in value for the map.
    }

    /** CLICK LISTENER METHODS _________________________________________________________________ **/

    @OnLongClick(R.id.suikoden_map_view)
    public void onSuikodenMapViewLongClicked() {
        HXSound.sound()
                .load(R.raw.gs_menu_scroll)
                .play(SCMapActivity.this);
    }

    /** INTENT METHODS _________________________________________________________________________ **/

    private void launchTitleIntent() {
        Intent titleIntent = new Intent(this, SCMainActivity.class);
        startActivity(titleIntent);
        finish();
    }
}