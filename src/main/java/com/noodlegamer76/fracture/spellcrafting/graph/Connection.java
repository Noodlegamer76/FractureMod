package com.noodlegamer76.fracture.spellcrafting.graph;

public class Connection {
    private final Pin from;
    private final Pin to;

    public Connection(Pin from, Pin to) {
        this.from = from;
        this.to = to;
    }

    public Pin from() {
        return from;
    }

    public Pin to() {
        return to;
    }

    @Override
    public String toString() {
        return "Connection{from=" + from.id() + ", to=" + to.id() + "}";
    }
}