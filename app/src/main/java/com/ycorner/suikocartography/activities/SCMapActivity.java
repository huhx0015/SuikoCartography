package com.ycorner.suikocartography.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.huhx0015.hxaudio.audio.HXMusic;
import com.huhx0015.hxaudio.audio.HXSound;
import com.huhx0015.hxaudio.utils.HXAudioPlayerUtils;
import com.squareup.picasso.Picasso;
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
    private String gameId;

    // VIEW INJECTION VARIABLES
    @BindView(R.id.suikoden_map_background) AppCompatImageView mapBackgroundImage;
    @BindView(R.id.suikoden_map_title) AppCompatTextView mapTitleText;
    @BindView(R.id.suikoden_map_view) TouchImageView mapView;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            gameId = savedInstanceState.getString(SCConstants.EXTRA_GAME_NAME);
        } else {
            gameId = getIntent().getExtras().getString(SCConstants.EXTRA_GAME_NAME);
        }

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
        String mapTitle;
        int mapBackgroundResource;
        int musicResource;
        InputStream inputStream;
        Drawable loadingResource;

        switch (gameId) {
            case SCConstants.GENSO_SUIKODEN_1_ID:
                mapTitle = getString(R.string.suikoden_1_map_name);
                mapBackgroundResource = R.drawable.gs1_map_background;
                musicResource = R.raw.gs1_tiny_characters_in_a_huge_world;
                inputStream = getResources().openRawResource(R.raw.gs1_world_map);
                loadingResource = ContextCompat.getDrawable(this, R.drawable.gs1_loading);
                break;
            case SCConstants.GENSO_SUIKODEN_2_ID:
                mapTitle = getString(R.string.suikoden_2_map_name);
                mapBackgroundResource = R.drawable.gs2_map_background;
                musicResource = R.raw.gs1_tiny_characters_in_a_huge_world;
                inputStream = getResources().openRawResource(R.raw.gs2_world_map);
                loadingResource = ContextCompat.getDrawable(this, R.drawable.gs1_loading);
                break;
            default:
                mapTitle = getString(R.string.suikoden_1_map_name);
                mapBackgroundResource = R.drawable.gs1_map_background;
                musicResource = R.raw.gs1_tiny_characters_in_a_huge_world;
                inputStream = getResources().openRawResource(R.raw.gs1_world_map);
                loadingResource = ContextCompat.getDrawable(this, R.drawable.gs1_loading);
        }

        // MAP TITLE:
        mapTitleText.setText(mapTitle);

        // MAP BACKGROUND:
        Picasso.with(this)
                .load(mapBackgroundResource)
                .config(Bitmap.Config.RGB_565)
                .centerCrop()
                .into(mapBackgroundImage);

        // MUSIC:
        HXMusic.music()
                .load(musicResource)
                .looped(true)
                .gapless(true)
                .play(this);

        // TOUCH MAP VIEW:
        TileBitmapDrawable.attachTileBitmapDrawable(mapView, inputStream, loadingResource, null);
        mapView.setMaxScale(8); // Sets the maximum zoom in value for the map.
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