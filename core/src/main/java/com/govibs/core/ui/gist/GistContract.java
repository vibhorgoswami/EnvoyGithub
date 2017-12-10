package com.govibs.core.ui.gist;

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

        void onListEndReached(Integer offset, Integer limit);

    }

    interface GistView extends RemoteView {

        void showGistList(List<GitPublicGist> gitPublicGists);

    }
}
