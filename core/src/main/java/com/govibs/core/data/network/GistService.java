package com.govibs.core.data.network;

import com.govibs.core.data.model.GitFiles;
import com.govibs.core.data.model.GitPublicGist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 *
 * Created by Vibhor on 12/8/17.
 */

public interface GistService {

    @GET("gists/public")
    Call<List<GitPublicGist>> getPublicGist();

}
