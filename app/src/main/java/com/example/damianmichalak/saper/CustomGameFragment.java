package com.example.damianmichalak.saper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomGameFragment extends Fragment {

    private TextView bombText;
    private TextView sizeText;

    public static Fragment newInstance() {
        return new CustomGameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("New custom game");

        bombText = (TextView) view.findViewById(R.id.bombs_text);
        sizeText = (TextView) view.findViewById(R.id.size_text);
        bombText.setText("1");
        sizeText.setText("5x5");

        view.findViewById(R.id.bombs_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseBombs();
            }
        });

        view.findViewById(R.id.bombs_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseBombs();
            }
        });

        view.findViewById(R.id.size_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseSize();
            }
        });

        view.findViewById(R.id.size_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseSize();
            }
        });

        view.findViewById(R.id.start_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bombs = getBombs();
                int size = getSize();
                ((MainActivity) getActivity()).startGame(size, bombs);
                getActivity().setTitle("custom game");
            }
        });

    }

    private void decreaseBombs() {
        int i = getBombs();
        if (i > 1) {
            i--;
        }
        setBombs(i);
    }

    private void increaseBombs() {
        int i = getBombs();
        int size = getSize();
        if (i + 1 < size * size) {
            i++;
        }
        setBombs(i);
    }

    private void decreaseSize() {
        int size = getSize();
        int bombs = getBombs();
        if (size > 4) {
            size--;
            if (bombs + 1 >= size * size) {
                setBombs((size * size) - 1);
            }
        }
        setSize(size);
    }

    private void increaseSize() {
        int i = getSize();
        if (i < 25) {
            i++;
        }
        setSize(i);
    }

    private int getSize() {
        final String text = sizeText.getText().toString();
        return Integer.parseInt(text.substring(0, text.indexOf("x")));
    }

    private int getBombs() {
        return Integer.parseInt(bombText.getText().toString());
    }

    private void setBombs(int size) {
        bombText.setText(size + "");
    }

    private void setSize(int size) {
        sizeText.setText(size + "x" + size);
    }
}
