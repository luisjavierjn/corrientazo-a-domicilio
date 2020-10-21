package com.corrientazo.services;

import com.corrientazo.domain.Drone;
import com.corrientazo.domain.Grid;
import com.corrientazo.domain.Route;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class DeliveryServiceAdapterTest {

    @Test
    public void hacerEntregaTest() throws ExecutionException, InterruptedException {
        List<Drone> drones = new ArrayList<>();
        drones.add(new Drone(new Grid(10)));
        drones.add(new Drone(new Grid(10)));

        DeliveryServiceAdapter entrega = new DeliveryServiceAdapter().setDrones(drones);

        entrega.addNewRoute(0,new Route("AAAAIAA"));
        entrega.addNewRoute(0,new Route("DDDAIAD"));
        entrega.addNewRoute(0,new Route("AAIADAD"));

        assertEquals(entrega.getDrones().get(0).getRoutes().size(),3);
        assertEquals(entrega.getDrones().get(0).getPositions().size(),0);

        entrega.addNewRoute(1,new Route("DAAAIAA"));
        entrega.addNewRoute(1,new Route("ADDAIAD"));
        entrega.addNewRoute(1,new Route("DAIADAD"));

        assertEquals(entrega.getDrones().get(1).getRoutes().size(),3);
        assertEquals(entrega.getDrones().get(1).getPositions().size(),0);

        entrega.makeDeliveries();

        assertEquals(entrega.getDrones().get(0).getRoutes().size(),3);
        assertEquals(entrega.getDrones().get(0).getPositions().size(),3);

        assertEquals(entrega.getDrones().get(1).getRoutes().size(),3);
        assertEquals(entrega.getDrones().get(1).getPositions().size(),3);
    }
}
