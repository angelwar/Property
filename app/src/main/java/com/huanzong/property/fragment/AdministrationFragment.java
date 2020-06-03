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
import com.huanzong.property.fragment.admin.FragmentUser1;
import com.huanzong.property.fragment.admin.FragmentUser2;
import com.huanzong.property.fragment.admin.FragmentUserBlack;
import com.huanzong.property.fragment.admin.FragmentUserNoIdent;
import com.huanzong.property.fragment.admin.FragmentVisitor;
import com.huanzong.property.fragment.sale.FragmentOrderListAdapter;
import java.util.ArrayList;
import java.util.List;

public class AdministrationFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tablayout;

    List<Fragment> fragments;

    int index = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_main_1,null);
        viewPager = content.findViewById(R.id.order_viewpager);
        tablayout = content.findViewById(R.id.order_tab);

        Bundle bundle = getArguments();
        if (bundle!=null) {
            index = bundle.getInt("index");
        }
    return content;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList();
        fragments.add(new FragmentUserNoIdent());
        fragments.add(new FragmentUser1());
        fragments.add(new FragmentUser2());
        fragments.add(new FragmentVisitor());
        fragments.add(new FragmentUserBlack());

        FragmentOrderListAdapter adapter = new FragmentOrderListAdapter(getActivity().getSupportFragmentManager(),fragments, new String[]{"未认证","业主", "租客", "访客","拉黑"});
        viewPager.setAdapter(adapter);

        tablayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                tablayout.getTabAt(index).select();
            }
        }, 100);
        tablayout.setupWithViewPager(viewPager);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
