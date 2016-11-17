package com.baseapp.baseapp.base;

import android.text.TextUtils;
import android.widget.FrameLayout;

import com.baseapp.baseapp.listener.OnClickOptionMenu;
import com.baseapp.baseapp.listener.OnCreateCustomOptionMenuListener;
import com.baseapp.baseapp.listener.OnItemClickOptionMenu;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mb on 7/14/16.
 */

public class BaseMainFragment extends BaseFragment {
    private String title;
    private String optionMenuName;
    private OnClickOptionMenu listener;
    private OnClickOptionMenu leftListener;
    private OnItemClickOptionMenu itemClickOptionMenu;
    private OnCreateCustomOptionMenuListener onCreateCustomOptionMenuListener;
    private List<String> optionMenus;
    private int optionIcon;
    private int customOptionMenuViewId;
    private int selectedMenuPosition;
    private String leftText;
    private boolean isShowHomeMenu = false;
    private boolean enableOptionMenu = true;
    private boolean isBackStack = false;

    public void setTitle(String title) {
        this.title = title;
        if (getBaseActivity() != null) {
            getBaseActivity().setTitle(title);
        }
    }

    public void setEnableOptionMenu(boolean enableOptionMenu) {
        this.enableOptionMenu = enableOptionMenu;
        if (getBaseActivity() != null) {
            getBaseActivity().setEnableOptionMenu(enableOptionMenu);
        }
    }

    public void setOptionMenu(String name, OnClickOptionMenu listener) {
        optionMenuName = name;
        this.listener = listener;
        if (getBaseActivity() != null) {
            getBaseActivity().setOptionMenu(optionMenuName, this.listener);
        }
    }

    public void setOptionMenu(int icon, OnClickOptionMenu listener) {
        this.optionIcon = icon;
        this.listener = listener;
        if (getBaseActivity() != null) {
            getBaseActivity().setOptionMenu(optionIcon, this.listener);
        }
    }

    public void setOptionMenu(List<String> menus, final OnItemClickOptionMenu listener, int selectedPosition) {
        this.optionMenus = menus;
        this.selectedMenuPosition = selectedPosition;
        this.itemClickOptionMenu = new OnItemClickOptionMenu() {
            @Override
            public void onItemClicked(String name, int position) {
                BaseMainFragment.this.selectedMenuPosition = position;
                if (listener != null) {
                    listener.onItemClicked(name, position);
                }
            }
        };
        if (getBaseActivity() != null) {
            getBaseActivity().setOptionMenu(optionMenus, this.itemClickOptionMenu, selectedPosition);
        }
    }

    public void setCustomOptionMenu(int resId, OnCreateCustomOptionMenuListener onCreateCustomOptionMenuListener) {
        this.customOptionMenuViewId = resId;
        this.onCreateCustomOptionMenuListener = onCreateCustomOptionMenuListener;
        if (getBaseActivity() != null) {
            getBaseActivity().setCustomOptionMenu(customOptionMenuViewId, this.onCreateCustomOptionMenuListener);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isChildTab()) {
            setUpMenu();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getBaseActivity() == null) {
            return;
        }
        if (!isChildTab()) {
            leftListener = null;
            getBaseActivity().hideLeftButton();
            getBaseActivity().setLeftText(null, null);
            getBaseActivity().setOptionMenu("", null);
            getBaseActivity().setOptionMenu(0, null);
            getBaseActivity().setCustomOptionMenu(0, null);
            getBaseActivity().setOptionMenu(new ArrayList<String>(), null, selectedMenuPosition);
            getBaseActivity().setShowHomeMenu(false);
            getBaseActivity().setEnableOptionMenu(true);
        }
    }

    /**
     * Remove supper, override empty code for fragments inside BaseTabFragment
     */
    protected void setUpMenu() {
        if (getBaseActivity() == null) {
            return;
        }
        getBaseActivity().setShowHomeMenu(isShowHomeMenu);
        getBaseActivity().setLeftText(leftText, leftListener);
        if (!isShowHomeMenu && isBackStack) {
            getBaseActivity().showLeftButton();
        }
        getBaseActivity().setTitle(title);
        getBaseActivity().setOptionMenu(optionMenuName, listener);
        getBaseActivity().setOptionMenu(optionIcon, listener);
        if (customOptionMenuViewId > 0) {
            getBaseActivity().setCustomOptionMenu(customOptionMenuViewId, onCreateCustomOptionMenuListener);
        } else {
            getBaseActivity().setOptionMenu(optionMenus, itemClickOptionMenu, selectedMenuPosition);
        }
        getBaseActivity().setEnableOptionMenu(enableOptionMenu);
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean hasOptionMenu() {
        return !TextUtils.isEmpty(optionMenuName) || optionIcon > 0;
    }

    public String getTitle() {
        return title;
    }

    public OnClickOptionMenu getListener() {
        return listener;
    }

    public String getOptionMenuName() {
        return optionMenuName;
    }

    public int getOptionIcon() {
        return optionIcon;
    }

    public boolean isChildTab() {
        return false;
    }

    public void setLeftText(String text, OnClickOptionMenu onClickOptionMenu) {
        this.leftText = text;
        leftListener = onClickOptionMenu;
    }

    public void setShowHomeMenu(boolean isShow) {
        isShowHomeMenu = isShow;
        if (getBaseActivity() == null) {
            return;
        }

        getBaseActivity().setShowHomeMenu(isShow);
    }

    public void hideToolBar(boolean isShow) {
        if (getBaseActivity() == null) {
            return;
        }

        getBaseActivity().hideToolBar(isShow);
    }

    public boolean isShowHomeMenu() {
        return isShowHomeMenu;
    }

    public void setBackStack(boolean backStack) {
        isBackStack = backStack;
    }

    public FrameLayout getCustomOptionMenu() {
        return getBaseActivity().getCustomOptionMenu();
    }


}
