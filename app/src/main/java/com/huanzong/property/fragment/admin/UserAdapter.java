package com.huanzong.property.fragment.admin;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huanzong.property.R;
import com.youth.xframe.adapter.XRecyclerViewAdapter;
import com.youth.xframe.adapter.XViewHolder;

import java.util.List;

public class UserAdapter extends XRecyclerViewAdapter<User> {

    public UserAdapter(@NonNull RecyclerView mRecyclerView, List<User> dataLists, int layoutId) {
        super(mRecyclerView, dataLists, layoutId);
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
        String rolestr ="角色";
        switch (data.getRole()){
            case 3:rolestr = "租客";break;
            case 1:rolestr = "业主";break;
            case 2:rolestr = "家属";break;
        }
        role.setText(rolestr);
    }
}
