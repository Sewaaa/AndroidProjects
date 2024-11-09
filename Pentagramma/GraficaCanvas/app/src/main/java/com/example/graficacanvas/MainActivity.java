package com.example.graficacanvas;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    FrameLayout fl;

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

    
        Display display=getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Point size=new Point();
        display.getSize(size);
        int screen_w=size.x;
        int screen_h=size.y;
        int dp_w=(int) (screen_w/metrics.density);
        int dp_h=(int) (screen_h/metrics.density);

        fl=findViewById(R.id.contenitore);

        Pentagramma p = new Pentagramma(getApplicationContext(),screen_w,screen_h);
        p.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    int hor=(int) event.getX();
                    int ver=(int) event.getY();
                    Log.d("DEBUG", "Click h="+hor+" w="+ver);
                    p.inserisciNota(hor,ver);
                    p.invalidate(); //chiede di ridisegnare la view
                }

                return true;
            }
        });

        fl.addView(p);

    }
}