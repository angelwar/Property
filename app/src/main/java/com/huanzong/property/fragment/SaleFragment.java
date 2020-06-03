package com.huanzong.property.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huanzong.property.R;
import com.huanzong.property.fragment.sale.FragmentOrderListAdapter;
import com.huanzong.property.fragment.sale.FragmentZushou;
import com.huanzong.property.fragment.sale.FragmentZushou1;
import com.huanzong.property.fragment.sale.FragmentZushou2;
import com.huanzong.property.fragment.sale.FragmentZushou3;
import com.huanzong.property.fragment.sale.FragmentZushouNoIdent;
import com.huanzong.property.util.PocketSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class SaleFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tableLayout;
    int index = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_main_2,null);
        viewPager = content.findViewById(R.id.order_viewpager);
        tableLayout = content.findViewById(R.id.order_tab);

        Bundle bundle = getArguments();
        if (bundle!=null) {
            index = bundle.getInt("index");
        }
    return content;
    }

    List<Fragment> fragments;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList();
        fragments.add(new FragmentZushouNoIdent());
        fragments.add(new FragmentZushou());
        fragments.add(new FragmentZushou1());
        fragments.add(new FragmentZushou2());
        fragments.add(new FragmentZushou3());

        FragmentOrderListAdapter adapter = new FragmentOrderListAdapter(getActivity().getSupportFragmentManager(),fragments, new String[]{"未认证", "租房", "售房", "已交易","预约中"});
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                tableLayout.getTabAt(index).select();
            }
        }, 100);
    }
}
