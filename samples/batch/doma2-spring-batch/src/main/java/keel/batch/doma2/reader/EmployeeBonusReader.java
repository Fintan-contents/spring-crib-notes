package keel.batch.doma2.reader;

import keel.batch.doma2.dao.EmployeeBonusDao;
import keel.batch.doma2.entity.EmployeeBonus;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.stream.Stream;

// doma2-spring-batch-example-start
@Component
public class EmployeeBonusReader extends AbstractItemCountingItemStreamItemReader<EmployeeBonus> {

    private final EmployeeBonusDao dao;
    private Stream<EmployeeBonus> stream;
    private Iterator<EmployeeBonus> iterator;

    public EmployeeBonusReader(EmployeeBonusDao dao) {
        super.setName(this.getClass().getSimpleName());
        this.dao = dao;
    }

    @Override
    protected EmployeeBonus doRead() {
        return iterator.hasNext() ? iterator.next() : null;
    }

    @Override
    protected void doOpen() {
        stream = dao.selectEmployeeBonus();
        iterator = stream.iterator();

    }

    @Override
    protected void doClose() {
        stream.close();
    }
}
// doma2-spring-batch-example-end
