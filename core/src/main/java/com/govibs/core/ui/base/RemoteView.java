package com.govibs.core.ui.base;

/**
 *
 * Created by Vibhor on 12/10/17.
 */

public interface RemoteView {

    void showProgress();

    void hideProgress();

    void showUnauthorizedError();

    void showEmpty();

    void showError(String errorMessage);

    void showMessageLayout(boolean show);

    void showSnackBar();

}
