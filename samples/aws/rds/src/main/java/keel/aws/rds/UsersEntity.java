package keel.aws.rds;

import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import org.seasar.doma.Id;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;

@Entity(immutable = true)
@Table(name = "users")
public class UsersEntity {
              
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;

    public final String name;

    public UsersEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UsersEntity(String name) {
        this(null, name);
    }

}
