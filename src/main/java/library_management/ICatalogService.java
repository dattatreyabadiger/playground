package library_management;

import library_management.model.BorrowStatus;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.book.BookCopy;
import library_management.model.member.Member;
import library_management.model.member.MemberType;

import java.time.LocalDateTime;
import java.util.Set;

public interface ICatalogService {

    Book searchBook(String bookName);

    Set<BookCopy> getBookCopies(int bookId);

    BookCopy getBookCopy(int bookCopyId);
}
