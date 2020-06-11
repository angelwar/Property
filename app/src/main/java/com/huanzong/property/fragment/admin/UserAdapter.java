package com.huanzong.property.fragment.admin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.fragment.admin.data.User;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.TimeUtil;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends XRecyclerViewAdapter<User> {

    Context context;
    public UserAdapter(Context context, @NonNull RecyclerView mRecyclerView, List<User> dataLists, int layoutId) {
        super(mRecyclerView, dataLists, layoutId);
        this.context = context;
    }

    @Override
    protected void bindData(XViewHolder holder, User data, int position) {
        TextView tv = holder.itemView.findViewById(R.id.tv_user_name);
        tv.setText(data.getName());
        TextView community = holder.itemView.findViewById(R.id.tv_user_community);
        community.setText(data.getC_name());
        TextView foolor = holder.itemView.findViewById(R.id.tv_user_foolor);
        foolor.setText("楼栋："+data.getFoolor());
        TextView room = holder.itemView.findViewById(R.id.tv_user_room);
        room.setText("房号："+data.getRoom());
        TextView ident = holder.itemView.findViewById(R.id.tv_ident);
        ident.setText(data.getIdent()==0?"未认证":"已认证");
        TextView status = holder.itemView.findViewById(R.id.tv_status);
        status.setText("状态："+(data.getStatus()==0?"正常":"拉黑"));

        TextView role = holder.itemView.findViewById(R.id.tv_role);
        LinearLayout linearLayout = holder.itemView.findViewById(R.id.ll_time);
        String rolestr ="角色";
        switch (data.getRole()){
            case 3:rolestr = "租客";
            linearLayout.setVisibility(View.VISIBLE);
            break;
            case 1:rolestr = "业主";
                linearLayout.setVisibility(View.GONE);break;
            case 2:rolestr = "家属";
                linearLayout.setVisibility(View.GONE);break;
        }
        role.setText(rolestr);

        TextView bt_lahei = holder.itemView.findViewById(R.id.bt_lahei);
        TextView bt_ident = holder.itemView.findViewById(R.id.bt_ident);
        TextView bt_delete = holder.itemView.findViewById(R.id.bt_delete);

        bt_lahei.setOnClickListener(view -> {
            SweetAlertDialog a = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("你确定拉黑该用户吗？")
                    .setCancelText("取消拉黑")
                    .setConfirmText("确定拉黑")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            onUpdata(data.getId(),"status",0);
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            onUpdata(data.getId(),"status",1);
                            sDialog.dismiss();
                        }
                    });
                    a.show();
                    a.setCanceledOnTouchOutside(true);
        });
        bt_ident.setOnClickListener(view -> {
            SweetAlertDialog c = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("你确定认证该用户吗？")
                    .setCancelText("取消认证")
                    .setCancelClickListener(sweetAlertDialog -> {
                        onUpdata(data.getId(),"ident",0);
                        sweetAlertDialog.dismissWithAnimation();
                    })
                    .setConfirmText("确定认证")
                    .setConfirmClickListener(sDialog -> {
                        onUpdata(data.getId(),"ident",1);
                        sDialog.dismissWithAnimation();
                    });
                    c.show();
                    c.setCanceledOnTouchOutside(true);
        });
        bt_delete.setOnClickListener(view -> {
            SweetAlertDialog b = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("你确定删除该用户吗？")
                    .setCancelText("取消")
                    .setConfirmText("确定")
                    .setConfirmClickListener(sDialog -> {
                        onDeleteUser(data.getId());
                        sDialog.dismissWithAnimation();
                    });
                    b.show();
                    b.setCanceledOnTouchOutside(true);
        });

        TextView tv_phone = holder.itemView.findViewById(R.id.tv_phone);
        tv_phone.setText(data.getMobile());
        ImageView iv_call = holder.itemView.findViewById(R.id.iv_call);
        iv_call.setOnClickListener(v -> {
            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +data.getMobile()));//跳转到拨号界面，同时传递电话号码
            context.startActivity(dialIntent);
        });

        TextView tv_expire_time = holder.itemView.findViewById(R.id.tv_expire_time);
        tv_expire_time.setText(TimeUtil.timestampToDate(data.getExpire_time()));
    }

    private void onUpdata(int oid,String status,int v){
        HttpServer.getAPIService().upDataUserStatus(oid,status,v).enqueue(new Callback<DataBase<String>>() {
            @Override
            public void onResponse(Call<DataBase<String>> call, Response<DataBase<String>> response) {
                //认证了之后要刷新当前页面
                if (lin!=null){
                    lin.onRefresh();
                }
            }

            @Override
            public void onFailure(Call<DataBase<String>> call, Throwable t) {

            }
        });
    }

    private void onDeleteUser(int oid){
        HttpServer.getAPIService().deleteUser(oid).enqueue(new Callback<DataBase<String>>() {
            @Override
            public void onResponse(Call<DataBase<String>> call, Response<DataBase<String>> response) {
                //删除了之后要刷新当前页面
                if (lin!=null){
                    lin.onRefresh();
                }
            }

            @Override
            public void onFailure(Call<DataBase<String>> call, Throwable t) {

            }
        });
    }


    public interface onRefreshListener{
        void onRefresh();
    }

    private onRefreshListener lin;

    public void setLin(onRefreshListener lin) {
        this.lin = lin;
    }
}
