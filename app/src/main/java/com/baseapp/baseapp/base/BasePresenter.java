package com.baseapp.baseapp.base;

/**
 * Created by tunglam on 11/19/16.
 */

public class BasePresenter<V extends BaseView> implements IPresenter<V> {
    private V mMvpView;

    private boolean isLoading;

    protected BaseActivity activity;

    public BasePresenter(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getView() {
        return mMvpView;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public BaseActivity getActivity() {
        return activity;
    }
}
