package keel.validation.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

@Entity(immutable = true)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    public final String name;
    public final String mailAddress;
    public final String role;

    public Users(final String name, final String mailAddress, final String role) {
        this(null, name, mailAddress, role);
    }

    public Users(final Long id, final String name, final String mailAddress, final String role) {
        this.id = id;
        this.name = name;
        this.mailAddress = mailAddress;
        this.role = role;
    }
}
