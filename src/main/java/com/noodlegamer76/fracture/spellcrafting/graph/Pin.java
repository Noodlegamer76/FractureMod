package com.noodlegamer76.fracture.spellcrafting.graph;

public class Pin {
    private final int id;
    private final Node parent;
    private final PinType type;
    private final Class<?> clazz;

    private Connection connection;

    public Pin(int id, Node parent, PinType type, Class<?> clazz) {
        this.id = id;
        this.parent = parent;
        this.type = type;
        this.clazz = clazz;
    }

    public int id() {
        return id;
    }

    public Node parent() {
        return parent;
    }

    public PinType type() {
        return type;
    }

    public Class<?> clazz() {
        return clazz;
    }

    public Connection connection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

    void setConnection(Connection c) {
        this.connection = c;
    }

    void clearConnection() {
        this.connection = null;
    }

    @Override
    public String toString() {
        return "Pin{id=" + id + ", type=" + type + "}";
    }
}