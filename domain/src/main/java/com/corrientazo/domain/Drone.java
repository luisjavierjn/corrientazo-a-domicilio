package com.corrientazo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Drone implements Supplier<Drone> {

    private final Grid grid;
    private final List<Route> routes;
    private final List<String> positions;

    public Drone(Grid grid) {
        this.grid = grid;
        this.routes = Collections.emptyList();
        this.positions = Collections.emptyList();
    }

    public Drone(Grid grid, List<Route> routes, List<String> positions) {
        this.grid = grid;
        this.routes = Collections.unmodifiableList(routes);
        this.positions = Collections.unmodifiableList(positions);
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<String> getPositions() {
        return positions;
    }

    public Drone addNewRoute(Route newRoute) {
        List<Route> r = new ArrayList<>(getRoutes());
        r.add(newRoute);
        return new Drone(grid, r, positions);
    }

    private String calculatePosition(List<Route> routeList, String ruta) {
        return grid.calculateDestination(routeList, ruta);
    }

    @Override
    public Drone get() {
        List<Route> oldR = routes.stream()
                .filter(Route::isChecked)
                .collect(Collectors.toList());

        if(oldR.size() == routes.size())
            return this;

        List<Route> newR = routes.stream()
                .filter(x -> !x.isChecked())
                .map(Route::check)
                .collect(Collectors.toList());

        List<String> done = routes.stream()
                .filter(x -> !x.isChecked())
                .map(x -> calculatePosition(
                        routes.stream()
                                .filter(f -> !f.isChecked())
                                .collect(Collectors.toList()),
                        x.getRoute()))
                .collect(Collectors.toList());

        return new Drone(grid,
                Stream.concat(oldR.stream(),newR.stream()).collect(Collectors.toList()),
                Stream.concat(positions.stream(),done.stream()).collect(Collectors.toList()));
    }
}
