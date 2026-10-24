package library_management;

import library_management.model.BorrowResult;
import library_management.model.BorrowStatus;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.book.BookCopy;
import library_management.model.member.Member;
import library_management.model.member.MemberType;
import library_management.repository.BorrowingPolicyRepository;
import library_management.repository.LoanRepository;

import java.time.LocalDateTime;
import java.util.Set;

public class CirculationService implements ICirculationService {

    private final ICatalogService catalogService;
    private final IMemberService memberService;

    private final LoanRepository loanRepository;
    private final BorrowingPolicyRepository borrowingPolicyRepository;


    public CirculationService(ICatalogService catalogService,
                              IMemberService memberService,
                              LoanRepository loanRepository,
                              BorrowingPolicyRepository borrowingPolicyRepository) {
        this.catalogService = catalogService;
        this.memberService = memberService;
        this.loanRepository = loanRepository;
        this.borrowingPolicyRepository = borrowingPolicyRepository;
    }


    @Override
    public BorrowResult borrowBook(int memberId, String bookName) {
        Book book = catalogService.searchBook(bookName);
        if (book == null)
            // book not available in library
            return new BorrowResult(-1, BorrowStatus.BOOK_NOT_AVAILABLE, null);
        return borrowBook(memberId, book.getId());
    }

    @Override
    public BorrowResult borrowBook(int memberId, int bookId) {
        Set<BookCopy> bookCopies = catalogService.getBookCopies(bookId);
        if (bookCopies == null)
            // book not available in library
            return new BorrowResult(-1, BorrowStatus.BOOK_NOT_AVAILABLE, null);;

        Member member = memberService.getMember(memberId);
        if (member == null)
            return new BorrowResult(-1, BorrowStatus.MEMBER_NOT_AVAILABLE, null);;

        if (isMemberBorrowingBookLimitReached(memberId, member.getType()))
            return new BorrowResult(-1, BorrowStatus.BORROW_LIMIT_EXCEEDED, null);

        BookCopy borrowedCopy = getBorrowedCopy(member, bookCopies);
        if (borrowedCopy == null)
            // bookCopy cannot be borrowed anymore
            return new BorrowResult(-1, BorrowStatus.NO_AVAILABLE_COPY, null);

        loanRepository.addBookCopyToMember(memberId, borrowedCopy.getId());
        return new BorrowResult(borrowedCopy.getId(), BorrowStatus.BORROWED, borrowedCopy.getDueDate());
    }

    @Override
    public ReturnStatus submitBook(int memberId, int bookCopyId, LocalDateTime localDateTime) {
        Member member = memberService.getMember(memberId);
        if (member == null)
            return ReturnStatus.MEMBER_NOT_AVAILABLE;

        Set<Integer> borrowedBookCopies = loanRepository.getBorrowedBookCopies(memberId);
        if (borrowedBookCopies == null || !borrowedBookCopies.contains(bookCopyId))
            return ReturnStatus.NOT_BORROWED_BY_USER;

        BookCopy bookCopy = catalogService.getBookCopy(bookCopyId);
        ReturnStatus result = bookCopy.releaseBook(localDateTime);
        if (result == ReturnStatus.RETURNED
                || result == ReturnStatus.RETURNED_LATE)
            loanRepository.removeBookCopyFromMember(memberId, bookCopyId);

        return result;
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
        return loanRepository.getBorrowedBookCopiesCount(memberId) >= maxBorrowingBooksCount;
    }
}
