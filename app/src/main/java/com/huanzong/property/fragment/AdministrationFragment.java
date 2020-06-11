package com.huanzong.property.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huanzong.property.R;
import com.huanzong.property.activity.SearchActivity;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.fragment.admin.FragmentUser1;
import com.huanzong.property.fragment.admin.FragmentUser2;
import com.huanzong.property.fragment.admin.FragmentUserBlack;
import com.huanzong.property.fragment.admin.FragmentUserNoIdent;
import com.huanzong.property.fragment.admin.FragmentVisitor;
import com.huanzong.property.fragment.admin.UserAdapter;
import com.huanzong.property.fragment.admin.data.User;
import com.huanzong.property.fragment.admin.data.UserData;
import com.huanzong.property.fragment.admin.data.UserDataBase;
import com.huanzong.property.fragment.sale.FragmentOrderListAdapter;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.SharedPreferencesUtil;
import com.huanzong.property.util.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdministrationFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tablayout;
    TextView tv_search;

    List<Fragment> fragments;

    int index = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_main_1,null);
        viewPager = content.findViewById(R.id.order_viewpager);
        tablayout = content.findViewById(R.id.order_tab);
        tv_search = content.findViewById(R.id.tv_search);
        Bundle bundle = getArguments();
        if (bundle!=null) {
            index = bundle.getInt("index");
        }

        tv_search.setOnClickListener(v -> getActivity().startActivity(new Intent(getActivity(),SearchActivity.class)));
    return content;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList();
        fragments.add(new FragmentUserNoIdent());
        fragments.add(new FragmentUser1());
        fragments.add(new FragmentUser2());//租客
        fragments.add(new FragmentVisitor());
        fragments.add(new FragmentUserBlack());

        FragmentOrderListAdapter adapter = new FragmentOrderListAdapter(getActivity().getSupportFragmentManager(),fragments, new String[]{"未认证","业主", "租客", "访客","拉黑"});
        viewPager.setAdapter(adapter);

        if (SharedPreferencesUtil.isMove(getActivity())){
            tablayout.postDelayed(() -> {
                tablayout.getTabAt(index).select();
                SharedPreferencesUtil.addisMove(getActivity(),false);
            }, 100);
        }

        tablayout.setupWithViewPager(viewPager);
    }
}
