package com.ycorner.suikocartography.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.huhx0015.hxaudio.audio.HXMusic;
import com.huhx0015.hxaudio.audio.HXSound;
import com.huhx0015.hxaudio.utils.HXAudioPlayerUtils;
import com.ycorner.suikocartography.R;
import com.ycorner.suikocartography.utils.SCConstants;
import com.ycorner.suikocartography.utils.SCGameUtility;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;

public class SCMainActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // SYSTEM VARIABLES
    private boolean isTerminating;

    // VIEW INJECTION VARIABLES
    @BindView(R.id.sc_suikoden_1_maps_button) Button suikoden_1_maps_button;
    @BindView(R.id.sc_suikoden_1_background) ImageView suikoden_1_background;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        HXAudioPlayerUtils.enableSystemSound(true, this); // Re-enables the physical button's sound effects.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Releases all audio-related instances if the application is terminating.
        if (isTerminating) {
            HXMusic.clear();
            HXSound.clear();
        }
    }

    /** ACTIVITY OVERRIDE METHODS ______________________________________________________________ **/

    @Override
    public void onBackPressed() {
        HXSound.sound()
                .load(R.raw.gs_menu_cancel)
                .play(this);
        HXMusic.stop();
        isTerminating = true;
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
                HXSound.sound()
                        .load(R.raw.gs_menu_select)
                        .play(SCMainActivity.this);
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
        HXMusic.music()
                .load(R.raw.gs1_beginning_theme)
                .gapless(true)
                .play(this);

        List<Integer> soundEffectList = new ArrayList<>();
        soundEffectList.add(R.raw.gs_menu_select);
        soundEffectList.add(R.raw.gs_menu_cancel);
        soundEffectList.add(R.raw.gs_menu_scroll);
        HXSound.load(soundEffectList, this);
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
            HXMusic.stop();
            Intent mapsIntent = new Intent(this, SCMapActivity.class);
            mapsIntent.putExtra(SCConstants.EXTRA_GAME_NAME, extraGameIdentifier);
            startActivity(mapsIntent);
            finish();
        }
    }
}
