package keel.tableauth;

import org.seasar.doma.Entity;

@Entity(immutable = true)
public class UserEntity {

    public final String username;

    public final String password;

    public UserEntity(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}
