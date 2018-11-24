package com.snwnw.snwnw.presentation.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by afaf.elshafey on 6/15/2016.
 */
public class English_Textview extends AppCompatTextView {
    public English_Textview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/JF_Flat_regular.ttf"));
    }
}
