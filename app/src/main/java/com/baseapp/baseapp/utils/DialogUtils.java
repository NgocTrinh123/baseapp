package com.baseapp.baseapp.utils;

import android.content.Context;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseActivity;
import com.baseapp.baseapp.dialog.MessageDialog;
import com.baseapp.baseapp.widget.kprogress.KProgressHUD;


/**
 * Created by Jerry  on 30/03/2016.
 */
public class DialogUtils {
    private static KProgressHUD kProgressHUD;
    private static boolean hasProcess = false;

    public static void showDialogWithMessage(Context context, String message) {
        showDialogWithMessageButton(context, message, context.getString(R.string.ok), R.mipmap.popup_blue, null);
    }

    public static void showDialogWithMessage(Context context, String message, MessageDialog.OnCloseDialog onCloseDialog) {
        showDialogWithMessageButton(context, message, context.getString(R.string.ok), R.mipmap.popup_blue, onCloseDialog);
    }


    public static void showDialogWithMessageButton(Context context, String message, String button, MessageDialog.OnCloseDialog onCloseDialog) {
        showDialogWithMessageButton(context, message, button, "", 0, onCloseDialog);
    }


    public static void showDialogWithMessageButton(Context context, String message, String button, int icon, MessageDialog.OnCloseDialog onCloseDialog) {
        showDialogWithMessageButton(context, message, button, null, icon, onCloseDialog);
    }

    public static void showDialogWithMessageButtonBack(Context context, String message, String buttonOk, String cancale, MessageDialog.OnCloseDialog onCloseDialog) {
        showDialogWithMessageButton(context, message, buttonOk, cancale, R.mipmap.popup_red, onCloseDialog);
    }

    public static void showDialogWithMessageButton(Context context, String message, String positiveButton, String nagetiveButton, int icon, MessageDialog.OnCloseDialog onCloseDialog) {
        MessageDialog messageDialog = new MessageDialog(context);
        messageDialog.setMessage(message)
                .setPositiveButton(positiveButton)
                .setNegativeButton(nagetiveButton)
                .setIcon(icon)
                .setOnCloseDialogListener(onCloseDialog)
                .show();
    }


    public static void showProgressBarCircle(Context context) {
        if (context == null) {
            return;
        }
        if (kProgressHUD == null) {
            synchronized (KProgressHUD.class) {
                if (kProgressHUD == null) {
                    kProgressHUD = KProgressHUD.create(context)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(3)
                            .setDimAmount(0.5f)
                            .setSize(100, 100)
                            .setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.radius));
                }
            }
        }
        if (!((BaseActivity) context).isFinishing() && !kProgressHUD.isShowing()) {
            kProgressHUD.show();
            hasProcess = false;
        } else {
            hasProcess = true;
        }
    }

    public static void hideProgressBarCircle() {
        if (kProgressHUD != null && !hasProcess) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
        hasProcess = false;
    }
}
