package com.baseapp.baseapp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.listener.OnClickOptionMenu;
import com.baseapp.baseapp.ui.fragment.HomeFragment;
import com.baseapp.baseapp.utils.Log;
import com.baseapp.baseapp.widget.WidgetUtils;
import com.lb.auto_fit_textview.AutoResizeTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mb on 7/14/16.
 */

public abstract class BaseFragmentActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_title)
    AutoResizeTextView tvTitle;

    @Bind(R.id.tv_left)
    TextView tvLeft;

    @Bind(R.id.option_menu)
    TextView tvOptionMenu;

    @Bind(R.id.imv_option_menu)
    ImageView imvOptionMenu;

    @Bind(R.id.imv_logo)
    ImageView imvActionbarLogo;

    @Bind(R.id.custom_option_menu)
    FrameLayout vCustomOptionMenu;

    @Bind(R.id.drawer_root)
    DrawerLayout drawerLayout;

    @Bind(R.id.left_drawer)
    FrameLayout leftDrawerView;

    @Bind(R.id.frame_container)
    public FrameLayout frameContainer;

    private BaseMainFragment currentFragment, homeFragment;
    private OnClickOptionMenu leftClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        ButterKnife.bind(this);
        tvTitle.setMinTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15.5f, getResources().getDisplayMetrics()));
        if (!hasLeftDrawer()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        setUpToolbar();
        setShowHomeMenu(false);
        setLeftText("back", null);
        homeFragment = getHomeFragment();
        if (homeFragment != null) {
            changeFragment(homeFragment, false);
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        ActionBar actionBar = getSupportActionBar();
        setTitle("");
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDefaultDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void setEnableOptionMenu(boolean enableOptionMenu) {
        if (tvOptionMenu == null) {
            return;
        }
        tvOptionMenu.setEnabled(enableOptionMenu);
        if (enableOptionMenu) {
            tvOptionMenu.setTextColor(ContextCompat.getColor(this, R.color.option_menu_color_list));
        } else {
            tvOptionMenu.setTextColor(ContextCompat.getColor(this, R.color.divider_color));
        }

        if (imvOptionMenu == null) {
            return;
        }
        imvOptionMenu.setEnabled(enableOptionMenu);
        int colorImage = enableOptionMenu ? ContextCompat.getColor(this, android.R.color.transparent) : ContextCompat.getColor(this, R.color.divider_color);
        imvOptionMenu.setColorFilter(colorImage);

        if (vCustomOptionMenu == null) {
            return;
        }
        vCustomOptionMenu.setEnabled(enableOptionMenu);
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    @Override
    public void setShowHomeMenu(boolean isShow) {
        if (imvActionbarLogo != null) {
            imvActionbarLogo.setVisibility(isShow ? View.VISIBLE : View.GONE);
            if (isShow) {
                tvLeft.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void hideToolBar(boolean isShow) {
        if (toolbar == null) {
            return;
        }
        if (isShow) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setOptionMenu(String name, final OnClickOptionMenu listener) {
        if (tvOptionMenu == null) {
            return;
        }

        if (TextUtils.isEmpty(name)) {
            name = "";
            tvOptionMenu.setOnClickListener(null);
            tvOptionMenu.setVisibility(View.GONE);
        } else {
            tvOptionMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick();
                    }
                }
            });
            tvOptionMenu.setVisibility(View.VISIBLE);
        }
        tvOptionMenu.setText(name);
    }

    @Override
    public void setOptionMenu(int icon, final OnClickOptionMenu listener) {
        if (imvOptionMenu == null) {
            return;
        }
        if (icon != 0) {
            imvOptionMenu.setVisibility(View.VISIBLE);
            imvOptionMenu.setImageResource(icon);
            imvOptionMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick();
                    }
                }
            });
        } else {
            imvOptionMenu.setVisibility(View.GONE);
            imvOptionMenu.setImageDrawable(null);
            imvOptionMenu.setOnClickListener(null);
        }
    }

    public void showLeftButton() {
        if (tvLeft != null) {
            tvLeft.setVisibility(View.VISIBLE);
        }

        if (imvActionbarLogo != null) {
            imvActionbarLogo.setVisibility(View.GONE);
        }
    }

    public void hideLeftButton() {
        if (tvLeft != null) {
            tvLeft.setVisibility(View.GONE);
        }
    }

    @Override
    public void setLeftText(String text, final OnClickOptionMenu onClickOptionMenu) {
        leftClickListener = onClickOptionMenu;
        if (TextUtils.isEmpty(text)) {
            text = "back";
        }
        if (tvLeft != null) {
            tvLeft.setText(text);
        }
    }

    public void changeFragment(BaseMainFragment fragment, boolean addBackStack) {
        if (fragment == null) {
            return;
        }
        currentFragment = fragment;
        if (homeFragment == null) {
            homeFragment = fragment;
        }

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!addBackStack) {
            hideLeftButton();
            //clear stack
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            for (int i = 0; i < count; ++i) {
                fm.popBackStackImmediate();
            }
        }
        transaction.replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName());
        fragment.setBackStack(addBackStack);
        if (addBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commitAllowingStateLoss();
    }

    public abstract BaseMainFragment getHomeFragment();

    public abstract boolean hasLeftDrawer();

    public void setLogo(int res) {
        if (imvActionbarLogo != null) {
            imvActionbarLogo.setImageResource(res);
        }
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public FrameLayout getLeftDrawerView() {
        return leftDrawerView;
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        WidgetUtils.hideSoftKeyboard(this);

        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                return;
            }
        }

        if (currentFragment != null && currentFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();

        if (getSupportFragmentManager() != null) {
            currentFragment = getActiveFragment();
        }

        if (currentFragment == null) {
            currentFragment = homeFragment;
        }
    }

    public BaseMainFragment getActiveFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return null;
        }
        String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
        return (BaseMainFragment) getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (currentFragment != null) {
            currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick(R.id.tv_left)
    void onClickLeftIcon() {
        Log.d("Back: " + tvLeft.getText().toString());
        if (tvLeft.getText().equals("back")) {
            Log.d("Back: Pressed");
            onBackPressed();
            return;
        }

        if (leftClickListener != null) {
            Log.d("Back: Custom");
            leftClickListener.onClick();
            return;
        }

        onBackPressed();
    }

    @OnClick(R.id.imv_logo)
    public void onClickLogo() {
        changeFragment(new HomeFragment(), false);
    }

    public FrameLayout getCustomOptionMenu() {
        return vCustomOptionMenu;
    }
}

