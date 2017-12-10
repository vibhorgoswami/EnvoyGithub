package com.govibs.envoygithub.utils;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;

import com.govibs.core.ui.views.GridSpacingItemDecoration;
import com.govibs.core.ui.views.RecycleViewSpacesItemDecoration;

/**
 *
 * Created by Vibhor on 12/9/17.
 */

public final class UiUtils {

    @UiThread
    public static void addSpacingItemDividerDecorationToRecyclerView(@NonNull final RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new RecycleViewSpacesItemDecoration(AppUtils.isTablet(recyclerView.getContext().getApplicationContext()) ? 35 : 25));
    }


    @UiThread
    public static void addGridItemDividerDecorationToRecyclerView(@NonNull RecyclerView recyclerView, int spanCount, int spacing, boolean includeEdge) {
        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(spanCount, spacing, includeEdge);
        recyclerView.addItemDecoration(gridSpacingItemDecoration);
    }
}
