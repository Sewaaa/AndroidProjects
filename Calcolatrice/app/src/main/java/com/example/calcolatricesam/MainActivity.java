package com.example.calcolatricesam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView TVdisplayPrincipale,TVdisplaySecondario,TVdisplayOperatore,TVdisplayMemoria;
    Button buttonc;
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
        TVdisplayPrincipale=findViewById(R.id.displayPrincipale);
        TVdisplaySecondario=findViewById(R.id.displaySecondario);
        TVdisplayOperatore=findViewById(R.id.displayOperatore);
        TVdisplayMemoria=findViewById(R.id.displayMemoria);

        /*Recupero instanze salvate dopo un destroy*/
        if(savedInstanceState!=null){
            TVdisplayPrincipale.setText(savedInstanceState.getString("displayPrincipale"));
            TVdisplayOperatore.setText(savedInstanceState.getString("displayOperatore"));
            TVdisplaySecondario.setText(savedInstanceState.getString("displaySecondario"));
            TVdisplayMemoria.setText(savedInstanceState.getString("displayMemoria"));
        }

        /*Pressione prolungata del bottone C (canc)*/
        buttonc=findViewById(R.id.buttonc);
        buttonc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TVdisplayPrincipale.setText("");
                TVdisplaySecondario.setText("");
                TVdisplayOperatore.setText("");
                return false;
            }
        });
    }

   /*SALVO LO STATO IN CASO DI DESTROY (esempio in caso di rotazione)*/
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putString("displayPrincipale",(String) TVdisplayPrincipale.getText());
        savedInstanceState.putString("displayOperatore",(String) TVdisplayOperatore.getText());
        savedInstanceState.putString("displaySecondario",(String) TVdisplaySecondario.getText());
        savedInstanceState.putString("displayMemoria",(String) TVdisplayMemoria.getText());

        super.onSaveInstanceState(savedInstanceState);
    }




    public void NumberPress(View v){
        Button b=(Button) v;
        TVdisplayPrincipale.setText(TVdisplayPrincipale.getText().toString()+b.getText());
    }

    public void OperatorPress(View v){
        Button b=(Button) v;
        String op1= (String) TVdisplayPrincipale.getText();
        if(op1.isEmpty()) {
            Toast.makeText(this, "Operatore nullo", Toast.LENGTH_SHORT).show();
            return;
        }
        TVdisplayOperatore.setText(b.getText());
        TVdisplaySecondario.setText(TVdisplayPrincipale.getText());
        TVdisplayPrincipale.setText("");
    }

    public void EqualPress(View v){
        Double op1= Double.parseDouble(TVdisplaySecondario.getText().toString());
        if(TVdisplayPrincipale.getText().toString().isEmpty()){
            Toast.makeText(this, "Operatore nullo", Toast.LENGTH_SHORT).show();
            return;
        }
        Double op2= Double.parseDouble(TVdisplayPrincipale.getText().toString());
        char op= TVdisplayOperatore.getText().toString().charAt(0);
        double res=0.00;
        switch (op){
            case '+': res=op1+op2;
                break;
            case '-': res=op1-op2;
                break;
            case '*': res=op1*op2;
                break;
            case '/':
                    if(op2==0){
                        Toast.makeText(this, "Divisione per 0 non ammessa", Toast.LENGTH_SHORT).show();
                        return;
                    }
                res=op1/op2;
                break;
        }
        TVdisplayPrincipale.setText(""+res);
        TVdisplaySecondario.setText("");
        TVdisplayOperatore.setText("");
        TVdisplayMemoria.setText("");
    }

    
    public void clearPress(View v){
        String str=TVdisplayPrincipale.getText().toString();
        if (str.length()>0) {
            TVdisplayPrincipale.setText(str.substring(0, str.length() - 1));
        }
    }



}