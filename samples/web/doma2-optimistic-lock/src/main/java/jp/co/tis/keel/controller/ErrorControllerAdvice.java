package jp.co.tis.keel.controller;

import org.seasar.doma.jdbc.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    /**
     * 楽観ロック例外が発生した場合は、HTTPステータスコードに409を設定。
     *
     * @param e 楽観ロック例外
     */
    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "楽観排他エラー")
    public void optimisticLockExceptionHandler(OptimisticLockException e) {
        LOGGER.debug("排他制御エラーが発生しました", e);
    }
}
