package keel.batch.doma2.entity;

import lombok.Value;
import org.seasar.doma.Entity;

@Entity(immutable = true)
@Value
public class EmployeeBonus {
    public final long id;
    public final String name;
    public final long salary;
    public final Long bonusMagnification;
    public final Long fixedBonus;
}
