package com.baseapp.baseapp.module.login;

import android.text.TextUtils;

import com.baseapp.baseapp.base.BaseActivity;
import com.baseapp.baseapp.base.BasePresenter;
import com.baseapp.baseapp.utils.DialogUtils;

/**
 * Created by tunglam on 11/19/16.
 */

public class LoginPresenterImp extends BasePresenter<LoginPresenter.view> implements LoginPresenter.presenter {

    public LoginPresenterImp(BaseActivity activity) {
        super(activity);
    }


    @Override
    public void validate(String useName, String pass) {
        if (TextUtils.isEmpty(useName)) {
            getView().onVaildateError("user name not empty");
        } else if (TextUtils.isEmpty(pass)) {
            getView().onVaildateError("pass not empty");
        } else {
            getView().onValidateSuccess(useName, pass);
        }
    }

    @Override
    public void login(String useName, String pass) {
        if (isViewAttached()) {
            DialogUtils.showProgressBarCircle(getActivity());
            getView().onLoginSuccess();
        }
    }
}
