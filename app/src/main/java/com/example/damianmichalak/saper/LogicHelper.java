package com.example.damianmichalak.saper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class LogicHelper implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private GameMode gameMode;
    private final SharedPreferences preferences;

    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    private ViewListener listener;

    public LogicHelper(Activity context, GameMode gameMode) {
        this.context = context;
        this.gameMode = gameMode;
        preferences = context.getPreferences(Context.MODE_PRIVATE);
        Log.d("CHUJ", "" + gameMode.toString());
    }

    @Override
    public void onClick(View v) {
        final Field field = getFieldFromPrefs((SaperField) v);

        if (!field.isMarked() && !field.isRevealed()) {
            revealField(field);
        }

        if (listener != null) {
            listener.update(gameMode.getFields());
        }

    }

    private void revealField(Field field) {
        final int bombsNear = getNearBombs(field);
        field.setRevealed(true);
        field.setBombsNear(bombsNear);

        revealNearFields(field);
    }

    private void revealNearFields(Field field) {

        if (field.getBombsNear() > 0) return;

        final List<Field> nears = new ArrayList<>();

        if (field.getX() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX() - 1][field.getY()]);

        if (field.getY() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX()][field.getY() - 1]);

        if (field.getX() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX() + 1][field.getY()]);

        if (field.getY() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX()][field.getY() + 1]);

        for (Field f : nears) {
            if (!f.isRevealed() && !f.isMarked()) {
                revealField(f);
            }
        }


    }

    @Override
    public boolean onLongClick(View v) {
        final Field field = getFieldFromPrefs((SaperField) v);

        if (field.isRevealed()) {
            return true;
        } else {
            field.setMarked(!field.isMarked());
        }

        if (listener != null) {
            listener.update(gameMode.getFields());
        }

        return true;
    }

    private int getNearBombs(Field field) {
        final List<Boolean> nears = new ArrayList<>();

        if (field.getX() - 1 >= 0 && field.getY() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX() - 1][field.getY() - 1].isBomb());

        if (field.getX() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX() - 1][field.getY()].isBomb());

        if (field.getY() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX()][field.getY() - 1].isBomb());

        if (field.getX() - 1 >= 0 && field.getY() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX() - 1][field.getY() + 1].isBomb());

        if (field.getX() + 1 < gameMode.getSize() && field.getY() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX() + 1][field.getY() - 1].isBomb());

        if (field.getX() + 1 < gameMode.getSize() && field.getY() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX() + 1][field.getY() + 1].isBomb());

        if (field.getX() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX() + 1][field.getY()].isBomb());

        if (field.getY() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX()][field.getY() + 1].isBomb());

        int result = 0;

        for (Boolean b : nears) {
            if (b) {
                result++;
            }
        }

        return result;
    }

    private Field getFieldFromPrefs(SaperField saperField) {
        //TODO add prefs;

        final Field[][] fields = gameMode.getFields();
        return fields[saperField.getCoords().x][saperField.getCoords().y];

    }

}
