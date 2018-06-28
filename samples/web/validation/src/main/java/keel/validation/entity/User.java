package keel.validation.entity;

import org.seasar.doma.*;

@Entity(immutable = true)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    public final String name;
    public final String mailAddress;
    public final String role;

    public User(final String name, final String mailAddress, final String role) {
        this(null, name, mailAddress, role);
    }

    public User(final Long id, final String name, final String mailAddress, final String role) {
        this.id = id;
        this.name = name;
        this.mailAddress = mailAddress;
        this.role = role;
    }
}
