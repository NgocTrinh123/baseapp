package com.baseapp.baseapp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseActivity;
import com.baseapp.baseapp.base.BaseAdapter;
import com.baseapp.baseapp.base.BaseFragmentActivity;
import com.baseapp.baseapp.listener.OnRecyclerViewClickListener;
import com.baseapp.baseapp.listener.RecyclerTouchListener;
import com.baseapp.baseapp.ui.fragment.CalendarFragment;
import com.baseapp.baseapp.ui.fragment.FireBaseChatFragment;
import com.baseapp.baseapp.ui.fragment.HomeFragment;
import com.baseapp.baseapp.ui.fragment.MapsFragment1;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tunglam on 11/17/16.
 */
public class LeftDrawer {
    @Bind(R.id.lv_dashboard_menu)
    RecyclerView lvMenu;

    private FrameLayout rootView;
    private BaseFragmentActivity activity;

    public LeftDrawer(FrameLayout rootView, BaseFragmentActivity activity) {
        this.rootView = rootView;
        this.activity = activity;
    }

    public void setUp() {
        rootView.removeAllViews();
        rootView.addView(activity.getLayoutInflater().inflate(R.layout.dashboard_left_menu, rootView, false));
        ButterKnife.bind(this, rootView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        lvMenu.setLayoutManager(linearLayoutManager);
        lvMenu.addOnItemTouchListener(new RecyclerTouchListener(activity, lvMenu, new OnRecyclerViewClickListener() {
            @Override
            public void onClick(RecyclerView.Adapter adapter, View view, int position) {
                clickCloseMenu();
                itemClick(((LeftMenuAdapter) adapter).getItem(position), position);
            }
        }));

        LeftMenuAdapter adapter = new LeftMenuAdapter(activity);
        adapter.addAll(Arrays.asList(activity.getResources().getStringArray(R.array.dashboard_menu)));
        lvMenu.setAdapter(adapter);
    }

    @OnClick(R.id.imv_close_menu)
    void clickCloseMenu() {
        activity.getDrawerLayout().closeDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.btn_back)
    void clickClickBack() {
        activity.finish();
    }

    private void itemClick(String name, int position) {
        switch (position) {
            case 1:
                activity.changeFragment(MapsFragment1.create(name, true), false);
                break;
//            case 2:
//                activity.changeFragment(UnderConstructorFragment.create(name, true), false);
//                break;
            case 2:
                activity.changeFragment(CalendarFragment.create(name, true), false);
                break;
            case 3:
                activity.changeFragment(FireBaseChatFragment.create(name, true), false);
                break;
            default:
                activity.changeFragment(new HomeFragment(), false);
                break;
        }
    }

    private class LeftMenuAdapter extends BaseAdapter<String, LeftMenuAdapter.ViewHolder> {
        public LeftMenuAdapter(BaseActivity mActivity) {
            super(mActivity, false);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mActivity.getLayoutInflater().inflate(R.layout.item_dashboard_menu, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(position);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvName;
            private ImageView ivIcon;

            public ViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tv_name);
                ivIcon = (ImageView) itemView.findViewById(R.id.item_dashboard_menu_iv_icon);
            }

            public void bind(int position) {
                tvName.setText(getItem(position));
            }
        }
    }

}
