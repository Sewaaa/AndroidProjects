package com.example.quizmio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvNumeroDomanda;
    TextView tvDomanda;
    TextView tvCorretteValide;
    TextView tvCorretteNonValide;
    TextView tvRisposteTotali;

    Quesito[] arrayQuesiti=new Quesito[]{
            new Quesito("1+1=2",true),
            new Quesito("1+1=3",false),
            new Quesito("1+2=3",true),
            new Quesito("3+2=5",true),
            new Quesito("3+2=6",false),
            new Quesito("5+2=8",false),
            new Quesito("5+2=7",true)

    };
    
    int quesito_corrente=0;
    int numero_totQuesiti=arrayQuesiti.length;
    int corretteValide=0,corretteNonValide=0,risposteTotali=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvNumeroDomanda=findViewById(R.id.numeroQuesito);
        tvDomanda=findViewById(R.id.quesito);
        tvCorretteValide=findViewById(R.id.corretteValide);
        tvCorretteNonValide=findViewById(R.id.corretteNonValide);
        tvRisposteTotali=findViewById(R.id.risposteTotali);

        tvNumeroDomanda.setText("Quesito n."+(quesito_corrente+1));
        tvDomanda.setText(arrayQuesiti[0].getTesto());
        tvCorretteValide.setText("Risposte corrette valide: "+corretteValide);
        tvCorretteNonValide.setText("Risposte corrette NON valide: "+corretteNonValide);
        tvRisposteTotali.setText("Risposte totali: "+risposteTotali);
    }
    /*PASSARE AL QUESITO SUCCESSIVO*/
    public void press_succ(View v){
        if(quesito_corrente+1 <= numero_totQuesiti-1 ) {
            quesito_corrente++;
            tvNumeroDomanda.setText("Quesito n." + (quesito_corrente + 1));
            tvDomanda.setText(arrayQuesiti[quesito_corrente].getTesto());
        }
    }
    /*TORNA A QUESITO PRECEDENTE*/
    public void press_prec(View v){
        if(quesito_corrente-1 >=0 ) {
            quesito_corrente--;
            tvNumeroDomanda.setText("Quesito n." + (quesito_corrente + 1));
            tvDomanda.setText(arrayQuesiti[quesito_corrente].getTesto());
        }
    }

    /*CLICCATO BOTTONE VERO*/
    public void press_vero(View v){
        if(arrayQuesiti[quesito_corrente].isGia_risposta())return;
        if(arrayQuesiti[quesito_corrente].isRisposta()) {
            if(arrayQuesiti[quesito_corrente].isSuggerimento_visto()){
                corretteNonValide++;
                tvCorretteNonValide.setText("Risposte corrette NON valide: " + corretteNonValide);
            }
            else {
                corretteValide++;
                tvCorretteValide.setText("Risposte corrette valide: " + corretteValide);
            }
        }
        risposteTotali++;
        tvRisposteTotali.setText("Risposte totali: "+risposteTotali);

        arrayQuesiti[quesito_corrente].setGia_risposta();
        press_succ(null);

    }
    /*CLICCATO BOTTONE FALSO*/
    public void press_falso(View v){
        if(arrayQuesiti[quesito_corrente].isGia_risposta())return;

        if(!arrayQuesiti[quesito_corrente].isRisposta()) {
            if (arrayQuesiti[quesito_corrente].isSuggerimento_visto()) {
                corretteNonValide++;
                tvCorretteNonValide.setText("Risposte corrette NON valide: " + corretteNonValide);
            } else {
                corretteValide++;
                tvCorretteValide.setText("Risposte corrette valide: " + corretteValide);
            }
        }
        risposteTotali++;
        tvRisposteTotali.setText("Risposte totali: "+risposteTotali);

        arrayQuesiti[quesito_corrente].setGia_risposta();
        press_succ(null);
    }

    public void press_suggerimento(View v){
        Intent i=new Intent();
        i.setClass(getApplicationContext(), SuggerimentoActivity.class);        //SETTIAMO activity da far partire
        i.putExtra("RISPOSTA", arrayQuesiti[quesito_corrente].isRisposta());       //passiamogli il quesito e la risposta
        i.putExtra("QUESITO", arrayQuesiti[quesito_corrente].getTesto());
        i.putExtra("NUMERO_QUESITO", quesito_corrente);

        startActivityForResult(i, 453); //facciamo partire l'activity settata (deve essere anche dichiarata nel manifest)
                                                  // 453 request code cioè identificativo della richiesta del risultato di ritorno
    }

    public void onActivityResult(int rc, int result, Intent i) {
        super.onActivityResult(rc, result, i);
        if (rc != 453) return;
        if (result != Activity.RESULT_OK) return; // il result deve essere un ok
        if (i == null) return;
        boolean visto = i.getBooleanExtra("SUGGERIMENTO_VISTO", false);
        arrayQuesiti[quesito_corrente].setSuggerimento_visto(visto);
    }

}