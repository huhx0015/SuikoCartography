package com.ycorner.suikocartography;

import android.app.Activity;
import android.os.Bundle;
import com.huhx0015.hxgselib.audio.HXGSEMusicEngine;
import com.huhx0015.hxgselib.audio.HXGSESoundHandler;

import butterknife.ButterKnife;

public class SCMainActivity extends Activity {

    private HXGSEMusicEngine musicEngine;
    private HXGSESoundHandler soundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLayout();
        initAudio();
    }

    private void initLayout() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void initAudio() {
        musicEngine.getInstance().initializeAudio(this);
        soundHandler.getInstance().initializeAudio(this, 2);
    }
}
