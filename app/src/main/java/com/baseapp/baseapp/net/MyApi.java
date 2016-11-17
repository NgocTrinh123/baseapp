package com.baseapp.baseapp.net;

import com.baseapp.baseapp.model.database.CurrentUser;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by tunglam on 11/8/16.
 */

public interface MyApi {

    @GET("api/v1/profiles")
    Observable<CurrentUser> getProfile();
}
