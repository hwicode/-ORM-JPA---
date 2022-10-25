package jpql;

public class MemberDto {

    private String userName;
    private int age;

    public MemberDto(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }
}
