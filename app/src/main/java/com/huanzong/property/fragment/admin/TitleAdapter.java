package com.huanzong.property.fragment.admin;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TitleAdapter extends XRecyclerViewAdapter<String> {
    List<String> list ;

    LinearLayoutManager lin;
    public TitleAdapter(@NonNull RecyclerView mRecyclerView, List<String> dataLists) {
        super(mRecyclerView, dataLists);

    }

    public TitleAdapter(@NonNull RecyclerView mRecyclerView, List<String> dataLists, int layoutId) {
        super(mRecyclerView, dataLists, layoutId);
        list = dataLists;
    }

    public TitleAdapter(@NonNull RecyclerView mRecyclerView, List<String> dataLists, int layoutId, LinearLayoutManager lin) {
        super(mRecyclerView, dataLists, layoutId);
        list = dataLists;
        this.lin = lin;
    }

    @Override
    protected void bindData(XViewHolder holder, String data, int position) {
        TextView tv = holder.itemView.findViewById(R.id.tv_title);
        tv.setText(list.get(position));
    }

    @Override
    public void onBindViewHolder(XViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams parm = holder.itemView.getLayoutParams();
        parm.width = lin.getWidth()/list.size();
    }
}
