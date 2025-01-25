package com.example.skusam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int player1Score = 501;
    private int player2Score = 501;

    private TextView remaining1, remaining2, suggestions1, suggestions2;
    private EditText input1, input2;
    private Spinner doubleSpinner1, doubleSpinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        remaining1 = findViewById(R.id.remaining1);
        remaining2 = findViewById(R.id.remaining2);
        suggestions1 = findViewById(R.id.suggestions1);
        suggestions2 = findViewById(R.id.suggestions2);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        doubleSpinner1 = findViewById(R.id.double_spinner1);
        doubleSpinner2 = findViewById(R.id.double_spinner2);
    }

    public void addScorePlayer1(View view) {
        updateScore(1);
    }

    public void addScorePlayer2(View view) {
        updateScore(2);
    }

    private void updateScore(int player) {
        try {
            int inputScore;
            Spinner doubleSpinner;
            TextView remaining;
            TextView suggestions;

            if (player == 1) {
                inputScore = Integer.parseInt(input1.getText().toString());
                doubleSpinner = doubleSpinner1;
                remaining = remaining1;
                suggestions = suggestions1;
            } else {
                inputScore = Integer.parseInt(input2.getText().toString());
                doubleSpinner = doubleSpinner2;
                remaining = remaining2;
                suggestions = suggestions2;
            }

            int remainingScore = player == 1 ? player1Score : player2Score;
            if (inputScore > remainingScore) {
                Toast.makeText(this, "Skóre nemôže byť väčšie ako zostávajúce skóre!", Toast.LENGTH_SHORT).show();
                return;
            }

            remainingScore -= inputScore;

            if (remainingScore == 0) {
                int doubleUsed = Integer.parseInt(doubleSpinner.getSelectedItem().toString());
                if (doubleUsed == 0 || (inputScore % 2 != 0)) {
                    Toast.makeText(this, "Posledná šípka musí byť double!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "Hráč " + player + " vyhral!", Toast.LENGTH_LONG).show();
                resetGame();
                return;
            }

            if (player == 1) {
                player1Score = remainingScore;
            } else {
                player2Score = remainingScore;
            }

            remaining.setText(String.valueOf(remainingScore));
            suggestions.setText(getSuggestions(remainingScore));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Zadajte platné číslo!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetGame() {
        player1Score = 501;
        player2Score = 501;

        remaining1.setText(String.valueOf(player1Score));
        remaining2.setText(String.valueOf(player2Score));

        suggestions1.setText("");
        suggestions2.setText("");

        input1.setText("");
        input2.setText("");
    }

    private String getSuggestions(int score) {
        Map<Integer, String> suggestionsMap = new HashMap<>();
        suggestionsMap.put(170, "T20, T20, Bull");
        suggestionsMap.put(167, "T20, T19, Bull");
        suggestionsMap.put(164, "T20, T18, Bull");
        suggestionsMap.put(161, "T20, T17, Bull");
        suggestionsMap.put(160, "T20, T20, D20");
        suggestionsMap.put(158, "T20, T20, D19");
        suggestionsMap.put(157, "T20, T19, D20");
        suggestionsMap.put(156, "T20, T20, D18");

        return suggestionsMap.getOrDefault(score, "Nie sú dostupné kombinácie.");
    }
}