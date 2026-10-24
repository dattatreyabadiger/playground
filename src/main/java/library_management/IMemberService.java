package library_management;

import library_management.model.member.Member;
import library_management.model.member.MemberType;

public interface IMemberService {

    Member createMember(MemberType memberType, String name, int contactNumber);

    Member getMember(int memberId);
}
