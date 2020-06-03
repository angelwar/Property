package com.huanzong.property.fragment.admin;

import android.os.Bundle;
import android.os.Handler;
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
import com.huanzong.property.database.DataBase;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.PocketSwipeRefreshLayout;
import com.huanzong.property.util.SpacesItemDecoration;
import com.youth.xframe.adapter.XRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentUserNoIdent extends Fragment {
    RecyclerView rv;
    TextView tv_null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_list, container, false);
        initView(view);
        sw_sale = view.findViewById(R.id.sw_sale);
        sw_sale.setOnRefreshListener(() -> {
            page = 1;
            //清空数据再次刷新
            userAdapter.setDataLists(null);
            setListData();
        });
        return view;
    }
    PocketSwipeRefreshLayout sw_sale ;
    private UserAdapter userAdapter;
    private List<User> userList;
    int page = 1;
    int lastpage = 0;
    private void initView(View view) {
        rv = view.findViewById(R.id.rv_list);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv.addItemDecoration(new SpacesItemDecoration(20));

        tv_null = view.findViewById(R.id.tv_null);
        setListData();
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(getActivity(),rv,userList,R.layout.item_user);

        userAdapter.isLoadMore(true);//开启加载更多功能,默认关闭
        userAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {//加载失败，重新加载回调方法
                setListData();
            }
            @Override
            public void onLoadMore() {//加载更多回调方法
                if (page==lastpage){
                    //因直接userAdapter.showLoadComplete()，会显示报错，延时2秒再显示RecyclerView is computing a layout or scrolling
                    new Handler().postDelayed(() -> {
                        userAdapter.showLoadComplete();//没有更多数据了
                    }, 2000);
                }else {
                    setListData();
                }
            }
        });

        rv.setAdapter(userAdapter);
    }

    public void setListData(){
        HashMap<String,Integer> hashMap = new HashMap<>();
        //1认证 0未认证
        hashMap.put("ident",0);
        hashMap.put("page",page);
        //        HttpServer.getAPIService().onUserList(0,0,"",0,1,0)
        HttpServer.getAPIService().onUserList(hashMap).enqueue(new Callback<DataBase<UserDataBase<UserData<User>>>>() {
            @Override
            public void onResponse(Call<DataBase<UserDataBase<UserData<User>>>> call, Response<DataBase<UserDataBase<UserData<User>>>> response) {
                sw_sale.setRefreshing(false);
                if (response.body().getCode()==1) {
                    List<User> list = response.body().getData().getUsers().getData();
                    if (list.size()==0){
                        showNullView();return;
                    }
                    lastpage = response.body().getData().getUsers().getLast_page();
                    if (page < lastpage){
                        page = response.body().getData().getUsers().getCurrent_page()+1;
                    }
                    userAdapter.addAll(list);
                    hideNullView();
                }
            }

            @Override
            public void onFailure(Call<DataBase<UserDataBase<UserData<User>>>> call, Throwable t) {
                showNullView();
                sw_sale.setRefreshing(false);
            }
        });


    }

    private void showNullView(){
        rv.setVisibility(View.GONE);
        tv_null.setVisibility(View.VISIBLE);
    }

    private void hideNullView(){
        rv.setVisibility(View.VISIBLE);
        tv_null.setVisibility(View.GONE);
    }
}