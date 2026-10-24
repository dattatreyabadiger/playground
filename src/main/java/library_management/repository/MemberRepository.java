package library_management.repository;

import library_management.model.member.Member;

public interface MemberRepository {

    void addMember(Member member);

    Member getMember(int memberId);
}
