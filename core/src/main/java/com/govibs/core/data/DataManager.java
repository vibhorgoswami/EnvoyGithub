package com.govibs.core.data;

import android.support.annotation.NonNull;

import com.govibs.core.data.model.GitPublicGist;
import com.govibs.core.data.model.GitUserModel;
import com.govibs.core.data.network.GistService;
import com.govibs.core.data.network.GitServiceFactory;
import com.govibs.core.data.network.RemoteCallback;

import java.util.List;

/**
 *
 * Created by Vibhor on 12/10/17.
 */

public class DataManager {

    private static DataManager mInstance;

    private final GistService mGistService;

    private DataManager() {
        mGistService = GitServiceFactory.makeGistService();
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }
        return mInstance;
    }

    public void getPublicGists(RemoteCallback<List<GitPublicGist>> listener) {
        mGistService.getPublicGist().enqueue(listener);
    }

    public void getOwnerInformation(@NonNull String userId, RemoteCallback<GitUserModel> listener) {
        mGistService.getOwnerInformation(userId).enqueue(listener);
    }

}
