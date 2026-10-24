package library_management.repository;

import library_management.model.member.Member;

import java.util.HashMap;
import java.util.Map;

public class InMemoryMemberRepository implements MemberRepository {

    private final Map<Integer, Member> memberIdToMemberMap;

    public InMemoryMemberRepository() {
        this.memberIdToMemberMap = new HashMap<>();
    }

    @Override
    public void addMember(Member member) {
        memberIdToMemberMap.put(member.getId(), member);
    }

    @Override
    public Member getMember(int memberId) {
        return memberIdToMemberMap.get(memberId);
    }
}
