package keel;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;

    public final String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this(null, name);
    }
}
