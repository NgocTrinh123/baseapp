package com.baseapp.baseapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.baseapp.baseapp.MyApplication;
import com.baseapp.baseapp.interfaces.ActionCallback;
import com.baseapp.baseapp.interfaces.RequestPermissionCallback;
import com.baseapp.baseapp.listener.OnClickOptionMenu;
import com.baseapp.baseapp.listener.OnCreateCustomOptionMenuListener;
import com.baseapp.baseapp.listener.OnItemClickOptionMenu;
import com.baseapp.baseapp.model.database.CurrentUser;
import com.baseapp.baseapp.net.MyApi;
import com.baseapp.baseapp.utils.BackgroundUtils;
import com.baseapp.baseapp.utils.Log;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by tunglam on 11/10/16.
 */

public class BaseActivity extends AppCompatActivity{
    @Inject
    MyApi api;

    @Inject
    EventBus eventBus;

    @Inject
    Realm realm;

    @Inject
    CurrentUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().getAppComponent().inject(this);
    }

    public void setEnableOptionMenu(boolean enableOptionMenu) {
        Log.d("Overriding yourself");
    }

    public void setTitle(String title) {
        Log.d("Overriding yourself");
    }

    public void changeFragment(BaseMainFragment fragment, boolean addBackStack) {
        Log.d("Overriding yourself");
    }

    public void setOptionMenu(String title, OnClickOptionMenu listener) {
        Log.d("Overriding yourself");
    }

    public void setOptionMenu(int icon, OnClickOptionMenu listener) {
        Log.d("Overriding yourself");
    }

    public void setShowHomeMenu(boolean isShow) {
        Log.d("Overriding yourself");
    }

    public void hideLeftButton() {
        Log.d("Overriding yourself");
    }

    public void showLeftButton() {
        Log.d("Overriding yourself");
    }

    public void hideToolBar(boolean isShow) {
        Log.d("Overriding yourself");
    }

    public void setOptionMenu(List<String> menus, OnItemClickOptionMenu listener, int selectedPosition) {
        Log.d("Overriding yourself");
    }

    public void setCustomOptionMenu(int resId, OnCreateCustomOptionMenuListener onCreateCustomOptionMenuListener) {
        Log.d("Overriding yourself");
    }

    public void setLeftText(String text, OnClickOptionMenu onClickOptionMenu) {
        Log.d("Overriding yourself");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getRealm().close();
    }

    public <T> void requestAPI(Observable<T> observable, ResponseListener<T> responseListener) {
        if (observable == null) {
            return;
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseListener);
    }

    public void requestPermissions(final RequestPermissionCallback callback, final String... permission) {
        RxPermissions.getInstance(this)
                .request(permission)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (callback == null) {
                            return;
                        }

                        if (granted) {
                            callback.onAccepted();
                        } else {
                            callback.onDenied();
                        }
                    }
                });
    }

    public <T> void doBackgroundJob(ActionCallback<T> callback) {
        BackgroundUtils.doAction(callback);
    }

    public <T> void startActivity(Class<T> tClass, Bundle bundle) {
        Intent intent = new Intent(this, tClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public <T> void startActivity(Class<T> tClass) {
        startActivity(tClass, null);
    }

    public <T> void startActivityForResult(Class<T> tClass, int requestCode) {
        startActivityForResult(tClass, null, requestCode);
    }

    public <T> void startActivityForResult(Class<T> tClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, tClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }

    public FrameLayout getCustomOptionMenu() {
        return null;
    }

}
