package library_management;

import library_management.model.BorrowStatus;
import library_management.model.ReturnStatus;
import library_management.model.book.Book;
import library_management.model.member.Member;
import library_management.model.member.MemberType;
import library_management.repository.InMemoryInventoryRepository;
import library_management.repository.InMemoryBorrowingPolicyRepository;
import library_management.repository.InMemoryMemberRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LibraryManagementServiceTest {

    private final ILibraryManagementService libraryManagementService;

    public LibraryManagementServiceTest() {
        libraryManagementService = new LibraryManagementService(new InMemoryInventoryRepository(),
                                                                new InMemoryBorrowingPolicyRepository(),
                                                                new InMemoryMemberRepository());
    }

    @Test
    public void searchBookTest() {
        Book book = libraryManagementService.searchBook("abc");
        assertNotNull(book);

        Book book2 = libraryManagementService.searchBook("abcd");
        assertNull(book2);
    }

    @Test
    public void borrowTest() {
        Member student = libraryManagementService.createMember(MemberType.STUDENT, "s1", 911);
        assertNotNull(student);

        BorrowStatus resp = libraryManagementService.borrow(student.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp);

        BorrowStatus resp2 = libraryManagementService.borrow(student.getId(), "def");
        assertEquals(BorrowStatus.NO_AVAILABLE_COPY, resp2);

        BorrowStatus resp3 = libraryManagementService.borrow(student.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp3);
    }

    @Test
    public void borrowTest_maxBooksAllowedForStudent() {
        Member student = libraryManagementService.createMember(MemberType.STUDENT, "s1", 911);
        assertNotNull(student);

        BorrowStatus resp = libraryManagementService.borrow(student.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp);

        BorrowStatus resp2 = libraryManagementService.borrow(student.getId(), "abc");
        assertEquals(BorrowStatus.BORROWED, resp2);

        BorrowStatus resp3 = libraryManagementService.borrow(student.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp3);

        BorrowStatus resp4 = libraryManagementService.borrow(student.getId(), "abc");
        assertEquals(BorrowStatus.BORROW_LIMIT_EXCEEDED, resp4);
    }

    @Test
    public void borrowTest_maxBooksAllowedForStaff() {
        Member staff = libraryManagementService.createMember(MemberType.STAFF, "sf1", 911);
        assertNotNull(staff);

        BorrowStatus resp = libraryManagementService.borrow(staff.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp);

        BorrowStatus resp2 = libraryManagementService.borrow(staff.getId(), "abc");
        assertEquals(BorrowStatus.BORROWED, resp2);

        BorrowStatus resp3 = libraryManagementService.borrow(staff.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp3);

        BorrowStatus resp4 = libraryManagementService.borrow(staff.getId(), "abc");
        assertEquals(BorrowStatus.BORROWED, resp4);

        BorrowStatus resp5 = libraryManagementService.borrow(staff.getId(), "xyz");
        assertEquals(BorrowStatus.BORROWED, resp5);

        BorrowStatus resp6 = libraryManagementService.borrow(staff.getId(), "xyz");
        assertEquals(BorrowStatus.BORROW_LIMIT_EXCEEDED, resp6);
    }

    @Test
    public void submitTest() {
        Member staff = libraryManagementService.createMember(MemberType.STUDENT, "sf1", 978);
        assertNotNull(staff);

        BorrowStatus respCode = libraryManagementService.borrow(staff.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, respCode);

        LocalDateTime timeAfter8Days = LocalDateTime.now().plusDays(8);
        ReturnStatus respCode2 = libraryManagementService.submit(staff.getId(), 5, timeAfter8Days);
        assertEquals(ReturnStatus.RETURNED_LATE, respCode2);


        Member staff2 = libraryManagementService.createMember(MemberType.STUDENT, "sf2", 978);
        assertNotNull(staff2);
        BorrowStatus resp3 = libraryManagementService.borrow(staff2.getId(), "def");
        assertEquals(BorrowStatus.BORROWED, resp3);

        LocalDateTime timeAfter2Days = LocalDateTime.now().plusDays(2);
        ReturnStatus resp4 = libraryManagementService.submit(staff2.getId(), 5, timeAfter2Days);
        assertEquals(ReturnStatus.RETURNED, resp4);

        LocalDateTime timeAfter1Days = LocalDateTime.now().plusDays(1);
        ReturnStatus resp5 = libraryManagementService.submit(staff.getId(), 7, timeAfter1Days);
        assertEquals(ReturnStatus.NOT_BORROWED_BY_USER, resp5);
    }
}
