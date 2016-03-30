package com.example.damianmichalak.saper;

import java.util.List;

public interface ViewListener {

    void update(Field[][] fields);

    void gameFail();

    void gameSuccess();
}
