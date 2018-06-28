package keel.validation.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;

@Entity(immutable = true)
public class Role {

    @Id
    public final String name;

    public Role(final String name) {
        this.name = name;
    }
}
