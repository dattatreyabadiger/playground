package cart_restriction_two.model;

import lombok.Getter;

@Getter
public class ResultSet {

    private final String message;

    public ResultSet(String message) {
        this.message = message;
    }
}
