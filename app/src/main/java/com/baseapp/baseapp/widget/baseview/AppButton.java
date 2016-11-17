package com.baseapp.baseapp.widget.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.interfaces.DefaultFontView;
import com.baseapp.baseapp.utils.FontUtil;
import com.baseapp.baseapp.utils.Log;


/**
 * Created by mb on 7/14/16.
 */

public class AppButton extends Button implements DefaultFontView {
    public AppButton(Context context) {
        super(context);
        init(null);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void init(AttributeSet attributeSet) {
        String font = "";
        if (attributeSet != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.AppButton);
            font = typedArray.getString(R.styleable.AppButton_font);
            typedArray.recycle();
        }

        setFont(font);
    }

    @Override
    public void setFont(String font) {
        if (TextUtils.isEmpty(font)) {
            font = getContext().getString(R.string.font_lato_regular);
        }
        try {
            setTypeface(FontUtil.getFont(getContext(), font));
        } catch (Exception e) {
            Log.e(e);
        }
    }
}
