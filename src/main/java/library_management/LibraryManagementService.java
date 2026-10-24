package library_management;

import library_management.model.BorrowStatus;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.book.BookCopy;
import library_management.model.member.Member;
import library_management.model.member.MemberType;
import library_management.repository.InventoryRepository;
import library_management.repository.BorrowingPolicyRepository;
import library_management.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LibraryManagementService implements ILibraryManagementService {

    private final InventoryRepository inventoryRepository;
    private final BorrowingPolicyRepository borrowingPolicyRepository;
    private final MemberRepository memberRepository;

    private final Map<Integer, Set<Integer>> memberToBorrowedBookCopies;


    public LibraryManagementService(InventoryRepository inventoryRepository,
                                    BorrowingPolicyRepository borrowingPolicyRepository,
                                    MemberRepository memberRepository) {
        this.inventoryRepository = inventoryRepository;
        this.borrowingPolicyRepository = borrowingPolicyRepository;
        this.memberRepository = memberRepository;

        this.memberToBorrowedBookCopies = new HashMap<>();
    }

    @Override
    public Book searchBook(String bookName) {
        return inventoryRepository.searchBooks(bookName);
    }

    @Override
    public BorrowStatus borrow(int memberId, String bookName) {
        Book book = searchBook(bookName);
        if (book == null)
            // book not available in library
            return BorrowStatus.BOOK_NOT_AVAILABLE;
        return borrow(memberId, book.getId());
    }

    @Override
    public BorrowStatus borrow(int memberId, int bookId) {
        Set<BookCopy> bookCopies = inventoryRepository.getBookCopies(bookId);
        if (bookCopies == null)
            // book not available in library
            return BorrowStatus.BOOK_NOT_AVAILABLE;

        Member member = memberRepository.getMember(memberId);
        if (member == null)
            return BorrowStatus.MEMBER_NOT_AVAILABLE;

        if (isMemberBorrowingBookLimitReached(memberId, member.getType()))
            return BorrowStatus.BORROW_LIMIT_EXCEEDED;

        BookCopy borrowedCopy = getBorrowedCopy(member, bookCopies);
        if (borrowedCopy == null)
            // bookCopy cannot be borrowed anymore
            return BorrowStatus.NO_AVAILABLE_COPY;

        memberToBorrowedBookCopies.computeIfAbsent(memberId, _ -> new HashSet<>())
                                  .add(borrowedCopy.getId());
        return BorrowStatus.BORROWED;
    }

    @Override
    public ReturnStatus submit(int memberId, int bookCopyId, LocalDateTime localDateTime) {
        Member member = memberRepository.getMember(memberId);
        if (member == null)
            return ReturnStatus.MEMBER_NOT_AVAILABLE;

        Set<Integer> borrowedBookCopies = memberToBorrowedBookCopies.get(memberId);
        if (borrowedBookCopies == null || !borrowedBookCopies.contains(bookCopyId))
            return ReturnStatus.NOT_BORROWED_BY_USER;

        BookCopy bookCopy = inventoryRepository.getBookCopy(bookCopyId);

        memberToBorrowedBookCopies.get(memberId).remove(bookCopyId);
        return bookCopy.releaseBook(localDateTime);
    }

    @Override
    public Member createMember(MemberType memberType, String name, int contactNumber) {
        Member member = new Member(name, memberType, contactNumber);
        memberRepository.addMember(member);
        return member;
    }

    private BookCopy getBorrowedCopy(Member member, Set<BookCopy> bookCopies) {
        int maxBorrowingDays = borrowingPolicyRepository.getMaxBorrowingDays(member.getType());
        for (BookCopy copy : bookCopies) {
            if (copy.borrowBookCopy(LocalDateTime.now()
                                                 .plusDays(maxBorrowingDays)))
                return copy;
        }
        return null;
    }

    private boolean isMemberBorrowingBookLimitReached(int memberId, MemberType memberType) {
        int maxBorrowingBooksCount = borrowingPolicyRepository.getMaxBorrowingBooksCount(memberType);
        return memberToBorrowedBookCopies.get(memberId) != null
                && memberToBorrowedBookCopies.get(memberId)
                                             .size() >= maxBorrowingBooksCount;
    }
}
