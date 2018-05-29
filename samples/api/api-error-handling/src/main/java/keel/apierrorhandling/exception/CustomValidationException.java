package keel.apierrorhandling.exception;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

/**
 * データベースを使用した入力値チェックがエラーになった場合など、 {@link Controller} から送出する例外クラスです。
 * <p>
 * Formの入力値などと関連付けるために {@link BindingResult} を保持しています。
 */
public class CustomValidationException extends RuntimeException {

    private final BindingResult bindingResult;

    public CustomValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return this.bindingResult;
    }

    @Override
    public String getMessage() {
        return this.bindingResult.toString();
    }
}
