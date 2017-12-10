package com.govibs.core.ui.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Item Decoration
 * Created by Vibhor on 11/10/17.
 */

public class RecycleViewSpacesItemDecoration extends RecyclerView.ItemDecoration {

    public final int mSpace;

    public RecycleViewSpacesItemDecoration(int mSpace) {
        this.mSpace = mSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.bottom = mSpace;
        outRect.right = mSpace;

        // Add top margin only for first item of views to avoid double space between items.
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace;
        }
    }
}
