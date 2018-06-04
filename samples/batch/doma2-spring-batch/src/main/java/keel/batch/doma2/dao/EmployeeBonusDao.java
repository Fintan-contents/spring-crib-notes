package keel.batch.doma2.dao;

import keel.batch.doma2.entity.EmployeeBonus;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Suppress;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.message.Message;

import java.util.stream.Stream;

// doma2-spring-batch-example-start
@Dao
@ConfigAutowireable
public interface EmployeeBonusDao {
    @Select
    @Suppress(messages = Message.DOMA4274)
    Stream<EmployeeBonus> selectEmployeeBonus();
}
// doma2-spring-batch-example-end
