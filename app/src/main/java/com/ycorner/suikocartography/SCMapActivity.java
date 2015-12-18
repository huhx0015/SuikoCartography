package com.ycorner.suikocartography;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.huhx0015.hxgselib.audio.HXGSEMusicEngine;
import com.huhx0015.hxgselib.audio.HXGSESoundHandler;

import java.io.InputStream;

import butterknife.ButterKnife;

/**
 * Created by huhx0015 on 12/17/15.
 */
public class SCMapActivity extends Activity {

    private HXGSEMusicEngine musicEngine;
    private HXGSESoundHandler soundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout();
        initMapImage();
    }

    private void initLayout() {
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
    }

    private void initMapImage() {
        final TouchImageView image = (TouchImageView) findViewById(R.id.touch_image_view_sample_image);
        final InputStream is = getResources().openRawResource(R.raw.gs1_world_map);
        final Drawable placeHolder = getResources().getDrawable(R.drawable.android_placeholder);
        TileBitmapDrawable.attachTileBitmapDrawable(image, is, placeHolder, null);
    }
}