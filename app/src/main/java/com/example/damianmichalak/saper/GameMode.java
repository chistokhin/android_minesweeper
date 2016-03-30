package com.example.damianmichalak.saper;

import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameMode implements Serializable {

    private final int size;

    public Field[][] getFields() {
        return fields;
    }

    private final int bombs;

    private Field[][] fields;

    public int getSize() {
        return size;
    }

    public int getBombs() {
        return bombs;
    }

    public GameMode(int size, int bombs) {
        this.size = size;
        this.bombs = bombs;
        fields = new Field[size][size];
        final Random r = new Random(System.currentTimeMillis());

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fields[i][j] = new Field(i, j, false, false, false);
            }
        }

        final List<Point> bombList = new ArrayList<>();

        for (int i = 0; i < bombs; ) {
            int x = r.nextInt(size);
            int y = r.nextInt(size);

            final Point point = new Point(x, y);
            if (!bombList.contains(point)) {
                bombList.add(point);
                fields[x][y] = new Field(x, y, false, true, false);
                i ++;
            }
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GameMode{");
        sb.append("size=").append(size);
        sb.append(", bombs=").append(bombs);
        sb.append(", fields=").append(fields == null ? "null" : Arrays.asList(fields).toString());
        sb.append('}');
        return sb.toString();
    }
}
