package com.hubwallet.apptspos.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SquireTextView extends AppCompatTextView {
    public SquireTextView(Context context) {
        super(context);
    }

    public SquireTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquireTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int measuredWidth = getMeasuredWidth();

            setMeasuredDimension(measuredWidth, measuredWidth);

    }
}
