package library_management.repository;


import java.util.Set;

public interface LoanRepository {

    void addBookCopyToMember(int memberId, int bookCopyId);

    void removeBookCopyFromMember(int memberId, int bookCopyId);

    Set<Integer> getBorrowedBookCopies(int memberId);

    int getBorrowedBookCopiesCount(int memberId);
}
