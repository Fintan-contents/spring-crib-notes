package keel.batch.doma2.entity;

import lombok.Value;
import org.seasar.doma.Entity;

@Entity(immutable = true)
@Value
public class Bonus {
    public final long employee_id;
    public final long payments;
}
