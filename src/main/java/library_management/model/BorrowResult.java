package library_management.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BorrowResult {

    private final int bookCopyId;
    private final BorrowStatus borrowStatus;
    private final LocalDateTime dueAt;

    public BorrowResult(int bookCopyId, BorrowStatus borrowStatus, LocalDateTime dueAt) {
        this.bookCopyId = bookCopyId;
        this.borrowStatus = borrowStatus;
        this.dueAt = dueAt;
    }
}
