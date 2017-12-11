package com.govibs.core.data.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 *
 * Created by Vibhor on 12/10/17.
 */

public class GitServiceFactory {

    private static final String BASE_URL = "https://api.github.com";
    private static final int HTTP_READ_TIMEOUT = 10000;
    private static final int HTTP_CONNECT_TIMEOUT = 6000;

    public static GistService makeGistService() {
        return makeMarvelService(makeOkHttpClient());
    }

    private static GistService makeMarvelService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(GistService.class);
    }

    private static OkHttpClient makeOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        //httpClientBuilder.addInterceptor(makeLoggingInterceptor());
        return httpClientBuilder.build();
    }

    /*private static HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }*/
}
