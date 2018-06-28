package keel.batch.doma2.entity;

import org.seasar.doma.Entity;

@Entity(immutable = true)
public class EmployeeBonus {

    private final long id;
    private final String name;
    private final long salary;
    private final Long bonusMagnification;
    private final Long fixedBonus;

    public EmployeeBonus(long id, String name, long salary, Long bonusMagnification, Long fixedBonus) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.bonusMagnification = bonusMagnification;
        this.fixedBonus = fixedBonus;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
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
