package product_search.model;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Product {

    private final int id;
    private final String name;
    private final String details;

    private static final AtomicInteger idGenerator = new AtomicInteger();

    public Product(String name, String details) {
        this.id = idGenerator.incrementAndGet();
        this.name = name;
        this.details = details;
    }
}
