package com.ycorner.suikocartography.utils;

import com.ycorner.suikocartography.constants.SCConstants;

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
}
