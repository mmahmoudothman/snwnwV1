package com.snwnw.snwnw.presentation.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by afaf.elshafey on 6/15/2016.
 */
public class English_EditText extends AppCompatEditText {
    public English_EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/JF_Flat_regular.ttf"));

    }
}
