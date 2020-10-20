package com.corrientazo.services;

import com.corrientazo.domain.Drone;
import com.corrientazo.domain.Ruta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EntregaService {
    private List<Drone> drones;

    public EntregaService(List<Drone> drones) {
        this.drones = drones;
    }

    public List<Drone> getDrones() {
        return drones;
    }

    public EntregaService setDrones(List<Drone> drones) {
        this.drones = drones;
        return this;
    }

    public void addNewRuta(int idxDrone, Ruta newRuta) {
        drones.set(idxDrone,drones.get(idxDrone).addNewRuta(newRuta));
    }

    public void hacerLasEntregas() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(drones.size());

        List<CompletableFuture<Drone>> completableFutures = new ArrayList<>();
        getDrones().forEach(drone -> completableFutures.add(CompletableFuture.supplyAsync(drone, pool)));

        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

        CompletableFuture<List<Drone>> allCompletableFuture = allFutures.thenApply(future -> completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));

        drones = allCompletableFuture.get();
        pool.shutdownNow();
        pool.awaitTermination(1, TimeUnit.MINUTES);
    }
}