package com.govibs.core.ui.gist.file;

import com.govibs.core.data.model.GitUserModel;
import com.govibs.core.ui.base.RemoteView;

/**
 * Created by Vibhor on 12/10/17.
 */

public interface GistDetailContract {

    interface ViewActions {

        void onRequestOwnerInfo(String url);
    }

    interface GistView extends RemoteView {

        void showFilesList(String infoToLoad);

        void showOwnerInformation(GitUserModel gitUserModel);
    }
}
