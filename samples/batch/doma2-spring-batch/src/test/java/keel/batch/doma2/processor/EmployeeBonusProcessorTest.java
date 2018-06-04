package keel.batch.doma2.processor;

import static org.assertj.core.api.Assertions.assertThat;

import keel.batch.doma2.entity.Bonus;
import keel.batch.doma2.entity.EmployeeBonus;

import org.junit.Test;

public class EmployeeBonusProcessorTest {

    private EmployeeBonusProcessor sut = new EmployeeBonusProcessor();

    @Test
    public void 固定のボーナスが設定されている場合はそれがボーナスとなります() throws Exception {
        final Bonus actual = sut.process(new EmployeeBonus(1L, "name", 100L, null, 200L));
        assertThat(actual)
                .extracting("employee_id", "payments")
                .containsExactly(1L, 200L);
    }

    @Test
    public void 固定のボーナスが設定されていない場合は給与と倍率からボーナスが算出されます() throws Exception {
        final Bonus actual = sut.process(new EmployeeBonus(10L, "name", 100L, 3L, null));
        assertThat(actual)
                .extracting("employee_id", "payments")
                .containsExactly(10L, 300L);
    }
}