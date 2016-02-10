package com.huhx0015.hxgselib.resources;

import android.util.Log;
import com.huhx0015.hxgselib.R;
import com.huhx0015.hxgselib.model.HXGSESong;
import java.util.LinkedList;

/** -----------------------------------------------------------------------------------------------
 *  [HXGSEMusicList] CLASS
 *  PROGRAMMER: Michael Yoon Huh (Huh X0015)
 *  DESCRIPTION: This class is responsible for providing methods to return the list of songs
 *  available for playback in this application.
 *  -----------------------------------------------------------------------------------------------
 */

public class HXGSEMusicList {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private static final String TAG = HXGSEMusicList.class.getSimpleName(); // Used for logging output to logcat.

    /** CLASS FUNCTIONALITY ____________________________________________________________________ **/

    // hxgseMusicList(): Creates and returns an LinkedList of HXGSESong objects.
    public static LinkedList<HXGSESong> hxgseMusicList() {

        LinkedList<HXGSESong> musicList = new LinkedList<>(); // Creates a new LinkedList<HXGSESong> object.

        // Adds the HXGSESong objects to the LinkedList.
        // TODO: Define your song name and resources here.
        musicList.add(new HXGSESong("SONG 1", R.raw.gs1_beginning_theme));
        musicList.add(new HXGSESong("SONG 2", R.raw.gs1_tiny_characters_in_a_huge_world));
        musicList.add(new HXGSESong("SONG 3", R.raw.gs2_name_entry));

        Log.d(TAG, "INITIALIZATION: List of songs has been constructed successfully.");

        return musicList;
    }
}
