package com.makestuffup.richtanner.tictacdoh;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private Button buttonReset;

    private boolean player1turn = true;

    private int roundCount;

    private int player1points;
    private int player2points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int redID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(redID);
                buttons[i][j].setOnClickListener(this);

            }
        }

        buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }

        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1turn) {
            ((Button) v).setText("X");
            v.setBackgroundResource(R.drawable.mario);
        } else {
            ((Button) v).setText("O");
            v.setBackgroundResource(R.drawable.wario);
        }

        roundCount++;

        if (checkForWin()) {
            if (player1turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1turn = !player1turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        // get the values
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // check for matches in columns
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        // check for matches in rows
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        // check for match diagonally \
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        // check for match diagonally /
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        // nobody won yet
        return false;
    }

    private void player1Wins() {
        player1points++;
        Toast.makeText(this, "Mario wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //do something
                resetBoard();
            }
        }, 1000);
    }

    private void player2Wins() {
        player2points++;
        Toast.makeText(this, "Wario wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //do something
                resetBoard();
            }
        }, 1000);

    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //do something
                resetBoard();
            }
        }, 1000);
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Mario: " + player1points);
        textViewPlayer2.setText("Wario: " + player2points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(buttonReset.getBackground());
            }
        }
        roundCount = 0;
        player1turn = true;
    }

    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1points", player1points);
        outState.putInt("player2points", player2points);
        outState.putBoolean("player1turn", player1turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1points");
        player2points = savedInstanceState.getInt("player2points");
        player1turn = savedInstanceState.getBoolean("player1turn");
    }
}
