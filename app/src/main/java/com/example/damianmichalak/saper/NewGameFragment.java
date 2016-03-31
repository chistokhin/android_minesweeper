package com.example.damianmichalak.saper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewGameFragment extends Fragment {

    private MainActivity activity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) getActivity();
        activity.setTitle("Saper");

        view.findViewById(R.id.new_game_easy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setTitle("easy game");
                activity.startGame(5, 5);
            }
        });

        view.findViewById(R.id.new_game_medium).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setTitle("medium game");
                activity.startGame(10, 15);
            }
        });

        view.findViewById(R.id.new_game_hard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setTitle("hard game");
                activity.startGame(20, 40);
            }
        });

        view.findViewById(R.id.new_game_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.customGame();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }
}
