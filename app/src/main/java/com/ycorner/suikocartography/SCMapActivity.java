package com.ycorner.suikocartography;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.huhx0015.hxgselib.audio.HXGSEMusicEngine;
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

    // VIEW INJECTION VARIABLES
    @Bind(R.id.touch_image_view_sample_image) TouchImageView worldMapView;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        musicEngine.getInstance().playSongName("SONG 2", true); // TODO: Sample Suikoden Map song.
    }

    @Override
    protected void onPause() {
        super.onPause();

        musicEngine.getInstance().pauseSong();
    }

    /** INITIALIZATION METHODS _________________________________________________________________ **/

    private void initView() {
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        Bundle mapBundle = getIntent().getExtras();
        if (mapBundle != null) {

        }

        initMapImage();
    }

    private void initMapImage() {
        final InputStream inputStream = getResources().openRawResource(R.raw.gs1_world_map);
        final Drawable loadingResource = getResources().getDrawable(R.drawable.android_placeholder);
        TileBitmapDrawable.attachTileBitmapDrawable(worldMapView, inputStream, loadingResource, null);

        worldMapView.setMaxScale(8); // Sets the maximum zoom in value for the map.
    }
}