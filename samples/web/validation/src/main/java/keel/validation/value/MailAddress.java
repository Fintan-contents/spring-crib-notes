package keel.validation.value;

import org.springframework.util.StringUtils;

public class MailAddress {
    
    private final String value;

    private MailAddress(final String value) {
        this.value = value;
    }

    public static MailAddress valueOf(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("値が設定されていません");
        }
        if (!value.contains("@") || value.startsWith("@") || value.endsWith("@")) {
            throw new IllegalArgumentException("メールアドレスの形式[sample@example.com]になっていません。");
        }
        return new MailAddress(value);
    }

    public String getValue() {
        return value;
    }
}
