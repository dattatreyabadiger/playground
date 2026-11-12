package autocomplete.repository;

import autocomplete.model.TrieNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InMemoryProductRepository implements ProductRepository {

    private final TrieNode trieNode;

    public InMemoryProductRepository() {
        trieNode = new TrieNode();
    }

    @Override
    public void insert(String productName) {
        TrieNode curr = trieNode;
        for (char c : productName.toCharArray()) {
            TrieNode child = curr.getChildren()[c];
            if (child == null) {
                curr.getChildren()[c] = new TrieNode();
            }
            curr = curr.getChildren()[c];
        }
        curr.setEndOfWord(true);
    }

    @Override
    public boolean isPresent(String productName) {
        TrieNode curr = trieNode;
        for (char c : productName.toCharArray()) {
            curr = curr.getChildren()[c];
            if (curr == null)
                return false;
        }
        return curr.isEndOfWord();
    }

    @Override
    public List<String> getSuggestions(String keyword) {
        List<String> res = new ArrayList<>();

        TrieNode curr = trieNode;
        for (char c : keyword.toCharArray()) {
            curr = curr.getChildren()[c];
            if (curr == null)
                return res;
        }

        getSuggestions(curr, new StringBuilder(keyword), res);
        return res;
    }

    private void getSuggestions(TrieNode node, StringBuilder prefix, List<String> res) {
        if (node.isEndOfWord())
            res.add(prefix.toString());

        for (int i = 0; i < node.getChildren().length; i++) {
            TrieNode child = node.getChildren()[i];
            if (child == null)
                continue;

            prefix.append((char) (i));
            getSuggestions(child, prefix, res);
            prefix.setLength(prefix.length() - 1);
        }
    }
}
