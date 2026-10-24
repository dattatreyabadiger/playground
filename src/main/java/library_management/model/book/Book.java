package library_management.model.book;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Book {

    private int id;
    private String name;

    private static AtomicInteger bookIdGenerator = new AtomicInteger();

    public Book(String name) {
        this.id = bookIdGenerator.incrementAndGet();
        this.name = name;
    }

}
