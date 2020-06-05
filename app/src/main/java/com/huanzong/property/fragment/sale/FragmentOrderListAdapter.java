package com.huanzong.property.fragment.sale;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class FragmentOrderListAdapter extends FragmentStatePagerAdapter  {
    private List<Fragment> list;
    private String[] titles;
    private Context context;
    public FragmentOrderListAdapter(FragmentManager mFragmentManager,
                                    List fragmentList, String[] title) {
        super(mFragmentManager);
        list = fragmentList;
        titles=title;
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (i < list.size()) {
            fragment = list.get(i);
        } else {
            fragment = list.get(0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0)
            return titles[position];
        return null;
    }


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }
}