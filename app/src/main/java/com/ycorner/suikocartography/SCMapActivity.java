package com.ycorner.suikocartography;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.huhx0015.hxgselib.audio.HXGSEMusicEngine;
import com.huhx0015.hxgselib.audio.HXGSEPhysicalSound;
import com.huhx0015.hxgselib.audio.HXGSESoundHandler;
import java.io.InputStream;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by huhx0015 on 12/17/15.
 */
public class SCMapActivity extends Activity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // AUDIO VARIABLES
    private HXGSEMusicEngine musicEngine;
    private HXGSESoundHandler soundHandler;
    private boolean isPaused = false;
    private String currentSong = "SONG 2";

    // VIEW INJECTION VARIABLES
    @Bind(R.id.suikoden_map_view) TouchImageView worldMapView;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicEngine.getInstance().playSongName(currentSong, true);
        isPaused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        HXGSEMusicEngine.getInstance().pauseSong(); // Pauses any song that is playing in the background.
        HXGSEPhysicalSound.disablePhysSounds(false, this); // Re-enables the physical button's sound effects.
        isPaused = true;
    }

    /** ACTIVITY OVERRIDE METHODS ______________________________________________________________ **/

    @Override
    public void onBackPressed() {
        HXGSESoundHandler.getInstance().playSoundFx("MENU_CANCEL", 0);
        HXGSEMusicEngine.getInstance().stopSong();
        finish();
    }

    /** INITIALIZATION METHODS _________________________________________________________________ **/

    private void initView() {
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        initMapImage();
    }

    private void initMapImage() {
        final InputStream inputStream = getResources().openRawResource(R.raw.gs1_world_map);
        final Drawable loadingResource = getResources().getDrawable(R.drawable.gs1_loading);
        TileBitmapDrawable.attachTileBitmapDrawable(worldMapView, inputStream, loadingResource, null);

        worldMapView.setMaxScale(8); // Sets the maximum zoom in value for the map.

        worldMapView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                soundHandler.getInstance().playSoundFx("MENU_SCROLL", 0);
                return false;
            }
        });
    }
}