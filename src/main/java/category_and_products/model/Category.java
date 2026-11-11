package category_and_products.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Category extends CatalogNode {

    private final Set<CatalogNode> children = new HashSet<>();
    private NodeType childType;

    public Category(int id, String name) {
        super(id, name);
    }

    public void add(CatalogNode catalogNode) {
        validateAddition(catalogNode);
        setChildType(catalogNode);

        catalogNode.setParent(this);
        children.add(catalogNode);
    }

    public Set<CatalogNode> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    private void setChildType(CatalogNode catalogNode) {
        if (!children.isEmpty())
            return;
        this.childType = catalogNode instanceof Category ?
                NodeType.CATEGORY : NodeType.PRODUCTS;
    }

    private void validateAddition(CatalogNode node) {
        if (childType == null)
            return;

        if (childType == NodeType.CATEGORY)
            throw new IllegalArgumentException("Parent category already contains a category");

        if ((childType == NodeType.PRODUCTS && node instanceof Category))
            throw new IllegalArgumentException(
                    "Parent category already contains products; Cannot add a subcategory alongside products");
    }
}
