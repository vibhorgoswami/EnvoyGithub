package com.govibs.core.ui.gist.file;

import com.govibs.core.data.DataManager;
import com.govibs.core.data.model.GitUserModel;
import com.govibs.core.data.network.RemoteCallback;
import com.govibs.core.ui.base.BasePresenter;

/**
 *
 * Created by Vibhor on 12/10/17.
 */

public class GistDetailPresenter extends BasePresenter<GistDetailContract.GistView>
        implements GistDetailContract.ViewActions {

    private final DataManager mDataManager;

    public GistDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onRequestOwnerInfo(String url) {
        if (!isViewAttached()) return;
        mView.showProgress();
        mDataManager.getOwnerInformation(url, new RemoteCallback<GitUserModel>() {
            @Override
            public void onSuccess(GitUserModel response) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showOwnerInformation(response);
            }

            @Override
            public void onUnauthorized() {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showSnackBar();
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showSnackBar();
            }
        });
    }
}
