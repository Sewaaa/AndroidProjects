package com.example.doppiotris;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Tris tris1,tris2;
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

        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();

        tris1=new Tris("X","O",getApplicationContext());
        tris2=new Tris("O","X",getApplicationContext());

        tris1.setAltroTris(tris2);
        tris2.setAltroTris(tris1);

        tris1.setTurno(true);
        tris2.setTurno(false);

        ft.add(R.id.frame1, tris1,"");
        ft.add(R.id.frame2, tris2,"");

        ft.commit();
    }

    public void resetTris(View v){
        tris1.reset();
        tris2.reset();
        /*reimpostiamo i turni*/
        tris1.setTurno(true);
        tris2.setTurno(false);
    }
}