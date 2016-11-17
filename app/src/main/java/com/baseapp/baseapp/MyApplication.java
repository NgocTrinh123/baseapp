package com.baseapp.baseapp;

import android.app.Application;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.baseapp.baseapp.di.component.AppComponent;
import com.baseapp.baseapp.di.component.DaggerAppComponent;
import com.baseapp.baseapp.di.module.AppModule;
import com.baseapp.baseapp.di.module.CommonModule;
import com.baseapp.baseapp.service.AutoSyncService;
import com.baseapp.baseapp.utils.Log;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

/**
 * Created by tunglam on 11/9/16.
 */

public class MyApplication extends Application {
    private static MyApplication instance = null;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        MultiDex.install(this);
        startAutoSyncService();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .commonModule(new CommonModule())
                .build();

        configDBBrowser();
        Log.d("GG API KEY: " + getString(R.string.google_map_key));
    }

    private void configDBBrowser() {
        if (!BuildConfig.isDebug) {
            return;
        }

        RealmInspectorModulesProvider provider = RealmInspectorModulesProvider.builder(this)
                .withMetaTables()
                .withDescendingOrder()
                .build();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(provider)
                        .build());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void startAutoSyncService() {
        Intent intent = new Intent(this, AutoSyncService.class);
        startService(intent);
    }
}
