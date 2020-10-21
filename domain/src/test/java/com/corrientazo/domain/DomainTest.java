package com.corrientazo.domain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DomainTest {

    public static final String RUTA_1 = "AAAAIAA";
    public static final String RUTA_2 = "DDDAIAD";
    public static final String RUTA_3 = "AAIADAD";

    public static final String RUTA_A = "AAAADAI";
    public static final String RUTA_B = "AAAAAAD";

    public static final String RUTA_ERR_1 = "AAAAIAX";
    public static final String RUTA_ERR_2 = "DDDAIAAAAAAAAAAAAAAAAAAAAAAA";

    @Test
    public void calculateDestWithoutRutasTest() {
        Drone drone = new Drone(new Grid(10));
        assertEquals(drone.getRoutes().size(),0);
        assertEquals(drone.getPositions().size(),0);
        drone = drone.get();
        assertEquals(drone.getRoutes().size(),0);
        assertEquals(drone.getPositions().size(),0);
    }

    @Test
    public void calculateDestinationsTest() {
        Drone drone = new Drone(new Grid(10));
        drone = drone.addNewRoute(new Route(RUTA_1));
        drone = drone.addNewRoute(new Route(RUTA_2));
        drone = drone.addNewRoute(new Route(RUTA_3));

        assertEquals(drone.getRoutes().size(),3);
        assertEquals(drone.getPositions().size(),0);
        assertEquals(drone.getRoutes().get(0).getRoute(),RUTA_1);
        assertFalse(drone.getRoutes().get(0).isChecked());
        assertEquals(drone.getRoutes().get(1).getRoute(),RUTA_2);
        assertFalse(drone.getRoutes().get(1).isChecked());
        assertEquals(drone.getRoutes().get(2).getRoute(),RUTA_3);
        assertFalse(drone.getRoutes().get(2).isChecked());

        drone = drone.get();

        assertEquals(drone.getRoutes().size(),3);
        assertEquals(drone.getPositions().size(),3);
        assertEquals(drone.getRoutes().get(0).getRoute(),RUTA_1);
        assertTrue(drone.getRoutes().get(0).isChecked());
        assertEquals(drone.getRoutes().get(1).getRoute(),RUTA_2);
        assertTrue(drone.getRoutes().get(1).isChecked());
        assertEquals(drone.getRoutes().get(2).getRoute(),RUTA_3);
        assertTrue(drone.getRoutes().get(2).isChecked());

        assertEquals(drone.getPositions().get(0),"(-2,4) dirección Occidente");
        assertEquals(drone.getPositions().get(1),"(-1,3) dirección Sur");
        assertEquals(drone.getPositions().get(2),"(0,0) dirección Occidente");

        drone = drone.addNewRoute(new Route(RUTA_A));
        drone = drone.addNewRoute(new Route(RUTA_B));

        assertEquals(drone.getRoutes().size(),5);
        assertEquals(drone.getPositions().size(),3);

        assertEquals(drone.getRoutes().get(3).getRoute(),RUTA_A);
        assertFalse(drone.getRoutes().get(3).isChecked());
        assertEquals(drone.getRoutes().get(4).getRoute(),RUTA_B);
        assertFalse(drone.getRoutes().get(4).isChecked());

        drone = drone.get();

        assertEquals(drone.getRoutes().size(),5);
        assertEquals(drone.getPositions().size(),2);
    }

    @Test
    public void calculateDestinationsWithErrorsTest() {
        Drone drone = new Drone(new Grid(10));
        drone = drone.addNewRoute(new Route(RUTA_ERR_1));
        drone = drone.addNewRoute(new Route(RUTA_ERR_2));
        drone = drone.addNewRoute(new Route(RUTA_3));

        drone = drone.get();

        assertEquals(drone.getPositions().get(0),"La ruta AAAAIAX contiene un caracter inválido");
        assertEquals(drone.getPositions().get(1),"La ruta DDDAIAAAAAAAAAAAAAAAAAAAAAAA es inalcanzable, excede 10 cuadras");
        assertEquals(drone.getPositions().get(2),"La ruta AAIADAD no se puede procesar");
    }
}
