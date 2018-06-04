package keel.batch.doma2.dao;

import keel.batch.doma2.entity.Bonus;
import org.seasar.doma.BatchInsert;
import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.BatchResult;

import java.util.List;

// doma2-spring-batch-example-start
@Dao
@ConfigAutowireable
public interface BonusDao {
    @BatchInsert
    BatchResult<Bonus> insert(List<Bonus> bonuses);
}
// doma2-spring-batch-example-end
