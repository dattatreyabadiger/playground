package library_management;

import library_management.model.BorrowResult;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.member.Member;
import library_management.model.member.MemberType;

import java.time.LocalDateTime;

public interface ILibraryManagementFacade {

    Book searchBook(String bookName);

    BorrowResult borrowBook(int memberId, String bookName);

    BorrowResult borrowBook(int memberId, int bookId);

    ReturnStatus returnBook(int memberId, int bookCopyId, LocalDateTime localDateTime);

    Member createMember(MemberType memberType, String name, int contactNumber);
}
