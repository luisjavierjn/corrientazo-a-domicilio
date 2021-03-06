package com.corrientazo.domain;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;

public final class Grid {

    private int blocks;
    private String err;

    private double r;
    private double d;
    private double i;
    private double j;
    private int X;
    private int Y;

    private static final String DIRECCION_NORTE = " dirección Norte"; // North i=0, j=1
    private static final String DIRECCION_SUR = " dirección Sur"; // South i=0, j=-1
    private static final String DIRECCION_ORIENTE = " dirección Oriente"; // East i=1, j=0
    private static final String DIRECCION_OCCIDENTE = " dirección Occidente"; // West i=-1, j=0

    BiFunction<Integer, Integer, String> coordenadas = (x ,y) -> String.format("(%s,%s)",x.toString(),y.toString());

    BiFunction<Double, Double, String> direccion = (i, j) -> {
        String retval = " dirección Desconocida";

        if(i==0 && j==1)
            retval = DIRECCION_NORTE;
        else if(i==0 && j==-1)
            retval = DIRECCION_SUR;
        else if(i==1 && j==0)
            retval = DIRECCION_ORIENTE;
        else if(i==-1 && j==0)
            retval = DIRECCION_OCCIDENTE;

        return retval;
    };

    DoubleBinaryOperator calculateXValue = (r, d) -> r * Math.round(Math.cos(Math.toRadians(d)));

    DoubleBinaryOperator calculateYValue = (r, d) -> r * Math.round(Math.sin(Math.toRadians(d)));

    public Grid(int blocks) {
        this.blocks = blocks;
    }

    public void init() {
        // coordenadas polares del vector de dirección
        r = 1;
        d = 90;

        // coordenadas cartesianas del vector de dirección
        i = calculateXValue.applyAsDouble(r, d);
        j = calculateYValue.applyAsDouble(r, d);

        // coordenadas cartesianas de posición del drone
        X = 0;
        Y = 0;
    }

    public String calculateDestination(List<Route> routes, String s) {
        init();
        err = "";
        for(Route r : routes) {
            if(apply(r.getRoute()) && !err.equals(""))
                err = "La ruta " + r.getRoute() + " no se puede procesar";

            if(r.getRoute().equals(s))
                break;
        }

        return err.equals("") ? coordenadas.apply(X,Y).concat(direccion.apply(i,j)) : err;
    }

    public boolean apply(String s) {
        char[] chars = s.toCharArray();
        for(char c : chars) {
            if(c == 'I') {
                d += 90;
                i = calculateXValue.applyAsDouble(r, d);
                j = calculateYValue.applyAsDouble(r, d);

            } else if(c == 'D') {
                d -= 90;
                i = calculateXValue.applyAsDouble(r, d);
                j = calculateYValue.applyAsDouble(r, d);

            } else if(c == 'A') {
                X += (int) i;
                Y += (int) j;

                if(Math.abs(X) > blocks || Math.abs(Y) > blocks) {
                    err = "La ruta " + s + " es inalcanzable, excede " + blocks + " cuadras" ;
                    init();
                    return false;
                }

            } else {
                err = "La ruta " + s + " contiene un caracter inválido";
                return false;
            }
        }
        return true;
    }

}
