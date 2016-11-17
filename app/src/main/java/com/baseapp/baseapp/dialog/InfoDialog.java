package com.baseapp.baseapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.baseapp.baseapp.R;

/**
 * Created by tunglam on 11/18/16.
 */

public class InfoDialog extends Dialog {

    public InfoDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info);
    }
}
