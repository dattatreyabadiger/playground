package library_management.repository;

import library_management.model.member.BorrowingPolicy;
import library_management.model.member.MemberType;

import java.util.Map;

public class InMemoryBorrowingPolicyRepository implements BorrowingPolicyRepository {

    private final Map<MemberType, BorrowingPolicy> memberToMaxBorrowingPolicyMap;

    public InMemoryBorrowingPolicyRepository() {
        this.memberToMaxBorrowingPolicyMap = Map.of(MemberType.STAFF, new BorrowingPolicy(7, 5),
                                                    MemberType.STUDENT, new BorrowingPolicy(5, 3));
    }

    @Override
    public int getMaxBorrowingDays(MemberType memberType) {
        return memberToMaxBorrowingPolicyMap.get(memberType)
                                            .getMaxDays();
    }

    @Override
    public int getMaxBorrowingBooksCount(MemberType memberType) {
        return memberToMaxBorrowingPolicyMap.get(memberType)
                                            .getMaxBooks();
    }
}
