package com.example.damianmichalak.saper;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment implements ViewTreeObserver.OnGlobalLayoutListener, ViewListener {

    private GameMode gameMode;
    private LinearLayout rootView;
    private boolean done = false;
    private LogicHelper logicHelper;
    private List<SaperField> saperFields = new ArrayList<>();
    private TextView bombsLeft;
    private TextView time;

    public static GameFragment newInstance(GameMode mode) {

        final Bundle args = new Bundle();
        args.putSerializable("GAME_MODE", mode);

        final GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logicHelper.setListener(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.empty_container, container, false);
        bombsLeft = ((TextView) view.findViewById(R.id.bombs_left));
        time = ((TextView) view.findViewById(R.id.time));
        rootView = (LinearLayout) view.findViewById(R.id.root_view);
        gameMode = (GameMode) getArguments().getSerializable("GAME_MODE");
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        logicHelper = new LogicHelper(gameMode);
        logicHelper.setListener(this);
        return view;
    }

    private void addFields(LinearLayout rootView) {

        final int size = gameMode.getSize();

        final int width = rootView.getWidth();
        final int height = rootView.getHeight();
        final int boxSize = ((width > height ? height : width) / size) - 1;

        for (int i = 0; i < size; i++) {
            final LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            rootView.addView(linearLayout);

            for (int j = 0; j < size; j++) {
                final SaperField saperField = new SaperField(getActivity());
                saperField.setCoords(new Point(i, j));
                saperField.setBackgroundColor(Color.GREEN);
                saperField.setBackground(ActivityCompat.getDrawable(getActivity(), R.drawable.ripple));
                final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(j == size - 1 ? boxSize + 1 : boxSize, boxSize);
                params.bottomMargin = 1;
                params.rightMargin = j == size - 1 ? 0 : 1;
                saperField.setLayoutParams(params);
                saperField.setOnClickListener(logicHelper);
                saperField.setOnLongClickListener(logicHelper);

                linearLayout.addView(saperField);
                saperFields.add(saperField);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onGlobalLayout() {
        if (!done) {
            addFields(rootView);
            done = true;
            update(gameMode.getFields());
        }
    }

    @Override
    public void update(Field[][] fields) {
        bombsLeft.setText("" + gameMode.getNotMarkedBombs());
        for (int i = 0; i < gameMode.getSize(); i++) {
            for (int j = 0; j < gameMode.getSize(); j++) {
                saperFields.get(j + (i * gameMode.getSize())).update(fields[i][j]);
            }
        }
    }

    @Override
    public void gameFail() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Game over")
                .setMessage("You clicked on a bomb.")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((MainActivity) getActivity()).showNewGameFragment();
                    }
                });

        builder.show();
    }

    @Override
    public void gameSuccess() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("You win!")
                .setMessage("You have found all the bombs!")
                .setPositiveButton("I'm so happy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.show();

    }

    @Override
    public void timeChanged(final int i) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                time.setText("" + i);
            }
        });
    }
}
