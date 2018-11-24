package com.snwnw.snwnw.presentation.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by afaf.elshafey on 6/15/2016.
 */
public class English_Button extends AppCompatButton {

    public English_Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/JF_Flat_regular.ttf"));
    }
}
