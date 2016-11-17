package com.baseapp.baseapp.base;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.baseapp.baseapp.MyApplication;
import com.baseapp.baseapp.R;
import com.baseapp.baseapp.interfaces.ActionCallback;
import com.baseapp.baseapp.utils.BackgroundUtils;
import com.baseapp.baseapp.utils.Log;
import com.baseapp.baseapp.utils.NetworkUtils;
import com.baseapp.baseapp.utils.ParserUtil;
import com.baseapp.baseapp.utils.Utils;
import com.google.gson.reflect.TypeToken;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by mb on 3/18/16
 */
public abstract class ResponseListener<T> implements Observer<T> {
    private MessageResponse messageResponse = null;
    private boolean fromCache;

    @Override
    public final void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        String message = MyApplication.getInstance().getString(R.string.general_error);
        int statusCode = 404;
        String code = "unknown";
        try {
            Log.d(e.toString());
            if (e instanceof ConnectException || e instanceof NetworkErrorException || e instanceof UnknownHostException) {
                message = MyApplication.getInstance().getString(R.string.connection_error);
                statusCode = 500;
                code = "network_error";
            } else if (e instanceof HttpException) {
                retrofit2.Response response = ((HttpException) e).response();
                statusCode = response.code();
                if (statusCode == 401) {
                    code = "token_expired";
                    message = response.message();
                } else if (response.errorBody() != null) {
                    String m = response.errorBody().string();
                    if (!TextUtils.isEmpty(m)) {
                        messageResponse = ParserUtil.fromJson(m, new TypeToken<MessageResponse>() {
                        }.getType());

                        if (messageResponse != null) {
                            if (!TextUtils.isEmpty(messageResponse.getMessage())) {
                                message = messageResponse.getMessage();
                                code = messageResponse.getCode();
                            }
                        }
                    }
                }
            } else {
                Log.d(e.toString());
            }
        } catch (Exception exp) {
            Log.e(exp);
        }

        message = Utils.upperCaseFirstChar(message);

        if (messageResponse == null) {
            messageResponse = new MessageResponse();
            List<String> messages = new ArrayList<>();
            messages.add(message);
            messageResponse.setMessages(messages);
            messageResponse.setStatusCode(statusCode);
        }
        messageResponse.setMessage(message);
        messageResponse.setCode(code);

        if (!NetworkUtils.availableWifiOr3G(MyApplication.getInstance())) {
            BackgroundUtils.doAction(new ActionCallback<T>() {
                @Override
                public T onBackground() {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    T t = onFetchDataFromCacheBG(realm);
                    realm.commitTransaction();
                    return t;
                }

                @Override
                public void onForeground(T result) {
                    if (result != null) {
                        fromCache = true;
                        onSuccess(result);
                    } else {
                        onError(messageResponse);
                    }
                }
            });
            return;
        }
        onError(messageResponse);
    }

    @Override
    public void onNext(T s) {
        onSuccess(s);
        if (!fromCache) {
            try {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                onSaveData(s, realm);
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.e(e);
            }
        }
    }

    public abstract void onSuccess(T result);

    public abstract void onError(@NonNull MessageResponse messageResponse);

    public T onFetchDataFromCacheBG(Realm realm) {
        return null;
    }

    public boolean isFromCache() {
        return fromCache;
    }

    public void onSaveData(T result, Realm realm) {
    }
}
