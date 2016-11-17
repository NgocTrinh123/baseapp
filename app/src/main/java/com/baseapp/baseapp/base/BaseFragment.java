package com.baseapp.baseapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.baseapp.baseapp.MyApplication;
import com.baseapp.baseapp.interfaces.ActionCallback;
import com.baseapp.baseapp.interfaces.RequestPermissionCallback;
import com.baseapp.baseapp.model.database.CurrentUser;
import com.baseapp.baseapp.net.MyApi;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by tunglam on 11/10/16.
 */

public class BaseFragment extends Fragment {
    private BaseActivity activity;

    @Inject
    MyApi api;

    @Inject
    EventBus eventBus;

    @Inject
    Realm realm;

    @Inject
    CurrentUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getRealm() != null) {
            getRealm().close();
        }
    }

    public MyApi getApi() {
        return api;
    }

    public EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = EventBus.getDefault();
        }
        return eventBus;
    }

    public Realm getRealm() {
        return realm;
    }


    public CurrentUser getUser() {
        return user;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    public <T> void requestAPI(Observable<T> observable, ResponseListener<T> responseListener) {
        if (observable == null) {
            return;
        }

        if (getBaseActivity() == null) {
            return;
        }

        getBaseActivity().requestAPI(observable, responseListener);
    }

    public void requestPermissions(final RequestPermissionCallback callback, final String... permission) {
        if (getBaseActivity() == null) {
            return;
        }

        getBaseActivity().requestPermissions(callback, permission);
    }

    public <T> void doBackgroundJob(ActionCallback<T> callback) {
        if (getBaseActivity() == null) {
            return;
        }

        getBaseActivity().doBackgroundJob(callback);
    }

    public void changeFragment(BaseMainFragment mainFragment, boolean addBackStack) {
        if (getBaseActivity() == null) {
            return;
        }
        getBaseActivity().changeFragment(mainFragment, addBackStack);
    }

    public <T> void startActivity(Class<T> tClass, Bundle bundle) {
        if (getBaseActivity() == null) {
            return;
        }
        getBaseActivity().startActivity(tClass, bundle);
    }

    public <T> void startActivity(Class<T> tClass) {
        startActivity(tClass, null);
    }

    public <T> void startActivityForResult(Class<T> tClass, Bundle bundle, int requestCode) {
        if (getBaseActivity() == null) {
            return;
        }
        getBaseActivity().startActivityForResult(tClass, bundle, requestCode);
    }

    public <T> void startActivityForResult(Class<T> tClass, int requestCode) {
        startActivityForResult(tClass, null, requestCode);
    }
}
