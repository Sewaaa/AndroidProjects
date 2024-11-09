package com.example.graficacanvas;

import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Pentagramma extends View {

    int w,h;
    int spazio=20;//spazio tra un rigo ed un altro
    int raggio_nota=spazio/2;//lunghezza del raggio del pallino della nota
    int lunghezza_nota=60; //lunghezza di default della stanchetta della nota

    ArrayList<Nota> listaNote=new ArrayList<>();
    public Pentagramma(Context context,int w,int h) {
        super(context);
        this.w=w;
        this.h=h;
        setMinimumHeight(h);
        setMinimumWidth(w);
    }


    protected void onDraw(Canvas canvas){
        Log.d("DEBUG", "onDraw, canvas.h="+canvas.getHeight()+" w: "+canvas.getWidth());
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);

        for(int i=0;i<5;i++){
            int y= 50+i*spazio;
            canvas.drawLine(0,y, w,y,paint);
        }

        for(int i=0; i<listaNote.size();i++) {
            Nota n=listaNote.get(i);
            int h=n.getH();
            int v=n.getV();
            canvas.drawCircle(h, v, raggio_nota, paint);
            canvas.drawLine(h + raggio_nota, v, h + raggio_nota, v - lunghezza_nota, paint);
        }
    }


    public class Nota{
        int h,v;

        public Nota(int h, int v) {
            this.h = h;
            this.v = v;
        }

        public int getH() {
            return h;
        }

        public int getV() {
            return v;
        }
    }

    public void inserisciNota(int w,int h){
        Nota n=new Nota(w,h);
        listaNote.add(n);
    }


}
