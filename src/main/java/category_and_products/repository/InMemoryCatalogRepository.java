package category_and_products.repository;

import category_and_products.model.CatalogNode;
import category_and_products.model.Category;
import category_and_products.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InMemoryCatalogRepository implements CatalogRepository {

    private final Map<Integer, CatalogNode> catalog;

    public InMemoryCatalogRepository() {
        this.catalog = new HashMap<>();
    }

    @Override
    public void addNode(int parentId, CatalogNode node) {
        CatalogNode existingParentCatalogNode = catalog.get(parentId);
        if (existingParentCatalogNode == null) {
            catalog.put(node.getId(), node);
            // root itself is getting added
            return;
        }
        validateNodeAddition(node, existingParentCatalogNode);

        if (existingParentCatalogNode instanceof Category parentCategory) {
            parentCategory.add(node);
            catalog.put(node.getId(), node);
        }
    }

    private void validateNodeAddition(CatalogNode node, CatalogNode existingParentCatalogNode) {
        if (catalog.containsKey(node.getId()))
            throw new IllegalArgumentException("Node ID already exists: " + node.getId());

        if (existingParentCatalogNode instanceof Product)
            throw new IllegalArgumentException("Parent cannot be a product");
    }

    @Override
    public Set<Product> getProducts(int categoryId) {
        CatalogNode catalogNode = catalog.get(categoryId);
        if (catalogNode == null)
            return Set.of();

        Set<Product> res = new HashSet<>();
        collect(catalogNode, res);
        return res;
    }

    @Override
    public String breadcrumb(int categoryId) {
        CatalogNode node = catalog.get(categoryId);
        if (node == null)
            throw new IllegalArgumentException("Invalid categoryId");

        List<String> paths = new ArrayList<>();
        // find the root
        while (node != null) {
            paths.add(node.getName());
            node = node.getParent();
        }
        Collections.reverse(paths);
        return String.join(" -> ", paths);
    }

    private void collect(CatalogNode node, Set<Product> out) {
        if (node instanceof Product product) {
            out.add(product);
        } else {
            for (CatalogNode child : ((Category) node).getChildren())
                collect(child, out);
        }
    }

    private void dfs(CatalogNode node, List<String> path, List<List<String>> out) {
        if (node instanceof Product) {
            out.add(new ArrayList<>(path));
            return;
        }

        for (CatalogNode child : ((Category) node).getChildren()) {
            path.add(child.getName());
            dfs(child, path, out);
            path.remove(path.getLast());
        }
    }
}
