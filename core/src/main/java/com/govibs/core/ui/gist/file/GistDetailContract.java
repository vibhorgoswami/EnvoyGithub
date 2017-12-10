package com.govibs.core.ui.gist.file;

import com.govibs.core.data.model.GitFiles;
import com.govibs.core.ui.base.RemoteView;

import java.util.List;

/**
 * Created by Vibhor on 12/10/17.
 */

public interface GistDetailContract {

    interface ViewActions {

        void onGistFileRequested(String userId);
    }

    interface GistView extends RemoteView {

        void showFilesList(List<GitFiles> gitFilesList);
    }
}
