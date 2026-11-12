package autocomplete.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrieNode {

    private final TrieNode[] children;
    private boolean endOfWord;

    public TrieNode() {
        children = new TrieNode[128];
        endOfWord = false;
    }

}
