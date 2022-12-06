package keel.tableauth;

import org.seasar.doma.Entity;

@Entity(immutable = true)
public class UserEntity {

    private final String username;

    private final String password;

    public UserEntity(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
