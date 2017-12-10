package com.govibs.core.ui.gist.file;

import com.govibs.core.data.DataManager;
import com.govibs.core.ui.base.BasePresenter;

/**
 * Created by Vibhor on 12/10/17.
 */

public class GistDetailPresenter extends BasePresenter<GistDetailContract.GistView>
        implements GistDetailContract.ViewActions {

    private static final int ITEM_REQUEST_INITIAL_OFFSET = 0;
    private static final int ITEM_REQUEST_LIMIT = 6;

    private final DataManager mDataManager;

    public GistDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onGistFileRequested(String userId) {

    }
}
