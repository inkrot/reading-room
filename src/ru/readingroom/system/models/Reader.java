package ru.readingroom.system.models;

public class Reader extends DataBaseEntity {

    private String name;

    public Reader(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
