package library_management.model.member;

public class Student extends Member {

    public Student(String name, int contactNumber) {
        super(name, MemberType.STUDENT, contactNumber);
    }
}
