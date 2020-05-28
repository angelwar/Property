package com.huanzong.property.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.huanzong.property.R;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.fragment.first.TongJiData;
import com.huanzong.property.fragment.first.TongJiDataBase;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.TextNumber;
import com.youth.xframe.base.XFragment;
import com.youth.xframe.utils.permission.XPermission;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends XFragment implements View.OnClickListener {



    private void getTongjiData(){
        HttpServer.getAPIService().tongji("").enqueue(new Callback<DataBase<TongJiDataBase>>() {
            @Override
            public void onResponse(Call<DataBase<TongJiDataBase>> call, Response<DataBase<TongJiDataBase>> response) {
                if (response.body()!=null&&response.body().getCode()==1){
                    TongJiData tongji = response.body().getData().getData();
                    showData(tongji);
                }
            }

            @Override
            public void onFailure(Call<DataBase<TongJiDataBase>> call, Throwable t) {

            }
        });
    }

    private void showData(TongJiData tongji) {
        textNumber_0.setNumber(tongji.getYztj());
        textNumber_1.setNumber(tongji.getZktj());
        textNumber_2.setNumber(tongji.getFktj());
        textNumber_3.setNumber(tongji.getLhzh());
        textNumber_4.setNumber(tongji.getZstj());
        textNumber_5.setNumber(tongji.getYwcdd());
        textNumber_6.setNumber(tongji.getWyytj());
        textNumber_7.setNumber(tongji.getYyytj());

        tv_all.setText(tongji.getZrs()+"");
        wrzyh.setText("未认证用户("+tongji.getWrzyh()+")");
        wshfw.setText("未审核房屋("+tongji.getWshfw()+")");

        textNumber_0.setOnClickListener(this);
        textNumber_1.setOnClickListener(this);
        textNumber_2.setOnClickListener(this);
        textNumber_3.setOnClickListener(this);
        textNumber_4.setOnClickListener(this);
        textNumber_5.setOnClickListener(this);
        textNumber_6.setOnClickListener(this);
        textNumber_7.setOnClickListener(this);

        rel_all.setOnClickListener(this);
        wrzyh.setOnClickListener(this);
        wshfw.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private TextNumber textNumber_0,textNumber_1,textNumber_2,textNumber_3,
            textNumber_4,textNumber_5,textNumber_6,textNumber_7;
    private TextView tv_all,wrzyh,wshfw;
    private RelativeLayout rel_all;
    @Override
    public void initView() {
        textNumber_0 = getView().findViewById(R.id.tn_yezhu);
        textNumber_1 = getView().findViewById(R.id.tn_zuke);
        textNumber_2 = getView().findViewById(R.id.tn_fangke);
        textNumber_3 = getView().findViewById(R.id.tn_lahei);
        textNumber_4 = getView().findViewById(R.id.tn_zushou);
        textNumber_5 = getView().findViewById(R.id.tn_finish);
        textNumber_6 = getView().findViewById(R.id.tn_no_yuyue);
        textNumber_7 = getView().findViewById(R.id.tn_deal_yuyue);

        tv_all = getView().findViewById(R.id.tv_all);
        wrzyh = getView().findViewById(R.id.tv_wrzyh);
        wshfw = getView().findViewById(R.id.tv_wshfw);
        rel_all = getView().findViewById(R.id.rl_all);
        getTongjiData();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_all:
                break;
            case R.id.tn_yezhu:
                break;
            case R.id.tn_zuke:
                break;
            case R.id.tn_fangke:
                break;
            case R.id.tn_lahei:
                break;
            case R.id.tn_zushou:
                break;
            case R.id.tn_finish:
                break;
            case R.id.tn_no_yuyue:
                break;
            case R.id.tn_deal_yuyue:
                break;
            case R.id.tv_wrzyh:
                break;
            case R.id.tv_wshfw:
                break;

        }
    }
}
