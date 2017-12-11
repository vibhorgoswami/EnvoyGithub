package com.govibs.envoygithub.ui.gist;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 *
 * Created by Vibhor on 12/10/17.
 */

public abstract class EndlessRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private static final int STARTING_PAGE_INDEX = 0;

    private static int sVisibleThreshold = 2;
    private int mCurrentPage = 0;
    private int mPreviousTotalItemCount = 0;
    private boolean mLoading = true;
    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewOnScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewOnScrollListener(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        sVisibleThreshold = sVisibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewOnScrollListener(StaggeredGridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        sVisibleThreshold = sVisibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager)
                    .findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);

        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager)
                    .findLastVisibleItemPosition();

        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }


        if (totalItemCount < mPreviousTotalItemCount) { // List was cleared
            mCurrentPage = STARTING_PAGE_INDEX;
            mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                mLoading = true;
            }
        }

        if (mLoading && (totalItemCount > mPreviousTotalItemCount + 1)) {
            mLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }

        if (!mLoading && (lastVisibleItemPosition + sVisibleThreshold) > totalItemCount) {
            mCurrentPage++;
            onLoadMore(mCurrentPage, totalItemCount);
            mLoading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount);
}
