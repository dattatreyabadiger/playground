package product_search.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Keyword {

    private final String word;
    private final int count;

    public Keyword(String word, int count) {
        this.word = word;
        this.count = count;
    }
}
