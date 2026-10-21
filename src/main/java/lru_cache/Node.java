package lru_cache;

import lombok.Data;

@Data
public class Node {

    private Integer key;
    private String value;
    private Node next;
    private Node prev;

    public Node(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
