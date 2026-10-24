package library_management.model.member;

public class Staff extends Member {

    public Staff(String name, int contactNumber) {
        super(name, MemberType.STAFF, contactNumber);
    }
}
