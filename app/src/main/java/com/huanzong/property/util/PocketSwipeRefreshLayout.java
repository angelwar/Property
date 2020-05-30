package com.huanzong.property.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class PocketSwipeRefreshLayout extends SwipeRefreshLayout {
    //解决SwipeRefreshLayout 与viewpager滑动冲突问题
    private float mStartX = 0;
    private float mStartY = 0;

    //记录viewpager是否被拖拉
    private boolean mIsVpDrag;
    private int mTouchSlop;

    public PocketSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取滑动取值范围
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //记录手指按下的位置
                mStartX = ev.getX();
                mStartY = ev.getY();
                mIsVpDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //如果viewpager正在拖拽，则不拦截viewpager事件
                if (mIsVpDrag)
                    return false;
                float endX = ev.getY();
                float endY = ev.getX();
                float distanceX = Math.abs(endX - mStartX);
                float distanceY = Math.abs(endX - mStartY);
                //滑动x位移大于Y位移时，不拦截viewpager事件
                if (distanceX > mTouchSlop && distanceX >distanceY){
                    mIsVpDrag = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsVpDrag = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
