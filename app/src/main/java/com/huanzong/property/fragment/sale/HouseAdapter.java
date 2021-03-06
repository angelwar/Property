package com.huanzong.property.fragment.sale;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.http.HttpServer;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseAdapter extends XRecyclerViewAdapter<HouseData> {

    public HouseAdapter(@NonNull RecyclerView mRecyclerView, List<HouseData> dataLists) {
        super(mRecyclerView, dataLists);
    }

    public HouseAdapter(@NonNull RecyclerView mRecyclerView, List<HouseData> dataLists, int layoutId) {
        super(mRecyclerView, dataLists, layoutId);
    }

    @Override
    protected void bindData(XViewHolder holder, HouseData data, int position) {

        TextView tv = holder.itemView.findViewById(R.id.tv_title);
        tv.setText(data.getC_name());
    }

}
