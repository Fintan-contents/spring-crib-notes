package keel.batch.doma2.entity;

import org.seasar.doma.Entity;

@Entity(immutable = true)
public class Bonus {

    private final long employee_id;
    private final long payments;

    public Bonus(long employee_id, long payments) {
        this.employee_id = employee_id;
        this.payments = payments;
    }

    public long getEmployee_id() {
        return employee_id;
    }

    public long getPayments() {
        return payments;
    }
}
