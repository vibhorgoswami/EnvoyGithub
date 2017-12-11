package com.govibs.core.data.network;

import com.govibs.core.data.model.GitPublicGist;
import com.govibs.core.data.model.GitUserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 *
 * Created by Vibhor on 12/10/17.
 */

public interface GistService {

    @GET("gists/public")
    Call<List<GitPublicGist>> getPublicGist();

    @GET("users/{userId}")
    Call<GitUserModel> getOwnerInformation(@Path("userId") String userId);

}
