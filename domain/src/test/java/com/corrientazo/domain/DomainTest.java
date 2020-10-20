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

    @Test
    public void calculateDestWithoutRutasTest() {
        Drone drone = new Drone(new Mapa(10));
        assertEquals(drone.getRutas().size(),0);
        assertEquals(drone.getPosiciones().size(),0);
        drone = drone.get();
        assertEquals(drone.getRutas().size(),0);
        assertEquals(drone.getPosiciones().size(),0);
    }

    @Test
    public void calculateDestinationsTest() {
        Drone drone = new Drone(new Mapa(10));
        drone = drone.addNewRuta(new Ruta(RUTA_1));
        drone = drone.addNewRuta(new Ruta(RUTA_2));
        drone = drone.addNewRuta(new Ruta(RUTA_3));

        assertEquals(drone.getRutas().size(),3);
        assertEquals(drone.getPosiciones().size(),0);
        assertEquals(drone.getRutas().get(0).getRuta(),RUTA_1);
        assertFalse(drone.getRutas().get(0).isProcesada());
        assertEquals(drone.getRutas().get(1).getRuta(),RUTA_2);
        assertFalse(drone.getRutas().get(1).isProcesada());
        assertEquals(drone.getRutas().get(2).getRuta(),RUTA_3);
        assertFalse(drone.getRutas().get(2).isProcesada());

        drone = drone.get();

        assertEquals(drone.getRutas().size(),3);
        assertEquals(drone.getPosiciones().size(),3);
        assertEquals(drone.getRutas().get(0).getRuta(),RUTA_1);
        assertTrue(drone.getRutas().get(0).isProcesada());
        assertEquals(drone.getRutas().get(1).getRuta(),RUTA_2);
        assertTrue(drone.getRutas().get(1).isProcesada());
        assertEquals(drone.getRutas().get(2).getRuta(),RUTA_3);
        assertTrue(drone.getRutas().get(2).isProcesada());

        assertEquals(drone.getPosiciones().get(0),"(-2,4) dirección Occidente");
        assertEquals(drone.getPosiciones().get(1),"(-1,3) dirección Sur");
        assertEquals(drone.getPosiciones().get(2),"(0,0) dirección Occidente");

        drone = drone.addNewRuta(new Ruta(RUTA_A));
        drone = drone.addNewRuta(new Ruta(RUTA_B));

        assertEquals(drone.getRutas().size(),5);
        assertEquals(drone.getPosiciones().size(),3);

        assertEquals(drone.getRutas().get(3).getRuta(),RUTA_A);
        assertFalse(drone.getRutas().get(3).isProcesada());
        assertEquals(drone.getRutas().get(4).getRuta(),RUTA_B);
        assertFalse(drone.getRutas().get(4).isProcesada());

        drone = drone.get();

        assertEquals(drone.getRutas().size(),5);
        assertEquals(drone.getPosiciones().size(),5);
    }
}
