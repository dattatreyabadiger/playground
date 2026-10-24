package library_management.repository;

import library_management.model.book.Book;
import library_management.model.book.BookCopy;

import java.util.Set;

public interface InventoryRepository {

    Book searchBooks(String bookName);

    Set<BookCopy> getBookCopies(int bookId);

    BookCopy getBookCopy(int bookCopyId);

}
