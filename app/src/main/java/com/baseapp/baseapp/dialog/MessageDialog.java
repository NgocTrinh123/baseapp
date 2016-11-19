package com.baseapp.baseapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseDialog;
import com.baseapp.baseapp.widget.baseview.other.AutoSizeTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jerry  on 30/03/2016.
 */
public class MessageDialog extends BaseDialog {

    @Bind(R.id.message_text)
    AutoSizeTextView tvMessage;

    @Bind(R.id.btn_negative)
    Button btnClose;

    @Bind(R.id.btn_positive)
    Button btnOk;

    @Bind(R.id.imv_icon)
    ImageView imvIcon;

    private String message = "";
    private String negativeButton, positiveButton;
    private int icon;

    public MessageDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);
        ButterKnife.bind(this);
        tvMessage.setMinTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.default_text_small));
    }

    public MessageDialog setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            this.message = message;
        }
        return this;
    }

    public MessageDialog setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
        return this;
    }

    public MessageDialog setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
        return this;
    }

    public MessageDialog setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public void show() {
        super.show();
        if (icon > 0) {
            imvIcon.setImageResource(icon);
        }
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(Html.fromHtml(message));
        }
        if (!TextUtils.isEmpty(negativeButton)) {
            btnClose.setText(Html.fromHtml(negativeButton));
            btnClose.setVisibility(View.VISIBLE);
        } else {
            btnClose.setText(Html.fromHtml(""));
            btnClose.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(positiveButton)) {
            btnOk.setVisibility(View.VISIBLE);
            btnOk.setText(Html.fromHtml(positiveButton));
        } else {
            btnOk.setText(Html.fromHtml(""));
            btnOk.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_negative)
    void clickCancel() {
        dismiss();
        if (getListener() != null) {
            getListener().onNegative();
        }
    }

    @OnClick(R.id.btn_positive)
    void onClickOk() {
        dismiss();
        if (getListener() != null) {
            getListener().onPositive();
        }
    }
}
