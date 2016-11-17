package com.baseapp.baseapp.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baseapp.baseapp.receiver.NetworkChangeReceiver;


/**
 * Created by cuong on 8/16/16.
 */

public class AutoSyncService extends Service {
    private NetworkChangeReceiver networkChangeReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        networkChangeReceiver = new NetworkChangeReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        Log.d("auto service", "onStartCommand: ");
        return START_STICKY;
    }
}
