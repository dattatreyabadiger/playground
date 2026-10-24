package library_management;

import library_management.model.BorrowStatus;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.member.Member;
import library_management.model.member.MemberType;

import java.time.LocalDateTime;

public interface ILibraryManagementService {

    Book searchBook(String bookName);

    BorrowStatus borrow(int memberId, String bookName);

    BorrowStatus borrow(int memberId, int bookId);

    ReturnStatus submit(int memberId, int bookCopyId, LocalDateTime localDateTime);

    Member createMember(MemberType memberType, String name, int contactNumber);
}
