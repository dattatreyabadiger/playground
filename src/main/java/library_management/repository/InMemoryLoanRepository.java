package library_management.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InMemoryLoanRepository implements LoanRepository {

    private final Map<Integer, Set<Integer>> memberToBorrowedBookCopies;

    public InMemoryLoanRepository() {
        this.memberToBorrowedBookCopies = new HashMap<>();
    }

    @Override
    public void addBookCopyToMember(int memberId, int bookCopyId) {
        memberToBorrowedBookCopies.computeIfAbsent(memberId, _ -> new HashSet<>())
                                  .add(bookCopyId);
    }

    @Override
    public void removeBookCopyFromMember(int memberId, int bookCopyId) {
        memberToBorrowedBookCopies.get(memberId)
                                  .remove(bookCopyId);
    }

    @Override
    public Set<Integer> getBorrowedBookCopies(int memberId) {
        return memberToBorrowedBookCopies.get(memberId);
    }

    @Override
    public int getBorrowedBookCopiesCount(int memberId) {
        Set<Integer> borrowedBookCopyIds = memberToBorrowedBookCopies.get(memberId);
        if (borrowedBookCopyIds == null)
            return 0;
        return borrowedBookCopyIds.size();
    }
}
