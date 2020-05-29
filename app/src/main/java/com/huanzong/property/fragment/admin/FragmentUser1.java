package com.huanzong.property.fragment.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.SpacesItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentUser1 extends Fragment {
    RecyclerView rv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv.addItemDecoration(new SpacesItemDecoration(20));

        setListData();
    }

    public void setListData(){

        HttpServer.getAPIService().onUserList(0,0,"",0,0,0).enqueue(new Callback<DataBase<UserDataBase<UserData<User>>>>() {
            @Override
            public void onResponse(Call<DataBase<UserDataBase<UserData<User>>>> call, Response<DataBase<UserDataBase<UserData<User>>>> response) {
                Log.e("log","success "+response.body().getData());
                if (response.body().getCode()==1) {
                    List<User> list = response.body().getData().getUsers().getData();
                    rv.setAdapter(new UserAdapter(rv, list, R.layout.item_user));
                }
            }

            @Override
            public void onFailure(Call<DataBase<UserDataBase<UserData<User>>>> call, Throwable t) {
                Log.e("log","onFailure "+t.getMessage());
            }
        });


    }
}
