package com.baseapp.baseapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseActivity;
import com.baseapp.baseapp.base.BaseDialog;
import com.baseapp.baseapp.module.login.LoginPresenter;
import com.baseapp.baseapp.module.login.LoginPresenterImp;
import com.baseapp.baseapp.utils.DialogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tunglam on 11/19/16.
 */

public class LoginActivity extends BaseActivity implements LoginPresenter.view {

    @Bind(R.id.activity_login_et_username)
    EditText etUserName;

    @Bind(R.id.activity_login_et_pass)
    EditText etPass;

    LoginPresenterImp loginPresenterImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenterImp = new LoginPresenterImp(this);
        loginPresenterImp.attachView(this);
    }

    @OnClick(R.id.activity_login_bt_login)
    void onClickButton() {
        //validate Data
        loginPresenterImp.validate(etUserName.getText().toString(), etPass.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        loginPresenterImp.detachView();
    }

    @Override
    public void onValidateSuccess(String useName, String pass) {
        loginPresenterImp.login(useName, pass);
    }

    @Override
    public void onVaildateError(String message) {
        DialogUtils.showDialogWithMessageButton(this, message, "ok", new BaseDialog.OnCloseDialog() {
            @Override
            public void onPositive() {

            }

            @Override
            public void onNegative() {

            }

            @Override
            public void onClose() {

            }
        });

    }

    @Override
    public void onLoginSuccess() {
        startActivity(ActivityMain.class);
        finish();
    }
}
