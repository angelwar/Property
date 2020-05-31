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
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huanzong.property.R;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.fragment.admin.FragmentYezhu;
import com.huanzong.property.fragment.admin.UserData;
import com.huanzong.property.fragment.admin.UserDataBase;
import com.huanzong.property.fragment.sale.FragmentNoYuyue;
import com.huanzong.property.fragment.sale.FragmentOrderListAdapter;
import com.huanzong.property.fragment.sale.FragmentZushou;
import com.huanzong.property.fragment.sale.FragmentZushou1;
import com.huanzong.property.fragment.sale.FragmentZushou2;
import com.huanzong.property.fragment.sale.FragmentZushou3;
import com.huanzong.property.fragment.sale.SaleData;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.PocketSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tableLayout;
    PocketSwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_main_2,null);
        viewPager = content.findViewById(R.id.order_viewpager);
        tableLayout = content.findViewById(R.id.order_tab);
        swipeRefreshLayout = content.findViewById(R.id.sw_sale);
    return content;
    }

    List<Fragment> fragments;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList();
        fragments.add(new FragmentZushou());
        fragments.add(new FragmentZushou1());
        fragments.add(new FragmentZushou2());
        fragments.add(new FragmentZushou3());

        FragmentOrderListAdapter adapter = new FragmentOrderListAdapter(getActivity().getSupportFragmentManager(),fragments, new String[]{"租房", "售房", "已交易房屋","未处理预约"});
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

//        getData();
    }

    private void getData() {
        HttpServer.getAPIService().onGetHouse(0,0,"",0).enqueue(new Callback<DataBase<UserDataBase<UserData<SaleData>>>>() {

            @Override
            public void onResponse(Call<DataBase<UserDataBase<UserData<SaleData>>>> call, Response<DataBase<UserDataBase<UserData<SaleData>>>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.body()!=null){

                    if (response.body()!=null&&response.body().getCode()==1){
                        UserData list = response.body().getData().getUsers();
                        if (fragments.get(0) instanceof FragmentZushou){
                            FragmentZushou fragmentYezhu = (FragmentZushou) fragments.get(0);
//                            fragmentYezhu.setListData(list.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<DataBase<UserDataBase<UserData<SaleData>>>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
