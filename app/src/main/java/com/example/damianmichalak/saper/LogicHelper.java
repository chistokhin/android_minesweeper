package com.example.damianmichalak.saper;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class LogicHelper implements View.OnClickListener, View.OnLongClickListener {

    private GameMode gameMode;

    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    private ViewListener listener;

    public LogicHelper(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void onClick(View v) {
        if (gameMode.getState() != GameMode.State.UNKNOWN) return;
        gameMode.setStep(gameMode.getStep() + 1);

        final Field field = getFieldFromPrefs((SaperField) v);

        if (field.isBomb() && gameMode.getStep() == 1) {
            if (gameMode.reload()) {
                gameMode.setStep(gameMode.getStep() - 1);
                onClick(v);
                return;
            }
        }

        if (!field.isMarked() && !field.isRevealed()) {
            if (!revealField(field)) {
                gameMode.setState(GameMode.State.FAIL);
                listener.gameFail();
            }
        }

        if (listener != null) {
            listener.update(gameMode.getFields());
        }

        checkAllBombsDetected();
    }

    @Override
    public boolean onLongClick(View v) {
        if (gameMode.getState() != GameMode.State.UNKNOWN) return true;

        final Field field = getFieldFromPrefs((SaperField) v);

        if (field.isRevealed()) {
            return true;
        } else {
            field.setMarked(!field.isMarked());
            gameMode.setMarkedFields(gameMode.getMarkedFields() + (field.isMarked() ? 1 : -1));
        }

        if (listener != null) {
            listener.update(gameMode.getFields());
        }

        checkAllBombsDetected();

        return true;
    }

    /**
     * @return true if field is NOT A BOMB
     */
    private boolean revealField(Field field) {
        if (field.isMarked()) return true;
        final int bombsNear = getNearBombs(field);
        field.setRevealed(true);
        field.setBombsNear(bombsNear);

        revealNearFields(field);

        return !field.isBomb();
    }

    private void checkAllBombsDetected() {
        boolean allBombsDetected = true;

        for (int i = 0; i < gameMode.getSize(); i++) {
            for (int j = 0; j < gameMode.getSize(); j++) {
                if (!gameMode.getFields()[i][j].isBomb() && !gameMode.getFields()[i][j].isRevealed()) {
                    allBombsDetected = false;
                    break;
                }
            }
        }

        if (allBombsDetected) {
            revealAllFields();
            gameMode.setState(GameMode.State.SUCCESS);
            listener.gameSuccess();
        }
    }

    private void revealAllFields() {
        for (int i = 0; i < gameMode.getSize(); i++) {
            for (int j = 0; j < gameMode.getSize(); j++) {
                revealField(gameMode.getFields()[i][j]);
            }
        }
        listener.update(gameMode.getFields());
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

        if (field.getX() - 1 >= 0 && field.getY() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX() - 1][field.getY() - 1]);

        if (field.getX() - 1 >= 0 && field.getY() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX() - 1][field.getY() + 1]);

        if (field.getX() + 1 < gameMode.getSize() && field.getY() - 1 >= 0)
            nears.add(gameMode.getFields()[field.getX() + 1][field.getY() - 1]);

        if (field.getX() + 1 < gameMode.getSize() && field.getY() + 1 < gameMode.getSize())
            nears.add(gameMode.getFields()[field.getX() + 1][field.getY() + 1]);

        for (Field f : nears) {
            if (!f.isRevealed() && !f.isMarked()) {
                revealField(f);
            }
        }


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
