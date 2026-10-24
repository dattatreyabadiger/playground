package library_management;

import library_management.model.book.Book;
import library_management.model.book.BookCopy;
import library_management.repository.InventoryRepository;

import java.util.Set;

public class CatalogService implements ICatalogService {

    private final InventoryRepository inventoryRepository;

    public CatalogService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Book searchBook(String bookName) {
        return inventoryRepository.searchBooks(bookName);
    }

    @Override
    public Set<BookCopy> getBookCopies(int bookId) {
        return inventoryRepository.getBookCopies(bookId);
    }

    @Override
    public BookCopy getBookCopy(int bookCopyId) {
        return inventoryRepository.getBookCopy(bookCopyId);
    }
}
