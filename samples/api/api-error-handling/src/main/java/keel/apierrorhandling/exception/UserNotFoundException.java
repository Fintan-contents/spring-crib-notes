package keel.apierrorhandling.exception;

import keel.apierrorhandling.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// example-start
/**
 * {@link ResponseStatus} が付いている例外を送出することで、 {@link GlobalExceptionHandler} でハンドリングしていなくても、
 * {@link ResponseStatus} に指定したHTTPステータスでレスポンスが返却されます。
 * <p>
 * {@code reason} に指定した文字列は、 {@code messages.properties} でキーとして定義されている場合は対応するメッセージに変換されます。
 * そうでない場合はそのまま表示されます。
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "keel.api-error-handling.user-not-found")
public class UserNotFoundException extends RuntimeException {
}
// example-end
