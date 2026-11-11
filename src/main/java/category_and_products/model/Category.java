package category_and_products.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class Category extends CatalogNode {

    private final Set<CatalogNode> children = new HashSet<>();

    public Category(int id, String name) {
        super(id, name);
    }

    public void add(CatalogNode catalogNode) {
        catalogNode.setParent(this);
        children.add(catalogNode);
    }
}
