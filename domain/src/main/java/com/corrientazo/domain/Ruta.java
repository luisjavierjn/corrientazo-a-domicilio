package com.corrientazo.domain;

public final class Ruta {
    private final String ruta;
    private final boolean procesada;

    public Ruta(String ruta) {
        this.ruta = ruta;
        this.procesada = false;
    }

    private Ruta(String ruta, boolean procesada) {
        this.ruta = ruta;
        this.procesada = procesada;
    }

    public String getRuta() {
        return ruta;
    }

    public boolean isProcesada() {
        return procesada;
    }

    public Ruta setProcesada() {
        return new Ruta(ruta, true);
    }
}
