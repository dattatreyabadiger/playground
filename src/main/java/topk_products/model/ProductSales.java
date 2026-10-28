package topk_products.model;

import lombok.Getter;

@Getter
public class ProductSales {

    private final int id;
    private int salesCount;

    public ProductSales(int id, int salesCount) {
        this.id = id;
        this.salesCount = salesCount;
    }

    public void incrementSales() {
        salesCount++;
    }
}
