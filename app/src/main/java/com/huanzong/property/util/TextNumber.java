package com.huanzong.property.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.huanzong.property.R;

public class TextNumber extends LinearLayout {
    private TextView tv;
    private TextView number;
    public TextNumber(Context context) {
        this(context,null);
    }

    public TextNumber(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.ui_text_number, this);
        tv = view.findViewById(R.id.tv_text);
        number = view.findViewById(R.id.tv_number);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextNumber);
        setTv(a.getString(R.styleable.TextNumber_text));
        setNumber(a.getInt(R.styleable.TextNumber_number,0));//默认为0
    }

    public void setTv(String tv) {
        this.tv.setText(tv);
    }

    public void setNumber(int number) {
        this.number.setText(""+number);
    }
}
