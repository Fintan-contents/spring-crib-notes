package keel.validation.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public class MailAddressTest {

    @Test
    public void メールアドレスとして妥当なものはインスタンスが生成できること() {
        final MailAddress actual = MailAddress.valueOf("mail@example.com");
        assertThat(actual.getValue())
                  .isEqualTo("mail@example.com");
    }

    @Test
    public void 未入力はエラーとなること() {
        assertThatThrownBy(() -> MailAddress.valueOf(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void アットマークがないものはエラーとなること() {
        assertThatThrownBy(() -> MailAddress.valueOf("example.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void アットマークで始まるものはエラーとなること() {
        assertThatThrownBy(() -> MailAddress.valueOf("@example.com"))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void アットマークでおわるものはエラーとなること() {
        assertThatThrownBy(() -> MailAddress.valueOf("sample@"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}