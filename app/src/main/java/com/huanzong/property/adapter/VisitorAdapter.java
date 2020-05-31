package com.huanzong.property.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.database.Visitor;
import com.huanzong.property.http.HttpServer;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;
import com.youth.xframe.widget.XLoadingDialog;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitorAdapter extends XRecyclerViewAdapter<Visitor> {

    Context context;
    public VisitorAdapter(Context context, @NonNull RecyclerView mRecyclerView, List<Visitor> dataLists, int layoutId) {
        super(mRecyclerView, dataLists, layoutId);
        this.context = context;
    }

    @Override
    protected void bindData(XViewHolder holder, Visitor data, int position) {
        TextView tv = holder.itemView.findViewById(R.id.tv_user_name);
        tv.setText(data.getName());
        TextView phone = holder.itemView.findViewById(R.id.tv_phone);
        phone.setText(data.getMobile());
        TextView foolor = holder.itemView.findViewById(R.id.tv_user_foolor);
        foolor.setText("楼栋："+data.getFoolor());
        TextView room = holder.itemView.findViewById(R.id.tv_user_room);
        room.setText("房号："+data.getRoom());
        ImageView iv_call = holder.itemView.findViewById(R.id.iv_call);
        iv_call.setOnClickListener(v -> {
            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +data.getMobile()));//跳转到拨号界面，同时传递电话号码
            context.startActivity(dialIntent);
        });


    }


}
