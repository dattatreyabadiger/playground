package category_and_products.repository;

import category_and_products.model.CatalogNode;
import category_and_products.model.Category;
import category_and_products.model.Product;

import java.util.ArrayList;
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
        catalog.put(node.getId(), node);
        if (existingParentCatalogNode == null) {
            // root itself is getting added
            return;
        }
        if (existingParentCatalogNode instanceof Category) {
            ((Category) existingParentCatalogNode).add(node);
        }
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
    public List<List<String>> breadcrumb(int categoryId) {
        CatalogNode node = catalog.get(categoryId);
        if (node == null)
            throw new IllegalArgumentException("Invalid categoryId");

        CatalogNode parent = node;
        // find the root
        while (node != null) {
            parent = node;
            node = node.getParent();
        }

        List<List<String>> res = new ArrayList<>();
        List<String> path = new ArrayList<>();
        path.add(parent.getName());
        dfs(parent, path, res);
        return res;
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
