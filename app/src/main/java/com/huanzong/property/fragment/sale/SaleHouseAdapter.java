package com.huanzong.property.fragment.sale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.huanzong.property.activity.HouseDetailActivity;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.http.HttpServer;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleHouseAdapter extends XRecyclerViewAdapter<SaleData> {
    Context context;

    public SaleHouseAdapter(Context context,@NonNull RecyclerView mRecyclerView, List<SaleData> dataLists, int layoutId) {
        super(mRecyclerView, dataLists, layoutId);
        this.context = context;
    }

    @Override
    protected void bindData(XViewHolder holder, SaleData data, int position) {
        TextView price = holder.itemView.findViewById(R.id.tv_price);
        price.setText("价格："+data.getFy()+"元");
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



        TextView tv_phone = holder.itemView.findViewById(R.id.tv_phone);
        tv_phone.setText(data.getSjhm());

        TextView bt_ident = holder.itemView.findViewById(R.id.bt_ident);
        TextView bt_delete = holder.itemView.findViewById(R.id.bt_delete);

        bt_ident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog c = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("你确定通过该房屋审核吗？")
                        .setCancelText("取消")
                        .setCancelClickListener(sweetAlertDialog -> {
                            onUpdata(data.getId(),"ident",0);
                            sweetAlertDialog.dismissWithAnimation();
                        })
                        .setConfirmText("确定")
                        .setConfirmClickListener(sDialog -> {
                            onUpdata(data.getId(),"ident",1);
                            sDialog.dismissWithAnimation();
                        });
                c.show();
                c.setCanceledOnTouchOutside(true);
            }
        });
        bt_delete.setOnClickListener(view -> {
            SweetAlertDialog b = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("你确定删除该用户吗？")
                    .setCancelText("取消")
                    .setConfirmText("确定")
                    .setConfirmClickListener(sDialog -> {
                        onDeleteHouse(data.getId());
                        sDialog.dismissWithAnimation();
                    });
            b.show();
            b.setCanceledOnTouchOutside(true);
        });

        ImageView iv_call = holder.itemView.findViewById(R.id.iv_call);
        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +data.getSjhm()));//跳转到拨号界面，同时传递电话号码
                context.startActivity(dialIntent);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HouseDetailActivity.class);
            //传房屋id
            intent.putExtra("id",data.getId());
            context.startActivity(intent);
        });

    }


    private void onUpdata(int oid,String status,int v){
        HttpServer.getAPIService().upDataHouseStatus(oid,status,v).enqueue(new Callback<DataBase<String>>() {
            @Override
            public void onResponse(Call<DataBase<String>> call, Response<DataBase<String>> response) {

            }

            @Override
            public void onFailure(Call<DataBase<String>> call, Throwable t) {

            }
        });
    }

    private void onDeleteHouse(int id){
        HttpServer.getAPIService().deteleHouse(id).enqueue(new Callback<DataBase<String>>() {
            @Override
            public void onResponse(Call<DataBase<String>> call, Response<DataBase<String>> response) {

            }

            @Override
            public void onFailure(Call<DataBase<String>> call, Throwable t) {

            }
        });
    }
}
