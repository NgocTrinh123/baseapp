package com.baseapp.baseapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baseapp.baseapp.R;
import com.baseapp.baseapp.base.BaseMainFragment;
import com.baseapp.baseapp.utils.Utils;

/**
 * Created by tunglam on 11/18/16.
 */
public class CalendarFragment extends BaseMainFragment {

    public static BaseMainFragment create(String name, boolean b) {
        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.setTitle(name);
        return calendarFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Utils.readCalendarEvent(getContext());

    }
}
