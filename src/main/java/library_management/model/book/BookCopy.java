package library_management.model.book;

import library_management.model.ReturnStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class BookCopy {

    private int id;
    private String name;
    private BookCopyStatus status;
    private LocalDateTime dueDate;
    private int renewals;

    private static AtomicInteger bookCopyIdGenerator = new AtomicInteger();

    public BookCopy(String name) {
        this.id = bookCopyIdGenerator.incrementAndGet();
        this.name = name;
        this.status = BookCopyStatus.AVAILABLE;
    }

    public synchronized boolean borrowBookCopy(LocalDateTime dueDate) {
        if (this.status == BookCopyStatus.BORROWED)
            // borrowed book cannot be borrowed again
            return false;

        this.status = BookCopyStatus.BORROWED;
        this.dueDate = dueDate;
        this.renewals++;
        return true;
    }

    public synchronized String renewalBookCopy(LocalDateTime newDueDate, int renewalLimit) {
        if (this.status == BookCopyStatus.BORROWED && this.dueDate.isBefore(LocalDateTime.now()))
            // renewal window is closed
            return "BookCopy cannot be renewed as renewal window is closed";

        this.renewals++;
        if (renewals > renewalLimit)
            return "BookCopy cannot be renewed as renewal limit is crossed";

        this.status = BookCopyStatus.BORROWED;
        this.dueDate = newDueDate;
        return "BookCopy is renewed with newer due date";
    }

    public synchronized ReturnStatus releaseBook(LocalDateTime localDateTime) {
        if (this.status == BookCopyStatus.AVAILABLE)
            return ReturnStatus.NOT_BORROWED_BY_USER;

        // member to receive fine for crossing dueDate
        ReturnStatus respStatus = this.dueDate.isBefore(localDateTime) ?
                ReturnStatus.RETURNED_LATE : ReturnStatus.RETURNED;

        this.status = BookCopyStatus.AVAILABLE;
        this.dueDate = null;
        this.renewals = 0;
        return respStatus;
    }

}
