package com.corrientazo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Drone implements Supplier<Drone> {

    private final Mapa mapa;
    private final List<Ruta> rutas;
    private final List<String> posiciones;

    public Drone(Mapa mapa) {
        this.mapa = mapa;
        this.rutas = Collections.emptyList();
        this.posiciones = Collections.emptyList();
    }

    public Drone(Mapa mapa, List<Ruta> rutas, List<String> posiciones) {
        this.mapa = mapa;
        this.rutas = Collections.unmodifiableList(rutas);
        this.posiciones = Collections.unmodifiableList(posiciones);
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public List<String> getPosiciones() {
        return posiciones;
    }

    public Drone addNewRuta(Ruta newRuta) {
        List<Ruta> r = new ArrayList<>(getRutas());
        r.add(newRuta);
        return new Drone(mapa, r, posiciones);
    }

    private String calculatePosition(List<Ruta> rutaList, String ruta) {
        return mapa.doTheMath(rutaList, ruta);
    }

    @Override
    public Drone get() {
        List<Ruta> oldR = rutas.stream()
                .filter(Ruta::isProcesada)
                .collect(Collectors.toList());

        if(oldR.size() == rutas.size())
            return this;

        List<Ruta> newR = rutas.stream()
                .filter(x -> !x.isProcesada())
                .map(Ruta::setProcesada)
                .collect(Collectors.toList());

        List<String> done = rutas.stream()
                .filter(x -> !x.isProcesada())
                .map(x -> calculatePosition(
                        rutas.stream()
                                .filter(f -> !f.isProcesada())
                                .collect(Collectors.toList()),
                        x.getRuta()))
                .collect(Collectors.toList());

        return new Drone(mapa,
                Stream.concat(oldR.stream(),newR.stream()).collect(Collectors.toList()),
                Stream.concat(posiciones.stream(),done.stream()).collect(Collectors.toList()));
    }
}
