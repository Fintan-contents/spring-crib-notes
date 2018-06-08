package keel.apierrorhandling.dao.criteria;

public class UserCriteria {

    private final String name;
    private final String role;
    private final Integer age;

    public UserCriteria(String name, String role, Integer age) {
        this.name = name;
        this.role = role;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Integer getAge() {
        return age;
    }
}
