package com.huanzong.property.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.huanzong.property.R;
import com.huanzong.property.database.Community;
import com.huanzong.property.database.CommunityDataBase;

import java.util.List;

public class SelectCommunityAdapter extends BaseAdapter {
    private List<Community> mList;
    private Context mContext;

    public SelectCommunityAdapter(Context pContext, List<Community> listist) {
        this.mContext = pContext;
        this.mList = listist;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * 下面是重要代码
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.item_community_name_layout, null);
        if(convertView!=null) {
            TextView name=convertView.findViewById(R.id.tv_search_name);
            name.setText(mList.get(position).getName());
        }
        return convertView;
    }
}
