package com.govibs.envoygithub.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.govibs.envoygithub.R;

/**
 *
 * Created by Vibhor on 12/9/17.
 */

public final class AppUtils {

    public static boolean isTablet(@NonNull Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

}
