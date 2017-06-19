package com.ycorner.suikocartography.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import com.huhx0015.hxaudio.audio.HXMusic;
import com.huhx0015.hxaudio.audio.HXSound;
import com.huhx0015.hxaudio.utils.HXAudioPlayerUtils;
import com.squareup.picasso.Picasso;
import com.ycorner.suikocartography.R;
import com.ycorner.suikocartography.constants.SCConstants;
import com.ycorner.suikocartography.utils.SCGameUtility;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SCMainActivity extends AppCompatActivity {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // SYSTEM VARIABLES
    private boolean isTerminating;

    // VIEW INJECTION VARIABLES
    @BindView(R.id.main_suikoden_1_background) AppCompatImageView suikoden1Background;
    @BindView(R.id.main_suikoden_2_background) AppCompatImageView suikoden2Background;

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

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBackground();
    }

    private void initBackground() {

        // SUIKODEN I:
        Picasso.with(this)
                .load(R.drawable.gs1_mural)
                .config(Bitmap.Config.RGB_565)
                .into(suikoden1Background);

        // SUIKODEN II:
        Picasso.with(this)
                .load(R.drawable.gs1_mural)
                .config(Bitmap.Config.RGB_565)
                .into(suikoden2Background);
    }

    private void initAudio() {
        HXMusic.music()
                .load(R.raw.gs1_beginning_theme)
                .looped(true)
                .gapless(true)
                .play(this);

        List<Integer> soundEffectList = new ArrayList<>();
        soundEffectList.add(R.raw.gs_menu_select);
        soundEffectList.add(R.raw.gs_menu_cancel);
        soundEffectList.add(R.raw.gs_menu_scroll);
        HXSound.load(soundEffectList, this);
    }

    /** CLICK LISTENER METHODS _________________________________________________________________ **/

    @OnClick(R.id.main_suikoden_1_start_button)
    public void onSuikoden1StartButtonClicked() {
        HXSound.sound()
                .load(R.raw.gs_menu_select)
                .play(SCMainActivity.this);
        launchMapsIntent(SCGameUtility.SCGameID.GENSO_SUIKODEN_1);
    }

    @OnClick(R.id.main_suikoden_2_start_button)
    public void onSuikoden2StartButtonClicked() {
        HXSound.sound()
                .load(R.raw.gs_menu_select)
                .play(SCMainActivity.this);
        launchMapsIntent(SCGameUtility.SCGameID.GENSO_SUIKODEN_2);
    }

    /** INTENT METHODS _________________________________________________________________________ **/

    private void launchMapsIntent(SCGameUtility.SCGameID id) {
        String extraGameIdentifier = SCGameUtility.getGameIdentity(id);

        if (!extraGameIdentifier.isEmpty()) {
            HXMusic.stop();
            Intent mapsIntent = new Intent(this, SCMapActivity.class);
            mapsIntent.putExtra(SCConstants.EXTRA_GAME_NAME, extraGameIdentifier);
            startActivity(mapsIntent);
            finish();
        }
    }
}
