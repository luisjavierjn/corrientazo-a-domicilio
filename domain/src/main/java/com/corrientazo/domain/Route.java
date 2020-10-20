package com.corrientazo.domain;

public final class Route {
    private final String route;
    private final boolean checked;

    public Route(String route) {
        this.route = route;
        this.checked = false;
    }

    private Route(String route, boolean checked) {
        this.route = route;
        this.checked = checked;
    }

    public String getRoute() {
        return route;
    }

    public boolean isChecked() {
        return checked;
    }

    public Route check() {
        return new Route(route, true);
    }
}
