package com.ycorner.suikocartography;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.huhx0015.hxgselib.audio.HXGSEDolbyEffects;
import com.huhx0015.hxgselib.audio.HXGSEMusicEngine;
import com.huhx0015.hxgselib.audio.HXGSEPhysicalSound;
import com.huhx0015.hxgselib.audio.HXGSESoundHandler;
import com.ycorner.suikocartography.utility.SCConstants;
import com.ycorner.suikocartography.utility.SCGameUtility;
import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

public class SCMainActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // AUDIO VARIABLES
    private String currentSong = "SONG 1";

    // VIEW INJECTION VARIABLES
    @Bind(R.id.sc_suikoden_1_maps_button) Button suikoden_1_maps_button;
    @Bind(R.id.sc_suikoden_1_background) ImageView suikoden_1_background;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAudio();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Releases all audio-related instances if the application is terminating.
        HXGSEMusicEngine.getInstance().releaseMedia();
        HXGSESoundHandler.getInstance().releaseSound();
        HXGSEDolbyEffects.getInstance().releaseDolbyEffects();
    }

    /** ACTIVITY OVERRIDE METHODS ______________________________________________________________ **/

    @Override
    public void onBackPressed() {
        HXGSESoundHandler.getInstance().playSoundFx("MENU_CANCEL", 0);
        HXGSEMusicEngine.getInstance().stopSong();
        finish();
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initButtons();
        initBackground();
    }

    private void initButtons() {

        suikoden_1_maps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HXGSESoundHandler.getInstance().playSoundFx("MENU_SELECT", 0);
                launchMapsIntent(SCGameUtility.SCGameID.GENSO_SUIKODEN_1);
            }
        });
    }

    private void initBackground() {
        Picasso.with(this)
                .load(R.drawable.gs1_mural)
                .into(suikoden_1_background);
    }

    private void initAudio() {
        HXGSEMusicEngine.getInstance().initializeAudio(getApplicationContext()); // Initializes the HXGSEMusic class object.
        HXGSESoundHandler.getInstance().initializeAudio(getApplicationContext(), 2); // Initializes the HXGSESound class object.
        HXGSEDolbyEffects.getInstance().initializeDolby(getApplicationContext()); // Initializes the HXGSEDolby class object.
    }

    private void launchMapsIntent(SCGameUtility.SCGameID id) {

        String extraGameIdentifier = "";

        switch (id) {
            case GENSO_SUIKODEN_1:
                extraGameIdentifier = SCConstants.GENSO_SUIKODEN_1_ID;
                break;
            case GENSO_SUIKODEN_2:
                extraGameIdentifier = SCConstants.GENSO_SUIKODEN_2_ID;
                break;
            case GENSO_SUIKODEN_3:
                extraGameIdentifier = SCConstants.GENSO_SUIKODEN_3_ID;
                break;
            case GENSO_SUIKODEN_4:
                extraGameIdentifier = SCConstants.GENSO_SUIKODEN_4_ID;
                break;
            case GENSO_SUIKODEN_5:
                extraGameIdentifier = SCConstants.GENSO_SUIKODEN_5_ID;
                break;
        }

        if (!extraGameIdentifier.isEmpty()) {
            Intent mapsIntent = new Intent(this, SCMapActivity.class);
            mapsIntent.putExtra(SCConstants.EXTRA_GAME_NAME, extraGameIdentifier);
            startActivity(mapsIntent);
        }
    }
}
