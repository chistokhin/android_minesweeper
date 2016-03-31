package com.example.damianmichalak.saper;

public interface ViewListener {

    void update(Field[][] fields);

    void gameFail();

    void gameSuccess();

    void timeChanged(final int i);
}
