package ru.readingroom.system.models;

public class Author extends DataBaseEntity {

    private String name;

    public Author(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName() + " (№" + getId() + ")";
    }
}
