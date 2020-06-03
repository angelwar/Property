package com.huanzong.property.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.huanzong.property.R;
import com.huanzong.property.util.SharedPreferencesUtil;

public class CenterFragment extends Fragment {

    TextView name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_main_3,null);
        name = content.findViewById(R.id.tv_community_name);
        String ss = SharedPreferencesUtil.queryCidsName(getActivity());
        name.setText(ss);
    return content;
    }
}
