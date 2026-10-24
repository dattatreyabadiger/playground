package library_management.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BorrowingPolicy {

    private int maxDays;
    private int maxBooks;
}
