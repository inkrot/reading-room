package ru.readingroom.system.models;

public class Book extends DataBaseEntity {

    private int quantity;
    private String name, author, year, isbn;

    public Book(int id, String name, String author, String year, String isbn, int quantity) {
        super(id);
        this.name = name;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
