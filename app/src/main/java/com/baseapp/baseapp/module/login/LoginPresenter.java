package com.baseapp.baseapp.module.login;

import com.baseapp.baseapp.base.BaseView;

/**
 * Created by tunglam on 11/19/16.
 */

public interface LoginPresenter {
    interface view extends BaseView {
        void onValidateSuccess(String useName, String pass);

        void onVaildateError(String message);

        void onLoginSuccess();
    }

    interface presenter {
        void login(String useName, String pass);

        void validate(String useName, String pass);
    }
}
