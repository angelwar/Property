package com.huanzong.property.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huanzong.property.R;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.fragment.admin.FragmentUser1;
import com.huanzong.property.fragment.admin.FragmentVisitor;
import com.huanzong.property.fragment.admin.FragmentYezhu;
import com.huanzong.property.fragment.admin.TitleAdapter;
import com.huanzong.property.fragment.admin.User;
import com.huanzong.property.fragment.admin.UserData;
import com.huanzong.property.fragment.admin.UserDataBase;
import com.huanzong.property.fragment.sale.FragmentNoYuyue;
import com.huanzong.property.fragment.sale.FragmentOrderListAdapter;
import com.huanzong.property.fragment.sale.FragmentZushou;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.PocketSwipeRefreshLayout;
import com.huanzong.property.util.SharedPreferencesUtil;
import com.youth.xframe.adapter.XRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdministrationFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tableLayout;

    List<Fragment> fragments;
    PocketSwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_main_1,null);

        viewPager = content.findViewById(R.id.order_viewpager);
        tableLayout = content.findViewById(R.id.order_tab);
        swipeRefreshLayout = content.findViewById(R.id.sw_user);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    return content;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList();
        fragments.add(new FragmentUser1());
        fragments.add(new FragmentUser1());
        fragments.add(new FragmentVisitor());
        fragments.add(new FragmentUser1());

        FragmentOrderListAdapter adapter = new FragmentOrderListAdapter(getActivity().getSupportFragmentManager(),fragments, new String[]{"业主", "租客", "访客","拉黑"});
        viewPager.setAdapter(adapter);

        tableLayout.setupWithViewPager(viewPager);
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

//        getData();
    }

    private void getData() {
        HttpServer.getAPIService().onUserList(SharedPreferencesUtil.queryCid(getActivity()),0,"",0,0,0).enqueue(new Callback<DataBase<UserDataBase<UserData<User>>>>() {
            @Override
            public void onResponse(Call<DataBase<UserDataBase<UserData<User>>>> call, Response<DataBase<UserDataBase<UserData<User>>>> response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.e("log","success "+response.body().getData());
                if (response.body()!=null&&response.body().getCode()==1){
                    UserData list = response.body().getData().getUsers();
                    if (fragments.get(0) instanceof FragmentUser1){
                        FragmentUser1 fragmentYezhu = (FragmentUser1) fragments.get(0);
                        fragmentYezhu.setListData();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataBase<UserDataBase<UserData<User>>>> call, Throwable t) {
                Log.e("log","error "+t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }
}
