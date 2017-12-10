package com.govibs.core.ui.gist;

import com.govibs.core.data.model.GitFiles;
import com.govibs.core.data.model.GitPublicGist;
import com.govibs.core.ui.base.RemoteView;

import java.util.List;

/**
 *
 * Created by Vibhor on 12/7/17.
 */

public interface GistContract {

    interface ViewActions {

        void onGistRequested();

        void onGistFileRequested(String userId);

        void onListEndReached(Integer offset, Integer limit);

    }

    interface GistView extends RemoteView {

        void showGist(GitPublicGist gitPublicGist);

        void showGistList(List<GitPublicGist> gitPublicGists);

        void showFilesList(List<GitFiles> gitFilesList);

    }
}
