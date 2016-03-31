package com.example.damianmichalak.saper;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);

        if (savedInstanceState == null) {
            showNewGameFragment();
        }

        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.close(true);
            }
        });

        findViewById(R.id.fab_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fab.close(false);
                        finish();
                    }
                });
                builder.setNegativeButton("no", null);
                builder.show();
            }
        });

        findViewById(R.id.fab_new_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.close(true);
                showNewGameFragment();
            }
        });

    }

    @Override
    public void onBackPressed() {
        final Fragment fragment = getSupportFragmentManager().getFragments().get(0);
        if (fragment instanceof CustomGameFragment) {
            showNewGameFragment();
        } else if (fragment instanceof GameFragment) {
            showNewGameFragment();
        } else {
            super.onBackPressed();
        }
    }

    public int showNewGameFragment() {
        return getSupportFragmentManager().beginTransaction().replace(R.id.container, new NewGameFragment()).commit();
    }

    public void startGame(int size, int bombs) {
        final GameMode mode = new GameMode(size, bombs);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, GameFragment.newInstance(mode)).commit();
    }

    public void customGame() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, CustomGameFragment.newInstance()).commit();
    }
}
