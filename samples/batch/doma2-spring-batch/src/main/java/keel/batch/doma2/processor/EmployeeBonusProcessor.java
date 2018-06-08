package keel.batch.doma2.processor;

import keel.batch.doma2.entity.Bonus;
import keel.batch.doma2.entity.EmployeeBonus;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeBonusProcessor implements ItemProcessor<EmployeeBonus, Bonus> {
    @Override
    public Bonus process(EmployeeBonus item) {
        return new Bonus(item.getId(), calc(item.getSalary(), item.getBonusMagnification(), item.getFixedBonus()));
    }

    private long calc(long salary, Long bonusMagnification, Long fixedBonus) {
        return fixedBonus != null ? fixedBonus : salary * bonusMagnification;
    }
}
