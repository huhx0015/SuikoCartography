package com.ycorner.suikocartography;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class SCMapActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // AUDIO VARIABLES
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
        HXGSEMusicEngine.getInstance().playSongName(currentSong, true);
        HXGSEPhysicalSound.disablePhysSounds(true, this); // Temporarily disables the physical button's sound effects.
    }

    @Override
    protected void onPause() {
        super.onPause();
        HXGSEMusicEngine.getInstance().pauseSong(); // Pauses any song that is playing in the background.
        HXGSEPhysicalSound.disablePhysSounds(false, this); // Re-enables the physical button's sound effects.
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

        // Defines the long click listener for the map view.
        worldMapView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                HXGSESoundHandler.getInstance().playSoundFx("MENU_SCROLL", 0);
                return false;
            }
        });
    }
}