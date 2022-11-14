package keel;

public class User {

    private final Long id;

    private final String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
