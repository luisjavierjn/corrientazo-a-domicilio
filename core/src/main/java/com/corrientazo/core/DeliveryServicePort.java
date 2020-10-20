package com.corrientazo.core;

import com.corrientazo.domain.Drone;
import com.corrientazo.domain.Route;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface DeliveryServicePort {
    List<Drone> getDrones();
    DeliveryServicePort setDrones(List<Drone> drones);
    void addNewRoute(int idxDrone, Route newRoute);
    void makeDeliveries() throws ExecutionException, InterruptedException;
}
