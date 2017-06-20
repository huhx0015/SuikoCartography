package com.ycorner.suikocartography.utils;

import com.ycorner.suikocartography.R;
import com.ycorner.suikocartography.constants.SCConstants;
import java.util.Random;

/**
 * Created by huhx0015 on 12/21/15.
 */
public class SCGameUtility {

    /** ENUM ___________________________________________________________________________________ **/

    public enum SCGameID {
        GENSO_SUIKODEN_1,
        GENSO_SUIKODEN_2,
        GENSO_SUIKODEN_3,
        GENSO_SUIKODEN_4,
        GENSO_SUIKODEN_5
    }

    /** UTILITY METHODS ________________________________________________________________________ **/

    public static String getGameIdentity(SCGameID id) {
        switch (id) {
            case GENSO_SUIKODEN_1:
                return SCConstants.GENSO_SUIKODEN_1_ID;
            case GENSO_SUIKODEN_2:
                return SCConstants.GENSO_SUIKODEN_2_ID;
            case GENSO_SUIKODEN_3:
                return SCConstants.GENSO_SUIKODEN_3_ID;
            case GENSO_SUIKODEN_4:
                return SCConstants.GENSO_SUIKODEN_4_ID;
            case GENSO_SUIKODEN_5:
                return SCConstants.GENSO_SUIKODEN_5_ID;
            default:
                return SCConstants.GENSO_SUIKODEN_1_ID;
        }
    }

    public static int getRandomMusic(String gameId) {
        int NUM_SONGS = 2;
        Random randValue = new Random();
        int randInt = randValue.nextInt(NUM_SONGS);

        switch (gameId) {
            case SCConstants.GENSO_SUIKODEN_1_ID:
                switch (randInt) {
                    case 0:
                        return R.raw.gs1_tiny_characters_on_the_great_plains;
                    case 1:
                        return R.raw.gs1_blue_sea_blue_sky;
                    default:
                        return R.raw.gs1_tiny_characters_on_the_great_plains;
                }
            case SCConstants.GENSO_SUIKODEN_2_ID:
                switch (randInt) {
                    case 0:
                        return R.raw.gs2_adventurous_journey;
                    case 1:
                        return R.raw.gs2_even_farther;
                    default:
                        return R.raw.gs2_adventurous_journey;
                }
            default:
                switch (randInt) {
                    case 0:
                        return R.raw.gs1_tiny_characters_on_the_great_plains;
                    case 1:
                        return R.raw.gs1_blue_sea_blue_sky;
                    default:
                        return R.raw.gs1_tiny_characters_on_the_great_plains;
                }
        }
    }
}
