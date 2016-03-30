package com.example.damianmichalak.saper;

import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LayoutHelper {

    public static LinearLayout.LayoutParams wrapContent() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams matchParent() {
        return new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }
}
