package com.huanzong.property.fragment.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.huanzong.property.adapter.VisitorAdapter;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.database.Visitor;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.SpacesItemDecoration;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentVisitor extends Fragment {
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

        tv_null = view.findViewById(R.id.tv_null);
        setListData();
    }

    public void setListData(){

        HttpServer.getAPIService().onVisiter().enqueue(new Callback<DataBase<UserDataBase<UserData<Visitor>>>>() {
            @Override
            public void onResponse(Call<DataBase<UserDataBase<UserData<Visitor>>>> call, Response<DataBase<UserDataBase<UserData<Visitor>>>> response) {
                Log.e("log","success "+response.body().getData());
                if (response.body().getCode()==1) {
                    List<Visitor> list = response.body().getData().getUsers().getData();
                    if (list.size()==0){
                        showNullView();return;
                    }
                    rv.setAdapter(new VisitorAdapter(getActivity(),rv, list, R.layout.item_visitor));
                    hideNullView();
                }
            }

            @Override
            public void onFailure(Call<DataBase<UserDataBase<UserData<Visitor>>>> call, Throwable t) {
                Log.e("log","onFailure "+t.getMessage());
                showNullView();
            }
        });



    }
    TextView tv_null;
    private void showNullView(){
        rv.setVisibility(View.GONE);
        tv_null.setVisibility(View.VISIBLE);
    }

    private void hideNullView(){
        rv.setVisibility(View.VISIBLE);
        tv_null.setVisibility(View.GONE);
    }
}
