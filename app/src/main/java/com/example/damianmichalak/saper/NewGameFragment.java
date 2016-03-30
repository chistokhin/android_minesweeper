package com.example.damianmichalak.saper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewGameFragment extends Fragment {

    private TextView bombText;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("New game");

        bombText = (TextView) view.findViewById(R.id.bombs_text);
        bombText.setText("1");

        view.findViewById(R.id.new_game_easy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setTitle("small");
                startGame(5);
            }
        });

        view.findViewById(R.id.new_game_medium).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setTitle("medium");
                startGame(10);
            }
        });

        view.findViewById(R.id.new_game_hard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setTitle("big");
                startGame(15);
            }
        });

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

    }

    private void startGame(int size) {
        final int bombs = Integer.parseInt(bombText.getText().toString());
        GameMode mode = new GameMode(size, bombs > size * 2 ? size * 2 : bombs);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, GameFragment.newInstance(mode)).commit();
    }

    private void decreaseBombs() {
        int i = Integer.parseInt(bombText.getText().toString());
        if (i - 1 > 0) {
            i--;
        }
        bombText.setText(i + "");
    }

    private void increaseBombs() {
        int i = Integer.parseInt(bombText.getText().toString());
        if (i != 0) {
            i++;
        }
        bombText.setText(i + "");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }
}
