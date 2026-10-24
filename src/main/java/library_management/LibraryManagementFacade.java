package library_management;

import library_management.model.BorrowResult;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.member.Member;
import library_management.model.member.MemberType;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class LibraryManagementFacade implements ILibraryManagementFacade {

    private final ICatalogService catalogService;
    private final ICirculationService circulationService;
    private final IMemberService memberService;

    @Override
    public Book searchBook(String bookName) {
        return catalogService.searchBook(bookName);
    }

    @Override
    public BorrowResult borrowBook(int memberId, String bookName) {
        return circulationService.borrowBook(memberId, bookName);
    }

    @Override
    public BorrowResult borrowBook(int memberId, int bookId) {
        return circulationService.borrowBook(memberId, bookId);
    }

    @Override
    public ReturnStatus returnBook(int memberId, int bookCopyId, LocalDateTime localDateTime) {
        return circulationService.submitBook(memberId, bookCopyId, localDateTime);
    }

    @Override
    public Member createMember(MemberType memberType, String name, int contactNumber) {
        return memberService.createMember(memberType, name, contactNumber);
    }
}
