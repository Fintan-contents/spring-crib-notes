package keel.apierrorhandling;

import keel.apierrorhandling.exception.CustomValidationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

// api-error-handling-example-start
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * リクエストボディに設定された値に対する入力値チェック時に、エラーが発生した場合のハンドリングを実施します。
     * レスポンスボディには、{@link BindingResult}から取得したフィールド名と、メッセージをを出力します。
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(
                ex,
                body(ex.getBindingResult()),
                headers,
                status,
                request);
    }

    /**
     * クエリパラメータに対する入力値チェック時に、エラーが発生した場合のハンドリングを実施します。
     * レスポンスボディには、{@link BindingResult}から取得したフィールド名と、メッセージをを出力します。
     */
    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(
                ex,
                body(ex.getBindingResult()),
                headers,
                status,
                request);
    }

    /**
     * 入力形式に誤りがあった場合のハンドリングを実施します。
     * レスポンスボディには、固定のメッセージを出力します。
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(
                ex,
                body("keel.api-error-handling.HttpMessageNotReadableException"),
                headers,
                status,
                request);
    }

    //optimistic-lock-example-start

    /**
     * {@link ResponseEntityExceptionHandler}がハンドリングしない例外については、{@link ExceptionHandler}を使用してハンドリングします。
     * 楽観ロック例外が発生した場合は、HTTPステータスコードに409を設定します。
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleOptimisticLockingFailureException() {
    }
    //optimistic-lock-example-end

    // database-validation-start

    /**
     * {@link ResponseEntityExceptionHandler} がハンドリングしない例外については、 {@link ExceptionHandler} を使用してハンドリングします。
     * 独自に作成した {@link CustomValidationException} が発生した場合は、HTTPステータスコードに400を設定し、エラー内容を返却しています。
     */
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<Object> handleCustomValidationException(CustomValidationException ex, WebRequest request) {
        return super.handleExceptionInternal(
                ex,
                body(ex.getBindingResult()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    private List<ApiError> body(BindingResult bindingResult) {
        return bindingResult
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ApiError(
                        fieldError.getField(),
                        messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())))
                .collect(Collectors.toList());
    }

    private ApiError body(String code) {
        String message = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        return new ApiError(null, message);
    }
    // database-validation-end
}
// api-error-handling-example-end
