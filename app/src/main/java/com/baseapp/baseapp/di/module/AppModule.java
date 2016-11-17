package com.baseapp.baseapp.di.module;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.baseapp.baseapp.BuildConfig;
import com.baseapp.baseapp.MyApplication;
import com.baseapp.baseapp.di.scope.AppScope;
import com.baseapp.baseapp.model.database.CurrentUser;
import com.baseapp.baseapp.net.MyApi;
import com.baseapp.baseapp.utils.CacheUtils;
import com.baseapp.baseapp.utils.ToStringConverterFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mb on 7/27/16.
 */
@Module
@AppScope
public class AppModule {

    public AppModule() {
    }

    @Provides
    @AppScope
    public MyApi provideApi(OkHttpClient okHttpClient) {
        synchronized (MyApi.class) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(new ToStringConverterFactory())
                    .client(okHttpClient)
                    .build();
            return retrofit.create(MyApi.class);
        }
    }

    @Provides
    @AppScope
    protected OkHttpClient provideOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!TextUtils.isEmpty(CacheUtils.getAuthToken())) {
                    Request newRequest = request.newBuilder().addHeader("Http-Auth-Token", CacheUtils.getAuthToken()).build();
                    return chain.proceed(newRequest);
                }
                return chain.proceed(request);
            }
        });
        httpClient.writeTimeout(15 * 60 * 1000, TimeUnit.MILLISECONDS);
        httpClient.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        httpClient.connectTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        return httpClient.build();
    }

    @Provides
    @AppScope
    protected Application provideApplication() {
        return MyApplication.getInstance();
    }

    @Provides
    public Realm provideRealm(RealmConfiguration realmConfiguration) {
        Realm.setDefaultConfiguration(realmConfiguration);
        return Realm.getDefaultInstance();
    }

    @Provides
    @AppScope
    protected RealmConfiguration provideRealmConfig(Application application, RealmMigration realmMigration) {
        return new RealmConfiguration
                .Builder(application)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .migration(realmMigration)
                .build();
    }

    @Provides
    @AppScope
    protected RealmMigration provideRealmMigration() {
        return new com.baseapp.baseapp.model.database.RealmMigration();
    }

    @Provides
    @AppScope
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }


    @Provides
    public CurrentUser provideUser(Realm realm) {
        realm.beginTransaction();
        CurrentUser profile = realm.where(CurrentUser.class).findFirst();
        realm.commitTransaction();
        if (profile == null) {
            profile = CacheUtils.getUser();
            if (profile == null) {
                return new CurrentUser();
            } else {
                return profile;
            }
        }
        return realm.copyFromRealm(profile);
    }

    @Provides
    Context provideContext() {
        return MyApplication.getInstance();
    }

}
