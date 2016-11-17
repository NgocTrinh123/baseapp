package com.baseapp.baseapp.di.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.baseapp.baseapp.base.BaseActivity;
import com.baseapp.baseapp.base.BaseFragment;
import com.baseapp.baseapp.di.module.AppModule;
import com.baseapp.baseapp.di.module.CommonModule;
import com.baseapp.baseapp.di.scope.AppScope;
import com.baseapp.baseapp.receiver.NetworkChangeReceiver;

import javax.inject.Named;

import dagger.Component;

/**
 * Created by mb on 7/27/16.
 */
@AppScope
@Component(modules = {AppModule.class, CommonModule.class})
public interface AppComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(NetworkChangeReceiver receiver);

    Context context();

    @Named("vertical_linear")
    LinearLayoutManager vertical_linear();

    @Named("horizontal_linear")
    LinearLayoutManager horizontal_linear();
}
