package library_management;

import library_management.model.member.Member;
import library_management.model.member.MemberType;
import library_management.repository.MemberRepository;

public class MemberService implements IMemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(MemberType memberType, String name, int contactNumber) {
        Member member = new Member(name, memberType, contactNumber);
        memberRepository.addMember(member);
        return member;
    }

    @Override
    public Member getMember(int memberId) {
        return memberRepository.getMember(memberId);
    }
}
