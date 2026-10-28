package topk_products.model;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Product {

    private final int id;
    private final String name;
    private final int price;

    private final static AtomicInteger idGenerator = new AtomicInteger();

    public Product(String name, int price) {
        this.id = idGenerator.incrementAndGet();
        this.name = name;
        this.price = price;
    }
}
