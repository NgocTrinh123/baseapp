package com.baseapp.baseapp.widget.baseview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.interfaces.DefaultFontView;
import com.baseapp.baseapp.utils.FontUtil;
import com.baseapp.baseapp.utils.Log;

/**
 * Created by mb on 7/14/16.
 */

public class AppEditText extends EditText implements DefaultFontView {
    public AppEditText(Context context) {
        super(context);
        init(null);
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    public void init(AttributeSet attributeSet) {
        String font = "";
        if (attributeSet != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.AppEditText);
            font = typedArray.getString(R.styleable.AppEditText_font);
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
