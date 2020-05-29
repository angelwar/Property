package com.huanzong.property.fragment.sale;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.List;

public class SaleHouseAdapter extends XRecyclerViewAdapter<SaleData> {


    public SaleHouseAdapter(@NonNull RecyclerView mRecyclerView, List<SaleData> dataLists, int layoutId) {
        super(mRecyclerView, dataLists, layoutId);
    }

    @Override
    protected void bindData(XViewHolder holder, SaleData data, int position) {
        TextView price = holder.itemView.findViewById(R.id.tv_price);
        price.setText("价格："+data.getFy());
   TextView xqmc = holder.itemView.findViewById(R.id.tv_user_community);
        xqmc.setText(data.getXqmc());
   TextView name = holder.itemView.findViewById(R.id.tv_user_name);
        name.setText(data.getLxrxm());

        TextView hx = holder.itemView.findViewById(R.id.tv_hx);
        hx.setText(data.getHx());
        TextView jzmj = holder.itemView.findViewById(R.id.tv_jzmj);
        jzmj.setText(data.getJzmj()+"㎡");
        TextView zx = holder.itemView.findViewById(R.id.tv_zx);
        if (!TextUtils.isEmpty(data.getZx())){
            zx.setVisibility(View.VISIBLE);
            zx.setText(data.getZx());
        }else {
            zx.setVisibility(View.GONE);
        }

    }

}
