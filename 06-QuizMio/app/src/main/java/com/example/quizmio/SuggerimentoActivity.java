package com.example.quizmio;

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

public class SuggerimentoActivity extends AppCompatActivity {
    TextView tvnumeroQuesito;
    TextView tvquesito;
    TextView tvsuggerimento;
    boolean risposta;
    String quesito;
    int numero_quesito;
    Intent i=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggerimento);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sugg), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*SETTIAMO CHE IL SUGGERIMENTO ANCORA NON è STATO VISTO*/
        i.putExtra("SUGGERIMENTO_VISTO",false);
        setResult(RESULT_OK,i);

        tvquesito=findViewById(R.id.quesito);
        tvnumeroQuesito=findViewById(R.id.numeroQuesito);
        tvsuggerimento=findViewById(R.id.suggerimento);

        tvsuggerimento.setText("Suggerimento: #####");

        Intent i=getIntent();
        risposta=i.getBooleanExtra("RISPOSTA",false) ; //il secondo è un valore di deafult se non trova il valore passato
        quesito=i.getStringExtra("QUESTIO");
        numero_quesito=i.getIntExtra("NUMERO_QUESITO", 0);

        tvnumeroQuesito.setText("Quesito n."+(numero_quesito+1));
        tvquesito.setText(quesito);
    }

    public  void confermaSuggerimento(View v){
        if(risposta) tvsuggerimento.setText("Suggerimento: Vero");
        else tvsuggerimento.setText("Suggerimento: Falso");
        /*Risultato di ritorno per dire che il suggerimento è stato visto*/
        i.putExtra("SUGGERIMENTO_VISTO",true);
        Log.d("MYQUIZ","visto");
        setResult(RESULT_OK,i);
    }

    public void tornaIndietro(View v){
        getOnBackPressedDispatcher().onBackPressed();
    }

}
