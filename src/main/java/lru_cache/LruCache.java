package lru_cache;

import java.util.HashMap;
import java.util.Map;

public class LruCache {

    private final Map<Integer, Node> cache;
    private final int capacity;
    private Node head;
    private Node tail;

    public LruCache(int capacity) {
        this.cache = new HashMap<>();
        this.capacity = capacity;
        head = new Node(-1, "");
        tail = new Node(-1, "");

        head.setNext(tail);
        tail.setPrev(head);
    }

    public String get(int key) {
        Node node = cache.get(key);
        if (node == null)
            return null;

        remove(node);
        addToHead(node);
        return node.getValue();
    }

    public void put(int key, String value) {
        Node node = cache.get(key);
        if (node != null) {
            node.setValue(value);
            remove(node);
            addToHead(node);
            return;
        }

        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        if (cache.size() > capacity) {
            removeTail();
        }
        addToHead(newNode);
    }

    public boolean remove(int key) {
        Node node = cache.get(key);
        if (node == null)
            return false;
        remove(node);
        cache.remove(key);
        return true;
    }

    private void remove(Node node) {
        Node next = node.getNext();
        Node prev = node.getPrev();
        next.setPrev(prev);
        prev.setNext(next);
    }

    private void addToHead(Node node) {
        Node currHead = head.getNext();
        head.setNext(node);

        node.setNext(currHead);
        node.setPrev(head);

        currHead.setPrev(node);
    }

    private void removeTail() {
        Node currTail = tail.getPrev();
        cache.remove(currTail.getKey());
        remove(currTail);
    }
}
