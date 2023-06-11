package org.raman.pojo;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private final List<Author> authors;

    public Book(List<Author> authors) {
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "authors=" + authors +
                '}';
    }
}
