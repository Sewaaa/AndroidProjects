package com.example.slot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView[][] cells;
    private TextView textPunteggio;
    private int score = 0;
    private Random random;
    private int[][] valoriTabella; // Matrice per mantenere i valori della tabella

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        cells = new TextView[3][4];
        valoriTabella = new int[3][4]; // Inizializza la matrice dei valori

        // Troviamo le celle della tabella
        cells[0][0] = findViewById(R.id.cell_00);
        cells[0][1] = findViewById(R.id.cell_01);
        cells[0][2] = findViewById(R.id.cell_02);
        cells[0][3] = findViewById(R.id.cell_03);
        cells[1][0] = findViewById(R.id.cell_10);
        cells[1][1] = findViewById(R.id.cell_11);
        cells[1][2] = findViewById(R.id.cell_12);
        cells[1][3] = findViewById(R.id.cell_13);
        cells[2][0] = findViewById(R.id.cell_20);
        cells[2][1] = findViewById(R.id.cell_21);
        cells[2][2] = findViewById(R.id.cell_22);
        cells[2][3] = findViewById(R.id.cell_23);

        textPunteggio = findViewById(R.id.textPunteggio);

        // Inizializza le celle con "-"
        resettaCelle();

        // Bottone "Gioca"
        Button buttonGioca = findViewById(R.id.buttonGioca);
        buttonGioca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gioca();
            }
        });

        // Bottone "Azzera"
        Button buttonAzzera = findViewById(R.id.buttonAzzera);
        buttonAzzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                azzeraPunteggio();
            }
        });

        // Ripristina i dati se disponibili nel Bundle
        if (savedInstanceState != null) {
            ripristinaDati(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Salva il punteggio
        outState.putInt("score", score);

        // Salva la matrice dei valori della tabella
        outState.putSerializable("valoriTabella", valoriTabella);
    }

    private void ripristinaDati(Bundle savedInstanceState) {
        // Ripristina il punteggio
        score = savedInstanceState.getInt("score", 0);
        textPunteggio.setText("Punteggio: " + score);

        // Ripristina la matrice dei valori
        valoriTabella = (int[][]) savedInstanceState.getSerializable("valoriTabella");

        // Aggiorna le celle della tabella con i valori ripristinati
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                cells[i][j].setText(valoriTabella[i][j] == -1 ? "-" : String.valueOf(valoriTabella[i][j]));
            }
        }
    }

    private void gioca() {
        // Riempie la riga centrale con numeri casuali e aggiorna le altre righe
        for (int i = 0; i < 4; i++) {
            int numeroCentrale = random.nextInt(10);
            valoriTabella[1][i] = numeroCentrale;
            cells[1][i].setText(String.valueOf(numeroCentrale));

            // Calcola il valore per la prima riga con sottrazione ciclica
            int valorePrimaRiga = numeroCentrale == 0 ? 9 : numeroCentrale - 1;
            valoriTabella[0][i] = valorePrimaRiga;
            cells[0][i].setText(String.valueOf(valorePrimaRiga));

            // Calcola il valore per la terza riga con somma ciclica
            int valoreTerzaRiga = numeroCentrale == 9 ? 0 : numeroCentrale + 1;
            valoriTabella[2][i] = valoreTerzaRiga;
            cells[2][i].setText(String.valueOf(valoreTerzaRiga));
        }

        // Calcola il punteggio in base ai numeri uguali nella riga centrale
        calcolaPunteggio(valoriTabella[1]);
    }

    private void calcolaPunteggio(int[] rigaCentrale) {
        Map<Integer, Integer> frequenzaNumeri = new HashMap<>();

        // Conta la frequenza di ciascun numero nella riga centrale
        for (int num : rigaCentrale) {
            int count = frequenzaNumeri.getOrDefault(num, 0);
            frequenzaNumeri.put(num, count + 1);
        }

        // Determina il punteggio in base alla frequenza
        int puntiAggiunti = 0;
        for (int count : frequenzaNumeri.values()) {
            if (count == 2) {
                puntiAggiunti += 10;
                mostraToast("+10 punti");
            } else if (count == 3) {
                puntiAggiunti += 25;
                mostraToast("+25 punti");
            } else if (count == 4) {
                puntiAggiunti += 50;
                mostraToast("+50 punti");
            }
        }

        // Aggiorna il punteggio e visualizza il punteggio aggiornato
        score += puntiAggiunti;
        textPunteggio.setText("Punteggio: " + score);
    }

    private void mostraToast(String messaggio) {
        Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show();
    }

    private void azzeraPunteggio() {
        // Reset del punteggio
        score = 0;
        textPunteggio.setText("Punteggio: 0");

        // Reimposta tutte le celle a "-" e la matrice dei valori
        resettaCelle();
    }

    private void resettaCelle() {
        // Imposta tutte le celle con la stringa "-" e reimposta la matrice dei valori
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                cells[i][j].setText("-");
                valoriTabella[i][j] = -1; // Utilizziamo -1 per rappresentare una cella vuota
            }
        }
    }
}
