package com.ycorner.suikocartography;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.huhx0015.hxgselib.audio.HXGSEMusicEngine;
import com.huhx0015.hxgselib.audio.HXGSESoundHandler;
import com.ycorner.suikocartography.utility.SCConstants;
import com.ycorner.suikocartography.utility.SCGameUtility;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SCMainActivity extends Activity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // AUDIO VARIABLES
    private HXGSEMusicEngine musicEngine;
    private HXGSESoundHandler soundHandler;

    // VIEW INJECTION VARIABLES
    @Bind(R.id.sc_suikoden_1_maps_button) Button suikoden_1_maps_button;

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

        musicEngine.getInstance().playSongName("SONG 1", true); // TODO: Sample Suikoden Menu song.
    }

    @Override
    protected void onPause() {
        super.onPause();

        musicEngine.getInstance().pauseSong();
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initButtons();
    }

    private void initButtons() {

        suikoden_1_maps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMapsIntent(SCGameUtility.SCGameID.GENSO_SUIKODEN_1);
            }
        });
    }

    private void initAudio() {
        musicEngine.getInstance().initializeAudio(this);
        soundHandler.getInstance().initializeAudio(this, 2);
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
