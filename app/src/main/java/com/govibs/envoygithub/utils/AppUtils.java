package com.govibs.envoygithub.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.govibs.envoygithub.R;

/**
 *
 * Created by Vibhor on 12/10/17.
 */

public final class AppUtils {

    public static boolean isTablet(@NonNull Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    public static Intent getIntentForView() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent = new Intent(Intent.ACTION_QUICK_VIEW);
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
        }
        return intent;
    }

}
