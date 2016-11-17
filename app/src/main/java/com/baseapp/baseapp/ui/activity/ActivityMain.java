package com.baseapp.baseapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseFragmentActivity;
import com.baseapp.baseapp.base.BaseMainFragment;
import com.baseapp.baseapp.ui.fragment.HomeFragment;
import com.baseapp.baseapp.ui.fragment.MapsFragment1;
import com.baseapp.baseapp.utils.Constants;

/**
 * Created by tunglam on 11/10/16.
 */

public class ActivityMain extends BaseFragmentActivity {

    public static final String TYPE = "type";
    private String type;
    private LeftDrawer leftDrawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            type = getIntent().getStringExtra(TYPE);
        }
        if (TextUtils.isEmpty(type)) {
            type = Constants.Dashboard.PRODUCTION_LEVEL;
        }
        super.onCreate(savedInstanceState);
        leftDrawer = new LeftDrawer(getLeftDrawerView(), this);
        leftDrawer.setUp();
    }

    @Override
    public BaseMainFragment getHomeFragment() {
        switch (type) {
            case Constants.Dashboard.MENU_MAPS:
                return new MapsFragment1();
            default:
                return new HomeFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLogo(R.mipmap.ic_menu);
    }

    @Override
    public void onClickLogo() {
        if (!getDrawerLayout().isDrawerOpen(Gravity.LEFT)) {
            getDrawerLayout().openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public boolean hasLeftDrawer() {
        return false;
    }
}
