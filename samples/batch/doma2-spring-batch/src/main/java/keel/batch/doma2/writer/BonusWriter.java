package keel.batch.doma2.writer;

import keel.batch.doma2.dao.BonusDao;
import keel.batch.doma2.entity.Bonus;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

// doma2-spring-batch-example-start
@Component
public class BonusWriter implements ItemWriter<Bonus> {

    private final BonusDao dao;

    public BonusWriter(BonusDao dao) {
        this.dao = dao;
    }

    @Override
    public void write(List<? extends Bonus> items) {
        dao.insert((List<Bonus>) items);
    }
}
// doma2-spring-batch-example-end
