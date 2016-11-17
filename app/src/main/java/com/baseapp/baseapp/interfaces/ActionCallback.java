package com.baseapp.baseapp.interfaces;

/**
 * Created by mb on 7/27/16.
 */

public interface ActionCallback<T> {
    T onBackground();

    void onForeground(T result);
}
