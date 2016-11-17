package com.baseapp.baseapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.baseapp.baseapp.MyApplication;
import com.baseapp.baseapp.net.MyApi;
import com.baseapp.baseapp.utils.Log;
import com.baseapp.baseapp.utils.NetworkUtils;

import javax.inject.Inject;

import io.realm.Realm;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Inject
    Realm realm;

    @Inject
    MyApi myApi;
    private static final String TAG = "AutoSync service";

    public NetworkChangeReceiver() {
        super();
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        boolean isOnline = NetworkUtils.availableWifiOr3G(context);
        if (isOnline) {
            Log.d("sync data");
            // check internet send data offline
        }
    }

}