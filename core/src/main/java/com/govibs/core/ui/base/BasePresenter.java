package com.govibs.core.ui.base;

import android.support.annotation.NonNull;

/**
 *
 * Created by Vibhor on 12/7/17.
 */

public abstract class BasePresenter<V> {

    protected V mView;

    public final void attachView(@NonNull V view) {
        mView = view;
    }

    public final void detachView() {
        mView = null;
    }

    protected final boolean isViewAttached() {
        return mView != null;
    }
}
