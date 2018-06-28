package keel.apierrorhandling.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity(immutable = true)
@Table(name = "users")
public class User {

    public User(String name, String role, Integer age) {
        this.role = role;
        this.id = null;
        this.name = name;
        this.age = age;
        this.versionNo = null;
    }

    public User(Long id, String name, String role, Integer age, Long versionNo) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.age = age;
        this.versionNo = versionNo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public final Long id;
    public final String name;
    public final String role;
    public final Integer age;
    //optimistic-lock-example-start
    // 楽観ロック用のバージョンカラムには、@Versionを付与します。
    @Version
    public final Long versionNo;
    //optimistic-lock-example-end
}
