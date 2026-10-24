package library_management;

import library_management.model.BorrowResult;
import library_management.model.BorrowStatus;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.member.Member;
import library_management.model.member.MemberType;
import library_management.repository.InMemoryInventoryRepository;
import library_management.repository.InMemoryBorrowingPolicyRepository;
import library_management.repository.InMemoryLoanRepository;
import library_management.repository.InMemoryMemberRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LibraryManagementFacadeTest {

    private final ILibraryManagementFacade libraryManagementFacade;

    public LibraryManagementFacadeTest() {
        CatalogService catalogService = new CatalogService(new InMemoryInventoryRepository());
        MemberService memberService = new MemberService(new InMemoryMemberRepository());

        libraryManagementFacade =
                new LibraryManagementFacade(catalogService,
                                            new CirculationService(catalogService, memberService,
                                                                   new InMemoryLoanRepository(),
                                                                   new InMemoryBorrowingPolicyRepository()),
                                            memberService);
    }

    @Test
    void searchBookTest() {
        Book book = libraryManagementFacade.searchBook("abc");
        assertNotNull(book);

        Book book2 = libraryManagementFacade.searchBook("abcd");
        assertNull(book2);
    }

    @Test
    void borrowBookTest() {
        Member student = libraryManagementFacade.createMember(MemberType.STUDENT, "s1", 911);
        assertNotNull(student);

        BorrowResult resp = libraryManagementFacade.borrowBook(student.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp.getBorrowStatus());

        BorrowResult resp2 = libraryManagementFacade.borrowBook(student.getId(), "def");
        assertEquals(BorrowStatus.NO_AVAILABLE_COPY, resp2.getBorrowStatus());

        BorrowResult resp3 = libraryManagementFacade.borrowBook(student.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp3.getBorrowStatus());
    }

    @Test
    void borrowBookTest_maxBooksAllowedForStudent() {
        Member student = libraryManagementFacade.createMember(MemberType.STUDENT, "s1", 911);
        assertNotNull(student);

        BorrowResult resp = libraryManagementFacade.borrowBook(student.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp.getBorrowStatus());

        BorrowResult resp2 = libraryManagementFacade.borrowBook(student.getId(), "abc");
        assertEquals(BorrowStatus.BORROWED, resp2.getBorrowStatus());

        BorrowResult resp3 = libraryManagementFacade.borrowBook(student.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp3.getBorrowStatus());

        BorrowResult resp4 = libraryManagementFacade.borrowBook(student.getId(), "abc");
        assertEquals(BorrowStatus.BORROW_LIMIT_EXCEEDED, resp4.getBorrowStatus());
    }

    @Test
    void borrowBookTest_maxBooksAllowedForStaff() {
        Member staff = libraryManagementFacade.createMember(MemberType.STAFF, "sf1", 911);
        assertNotNull(staff);

        BorrowResult resp = libraryManagementFacade.borrowBook(staff.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp.getBorrowStatus());

        BorrowResult resp2 = libraryManagementFacade.borrowBook(staff.getId(), "abc");
        assertEquals(BorrowStatus.BORROWED, resp2.getBorrowStatus());

        BorrowResult resp3 = libraryManagementFacade.borrowBook(staff.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp3.getBorrowStatus());

        BorrowResult resp4 = libraryManagementFacade.borrowBook(staff.getId(), "abc");
        assertEquals(BorrowStatus.BORROWED, resp4.getBorrowStatus());

        BorrowResult resp5 = libraryManagementFacade.borrowBook(staff.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp5.getBorrowStatus());

        BorrowResult resp6 = libraryManagementFacade.borrowBook(staff.getId(), "xyz");
        assertEquals(BorrowStatus.BORROW_LIMIT_EXCEEDED, resp6.getBorrowStatus());
    }

    @Test
    void returnBookTest() {
        Member staff = libraryManagementFacade.createMember(MemberType.STUDENT, "sf1", 978);
        assertNotNull(staff);

        BorrowResult resp = libraryManagementFacade.borrowBook(staff.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp.getBorrowStatus());

        LocalDateTime timeAfter8Days = LocalDateTime.now()
                                                    .plusDays(8);
        ReturnStatus resp2 = libraryManagementFacade.returnBook(staff.getId(), resp.getBookCopyId(), timeAfter8Days);
        assertEquals(ReturnStatus.RETURNED_LATE, resp2);


        Member staff2 = libraryManagementFacade.createMember(MemberType.STUDENT, "sf2", 978);
        assertNotNull(staff2);
        BorrowResult resp3 = libraryManagementFacade.borrowBook(staff2.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp3.getBorrowStatus());

        LocalDateTime timeAfter2Days = LocalDateTime.now()
                                                    .plusDays(2);
        ReturnStatus resp4 = libraryManagementFacade.returnBook(staff2.getId(), resp3.getBookCopyId(), timeAfter2Days);
        assertEquals(ReturnStatus.RETURNED, resp4);

        LocalDateTime timeAfter1Days = LocalDateTime.now()
                                                    .plusDays(1);
        ReturnStatus resp5 = libraryManagementFacade.returnBook(staff.getId(), 7, timeAfter1Days);
        assertEquals(ReturnStatus.NOT_BORROWED_BY_USER, resp5);
    }
}
