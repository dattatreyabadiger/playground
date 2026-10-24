package library_management;

import library_management.model.BorrowResult;
import library_management.model.ReturnStatus;

import java.time.LocalDateTime;

public interface ICirculationService {

    BorrowResult borrowBook(int memberId, String bookName);

    BorrowResult borrowBook(int memberId, int bookId);

    ReturnStatus submitBook(int memberId, int bookCopyId, LocalDateTime localDateTime);
}
