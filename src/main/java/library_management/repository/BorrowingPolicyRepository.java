package library_management.repository;

import library_management.model.member.MemberType;

public interface BorrowingPolicyRepository {

    int getMaxBorrowingDays(MemberType memberType);

    int getMaxBorrowingBooksCount(MemberType memberType);
}
