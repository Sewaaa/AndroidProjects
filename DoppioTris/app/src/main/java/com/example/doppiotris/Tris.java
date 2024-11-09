package com.example.doppiotris;



import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class Tris extends Fragment implements View.OnClickListener {

    String simbolo,altroSimbolo;
    Tris altroTris;
    View v;
    Context c;  //per usare i toast qui invece che nel mainactivity
    boolean turno;
    public Tris() {}

    public Tris(String s, String as, Context c) {
        this.simbolo = s;
        this.altroSimbolo = as;
        this.c=c;
    }

    public void setTurno(boolean mode) {
        this.turno = mode;
    }

    public void setAltroTris(Tris altroTris) {
        this.altroTris = altroTris;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.tris, container, false);
        ArrayList<View> allButtons;
        allButtons = (v.findViewById(R.id.trisTable)).getTouchables();
        int i=1;
        for (View element: allButtons) {
            Button b = (Button) element;
            Log.d("DEBUG","Setting listener for button "+i);
            b.setOnClickListener(this);
            b.setId(i); //diamo un id ad ogni bottone per identificarli
            i++;
        }
        return v;
    }

    public void onClick(View v) {
        if (!turno) return;             //se non è il tuo turno non puoi giocare

        Button b = (Button) v;
        Log.d("DEBUG", "Hello button " + b.getText().toString());
        if(b.getText().toString().equals("-")){         //se il bottone non è vuoto non puoi modificarlo
            b.setText(simbolo);
            int id=b.getId();
            altroTris.aggiornaAltroTris(id);
            turno=false;
            altroTris.setTurno(true);                   //cambio turno
        }
        if(controlloVittoria()){
            Toast.makeText(c, "VITTORIA GIOCATORE "+simbolo, Toast.LENGTH_SHORT).show();
            reset();
            altroTris.reset();
        }
      }

    /*CONTROLLO VITTORIA*/
    private boolean controlloVittoria() {
        // Controlla le righe
        if (checkLine(1, 2, 3) ||
                checkLine(4, 5, 6) ||
                checkLine(7, 8, 9)) {
            return true;
        }

        // Controlla le colonne
        if (checkLine(1, 4, 7) ||
                checkLine(2, 5, 8) ||
                checkLine(3, 6, 9)) {
            return true;
        }

        // Controlla le diagonali
        if (checkLine(1, 5, 9) ||
                checkLine(3, 5, 7)) {
            return true;
        }

        return false;
    }

    private boolean checkLine(int a, int b, int c) {
        Button b1 = v.findViewById(a);
        Button b2 = v.findViewById(b);
        Button b3 = v.findViewById(c);

        return b1.getText().toString().equals(simbolo) &&
                b2.getText().toString().equals(simbolo) &&
                b3.getText().toString().equals(simbolo);
    }


    /*appena clicchi e fai la tua mossa viene aggiornato anche nell'altra tabella*/
    public void aggiornaAltroTris(int id){
        Button b=v.findViewById(id);
        b.setText(altroSimbolo);
    }

    public void reset(){
        /*Rimettiamo tutti i bottoni sul niente*/
        for(int id=1;id<=9;id++){
            Button b=v.findViewById(id);
            b.setText("-");
        }
    }
}
