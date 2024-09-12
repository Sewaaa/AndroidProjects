package com.example.quizmio;

public class Quesito {
    private String testo;
    private boolean risposta;
    private boolean gia_risposta;
    private boolean suggerimento_visto;

    public Quesito(String testo, boolean risposta) {
        this.testo = testo;
        this.risposta = risposta;
        this.gia_risposta = false;
        this.suggerimento_visto = false;
    }

    public void setGia_risposta(boolean gia_risposta) {
        this.gia_risposta = gia_risposta;
    }

    public boolean isSuggerimento_visto() {
        return suggerimento_visto;
    }

    public void setSuggerimento_visto(boolean suggerimento_visto) {
        this.suggerimento_visto = suggerimento_visto;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public boolean isRisposta() {
        return risposta;
    }

    public void setRisposta(boolean risposta) {
        this.risposta = risposta;
    }

    public boolean isGia_risposta() {
        return gia_risposta;
    }

    public void setGia_risposta() {
        this.gia_risposta = true;
    }
}

