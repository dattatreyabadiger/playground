package library_management.repository;

import library_management.model.book.Book;
import library_management.model.book.BookCopy;

import java.util.Map;
import java.util.Set;

public class InMemoryInventoryRepository implements InventoryRepository {

    private final Map<String, Book> bookNameToDetailsMap;
    private final Map<Integer, Set<BookCopy>> bookIdToCopiesMap;
    private final Map<Integer, BookCopy> bookCopyIdToCopiesMap;

    public InMemoryInventoryRepository() {
        // add books
        Book book1 = new Book("abc");
        Book book2 = new Book("xyz");
        Book book3 = new Book("def");

        bookNameToDetailsMap = Map.of("abc", book1,
                                      "xyz", book2,
                                      "def", book3);

        // add bookCopies
        BookCopy book1Copy1 = new BookCopy("abc");
        BookCopy book1Copy2 = new BookCopy("abc");
        BookCopy book2Copy1 = new BookCopy("xyz");
        BookCopy book2Copy2 = new BookCopy("xyz");
        BookCopy book3Copy1 = new BookCopy("def");

        bookIdToCopiesMap = Map.of(book1.getId(), Set.of(book1Copy1, book1Copy2),
                                   book2.getId(), Set.of(book2Copy1, book2Copy2),
                                   book3.getId(), Set.of(book3Copy1));

        bookCopyIdToCopiesMap = Map.of(book1Copy1.getId(), book1Copy1,
                                       book1Copy2.getId(), book1Copy2,
                                       book2Copy1.getId(), book2Copy1,
                                       book2Copy2.getId(), book2Copy2,
                                       book3Copy1.getId(), book3Copy1);
    }

    @Override
    public Book searchBooks(String bookName) {
        return bookNameToDetailsMap.get(bookName);
    }

    @Override
    public Set<BookCopy> getBookCopies(int bookId) {
        return bookIdToCopiesMap.get(bookId);
    }

    @Override
    public BookCopy getBookCopy(int bookCopyId) {
        return bookCopyIdToCopiesMap.get(bookCopyId);
    }
}
