package com.example.damianmichalak.saper;

import java.io.Serializable;

public class Field implements Serializable {

    private final int x;
    private final int y;
    private boolean marked;
    private boolean bomb;
    private boolean revealed;
    private int bombsNear;

    public int getBombsNear() {
        return bombsNear;
    }

    public void setBombsNear(int bombsNear) {
        this.bombsNear = bombsNear;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isBomb() {
        return bomb;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public Field(int x, int y, boolean marked, boolean bomb, boolean revealed) {
        this.x = x;
        this.y = y;
        this.marked = marked;

        this.bomb = bomb;
        this.revealed = revealed;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Field{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", marked=").append(marked);
        sb.append(", bomb=").append(bomb);
        sb.append(", revealed=").append(revealed);
        sb.append('}');
        return sb.toString();
    }
}
