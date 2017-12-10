package com.govibs.core.ui.gist;

import android.support.annotation.NonNull;

import com.govibs.core.data.DataManager;
import com.govibs.core.data.model.GitPublicGist;
import com.govibs.core.data.network.RemoteCallback;
import com.govibs.core.ui.base.BasePresenter;

import java.util.List;

/**
 *
 * Created by Vibhor on 12/8/17.
 */

public class GistPresenter extends BasePresenter<GistContract.GistView> implements GistContract.ViewActions {

    private static final int ITEM_REQUEST_INITIAL_OFFSET = 0;
    private static final int ITEM_REQUEST_LIMIT = 6;

    private final DataManager mDataManager;

    public GistPresenter(@NonNull DataManager dataManager) {
        mDataManager = dataManager;
    }


    @Override
    public void onGistRequested() {
        getGitPublicList(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT);
    }

    @Override
    public void onListEndReached(Integer offset, Integer limit) {
        if (!isViewAttached()) {
            return;
        }
        // Show no more data to load.
        mView.hideProgress();
    }

    private void getGitPublicList(Integer offset, Integer limit) {
        if (!isViewAttached()) {
            return;
        }
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getPublicGists(new RemoteCallback<List<GitPublicGist>>() {
            @Override
            public void onSuccess(List<GitPublicGist> response) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                if (response.isEmpty()) {
                    mView.showEmpty();
                    return;
                }
                mView.showGistList(response);
            }

            @Override
            public void onUnauthorized() {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showUnauthorizedError();
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showError(throwable.getMessage());
            }
        });
    }
}
