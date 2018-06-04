package keel.doublesubmission;

import org.seasar.doma.*;

@Entity(immutable = true)
@Table(name = "users")
public class User {

    public User(String name, Integer age) {
        this(null, name, age);
    }

    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    public final String name;
    public final Integer age;
}
