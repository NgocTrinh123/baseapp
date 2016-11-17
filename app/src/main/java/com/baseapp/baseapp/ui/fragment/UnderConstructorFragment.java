package com.baseapp.baseapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseMainFragment;

/**
 * Created by tunglam on 11/17/16.
 */
public class UnderConstructorFragment extends BaseMainFragment {
    private static final String TITLE = "title";
    private static final String SHOW_MENU = "show_menu";

    public static BaseMainFragment create(String title) {
        return create(title, false);
    }

    public static BaseMainFragment create(String title, boolean showMenu) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putBoolean(SHOW_MENU, showMenu);
        UnderConstructorFragment underConstructorFragment = new UnderConstructorFragment();
        underConstructorFragment.setArguments(bundle);
        return underConstructorFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String title = getArguments().getString(TITLE);
            if (!TextUtils.isEmpty(title)) {
                setTitle(title);
            }
            setShowHomeMenu(getArguments().getBoolean(SHOW_MENU));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_under_constructor, container, false);
    }
}
