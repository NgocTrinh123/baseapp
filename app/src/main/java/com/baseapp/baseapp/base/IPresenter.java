package com.baseapp.baseapp.base;

/**
 * Created by tunglam on 11/19/16.
 */
public interface IPresenter<V> {
    void attachView(V mvpView);

    void detachView();
}
