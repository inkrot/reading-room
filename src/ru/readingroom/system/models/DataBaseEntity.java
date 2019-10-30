package ru.readingroom.system.models;

public class DataBaseEntity {

    private int id;

    public DataBaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
