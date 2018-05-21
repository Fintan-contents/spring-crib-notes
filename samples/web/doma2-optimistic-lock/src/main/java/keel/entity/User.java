package keel.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.Version;

@Entity(immutable = true)
@Table(name = "users")
public class User {

    public User(Long userId, String userName, Long versionNo) {
        this.userId = userId;
        this.userName = userName;
        this.versionNo = versionNo;
    }

    @Id
    public final Long userId;
    public final String userName;
    // example-start
    // 楽観ロック用のバージョンカラムには、@Versionを付与します。
    @Version
    public final Long versionNo;
    // example-end

}
