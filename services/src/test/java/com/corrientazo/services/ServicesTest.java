package com.corrientazo.services;

import com.corrientazo.domain.Drone;
import com.corrientazo.domain.Mapa;
import com.corrientazo.domain.Ruta;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class ServicesTest {

    @Test
    public void hacerEntregaTest() throws ExecutionException, InterruptedException {
        List<Drone> drones = new ArrayList<>();
        drones.add(new Drone(new Mapa(10)));
        drones.add(new Drone(new Mapa(10)));

        drones.set(0,drones.get(0).addNewRuta(new Ruta("AAAAIAA")));
        drones.set(0,drones.get(0).addNewRuta(new Ruta("DDDAIAD")));
        drones.set(0,drones.get(0).addNewRuta(new Ruta("AAIADAD")));

        assertEquals(drones.get(0).getRutas().size(),3);
        assertEquals(drones.get(0).getPosiciones().size(),0);

        drones.set(1,drones.get(1).addNewRuta(new Ruta("DAAAIAA")));
        drones.set(1,drones.get(1).addNewRuta(new Ruta("ADDAIAD")));
        drones.set(1,drones.get(1).addNewRuta(new Ruta("DAIADAD")));

        assertEquals(drones.get(1).getRutas().size(),3);
        assertEquals(drones.get(1).getPosiciones().size(),0);

        EntregaService entrega = new EntregaService(drones);
        entrega.hacerLasEntregas();

        assertEquals(entrega.getDrones().get(0).getRutas().size(),3);
        assertEquals(entrega.getDrones().get(0).getPosiciones().size(),3);

        assertEquals(entrega.getDrones().get(1).getRutas().size(),3);
        assertEquals(entrega.getDrones().get(1).getPosiciones().size(),3);
    }
}
