package keel.batch.doma2.entity;

import org.seasar.doma.Entity;

@Entity(immutable = true)
public class EmployeeBonus {

    private final long id;
    private final long salary;
    private final Long bonusMagnification;
    private final Long fixedBonus;

    public EmployeeBonus(long id, long salary, Long bonusMagnification, Long fixedBonus) {
        this.id = id;
        this.salary = salary;
        this.bonusMagnification = bonusMagnification;
        this.fixedBonus = fixedBonus;
    }

    public long getId() {
        return id;
    }

    public long getSalary() {
        return salary;
    }

    public Long getBonusMagnification() {
        return bonusMagnification;
    }

    public Long getFixedBonus() {
        return fixedBonus;
    }
}
