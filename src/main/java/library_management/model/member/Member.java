package library_management.model.member;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Member {

    private final int id;
    private final String name;
    private final MemberType type;
    private final int contactNumber;

    private static final AtomicInteger memberIdGenerator = new AtomicInteger();

    public Member(String name, MemberType type, int contactNumber) {
        this.id = memberIdGenerator.incrementAndGet();
        this.name = name;
        this.type = type;
        this.contactNumber = contactNumber;
    }
}
