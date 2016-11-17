package com.baseapp.baseapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseMainFragment;
import com.baseapp.baseapp.listener.OnClickOptionMenu;

import butterknife.ButterKnife;

/**
 * Created by tunglam on 11/16/16.
 */

public class HomeFragment extends BaseMainFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("HomeFragment");
        setShowHomeMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOptionMenu(R.mipmap.ic_launcher, new OnClickOptionMenu() {
            @Override
            public void onClick() {
                Toast.makeText(getActivity(), "Right menu", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
