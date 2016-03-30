package com.example.damianmichalak.saper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SaperField extends ImageView {

    private Point coords;

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public SaperField(Context context) {
        super(context);
    }

    public SaperField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SaperField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void update(Field field) {

        if (field.isMarked()) {
            setImageResource(R.drawable.ic_flag);
            return;
        } else {
            setImageResource(0);
        }

        if (field.isRevealed()) {
            setBackgroundColor(ActivityCompat.getColor(getContext(), R.color.colorAccent));

            if (field.isBomb()) {
                setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.ic_report));
            } else {
                switch (field.getBombsNear()) {
                    case 0:
                        setImageResource(0);
                        return;
                    case 1:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_1));
                        return;
                    case 2:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_2));
                        return;
                    case 3:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_3));
                        return;
                    case 4:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_4));
                        return;
                    case 5:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_5));
                        return;
                    case 6:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_6));
                        return;
                    case 7:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_7));
                        return;
                    case 8:
                        setImageDrawable(ActivityCompat.getDrawable(getContext(), R.drawable.number_8));
                }
            }
        }
    }
}
