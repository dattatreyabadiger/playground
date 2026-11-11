package category_and_products.model;

import lombok.Getter;

@Getter
public abstract class CatalogNode {

    private final int id;
    private final String name;
    private Category parent;

    protected CatalogNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected void setParent(Category parent) {
        this.parent = parent;
    }
}
