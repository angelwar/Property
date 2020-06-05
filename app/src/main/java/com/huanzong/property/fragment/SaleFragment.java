package com.huanzong.property.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huanzong.property.R;
import com.huanzong.property.activity.PublishActivity;
import com.huanzong.property.fragment.sale.FragmentDealYuyue;
import com.huanzong.property.fragment.sale.FragmentNoYuyue;
import com.huanzong.property.fragment.sale.FragmentOrderListAdapter;
import com.huanzong.property.fragment.sale.FragmentZushou;
import com.huanzong.property.fragment.sale.FragmentZushou1;
import com.huanzong.property.fragment.sale.FragmentZushouNoIdent;
import com.huanzong.property.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SaleFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tableLayout;
    int index = 0;

    private TextView publish;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_main_2,null);
        viewPager = content.findViewById(R.id.order_viewpager);
        tableLayout = content.findViewById(R.id.order_tab);
        publish = content.findViewById(R.id.publish);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity()).setTitleText("请选择租售方式")
                        .setCancelText("发布租房")
                        .setConfirmText("发布售房")
                        .setCancelClickListener(sweetAlertDialog ->{
                            getActivity().startActivity(new Intent(getActivity(), PublishActivity.class));
                            sweetAlertDialog.dismiss();
                        })
                        .setConfirmClickListener(sweetAlertDialog -> {
                            getActivity().startActivity(new Intent(getActivity(), PublishActivity.class));
                            sweetAlertDialog.dismiss();
                        }).show();

            }
        });

        Bundle bundle = getArguments();
        if (bundle!=null) {
            index = bundle.getInt("index");
        }
        rgChange = content.findViewById(R.id.rg_change);
    return content;
    }

    List<Fragment> fragments;
    FragmentZushouNoIdent fragmentZushouNoIdent;
    FragmentZushou fragmentZushou;
    FragmentZushou1 fragmentZushou1;
    FragmentDealYuyue fragmentZushou2;
    FragmentNoYuyue fragmentZushou3;
    RadioGroup rgChange;

    public int getZs() {
        return zs;
    }

    public void setZs(int zs) {
        this.zs = zs;
        SharedPreferencesUtil.setZs(this.getActivity(),zs);
        SharedPreferencesUtil.addisMove(getActivity(),true);
        adapter.notifyDataSetChanged();
    }
    int zs;

    FragmentOrderListAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = new ArrayList();
        fragmentZushouNoIdent = new FragmentZushouNoIdent();
        fragmentZushou = new FragmentZushou();
        fragmentZushou1 = new FragmentZushou1();
        fragmentZushou2 = new FragmentDealYuyue();
        fragmentZushou3 = new FragmentNoYuyue();

        fragments.add(fragmentZushouNoIdent);
        fragments.add(fragmentZushou);
        fragments.add(fragmentZushou1);
        fragments.add(fragmentZushou2);
        fragments.add(fragmentZushou3);

        adapter = new FragmentOrderListAdapter(getActivity().getSupportFragmentManager(),fragments, new String[]{"未认证", "已上架", "已完成", "已预约","预约中"});
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);

        if (SharedPreferencesUtil.isMove(getActivity())){
            tableLayout.postDelayed(() -> {
                tableLayout.getTabAt(index).select();
                SharedPreferencesUtil.addisMove(getActivity(),false);
            }, 100);
        }

        rgChange.check(R.id.rt_all);
        rgChange.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.rt_zu:
                    setZs(0);
                    break;
                case R.id.rt_sale:
                    setZs(1);
                    break;

                case R.id.rt_all:
                    setZs(3);
                    break;
            }
        });

    }
}
