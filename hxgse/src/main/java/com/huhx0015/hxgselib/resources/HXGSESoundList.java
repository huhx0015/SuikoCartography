package com.huhx0015.hxgselib.resources;

import android.util.Log;
import com.huhx0015.hxgselib.R;
import com.huhx0015.hxgselib.model.HXGSESoundFX;
import java.util.LinkedList;

/** -----------------------------------------------------------------------------------------------
 *  [HXGSESoundList] CLASS
 *  PROGRAMMER: Michael Yoon Huh (Huh X0015)
 *  DESCRIPTION: This class is responsible for providing methods to return the list of sound effects
 *  available for playback in this application.
 *  -----------------------------------------------------------------------------------------------
 */

public class HXGSESoundList {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private static final String TAG = HXGSESoundList.class.getSimpleName(); // Used for logging output to logcat.

    /** CLASS FUNCTIONALITY ____________________________________________________________________ **/

    // hxgseSoundList(): Creates and returns an LinkedList of sound effects.
    public static LinkedList<HXGSESoundFX> hxgseSoundList() {

        LinkedList<HXGSESoundFX> soundList = new LinkedList<>(); // Creates a new LinkedList<Integer> object.

        // Adds the HXGSESoundFX objects to the LinkedList.
        // TODO: Define your sound effects and associated resources here.
        soundList.add(new HXGSESoundFX("MENU_SELECT", R.raw.gs_menu_select));
        soundList.add(new HXGSESoundFX("MENU_SCROLL", R.raw.gs_menu_scroll));
        soundList.add(new HXGSESoundFX("MENU_CANCEL", R.raw.gs_menu_cancel));
        soundList.add(new HXGSESoundFX("RECRUITMENT", R.raw.gs_recruitment));

        Log.d(TAG, "INITIALIZATION: List of sound effects has been constructed successfully.");

        return soundList;
    }
}
