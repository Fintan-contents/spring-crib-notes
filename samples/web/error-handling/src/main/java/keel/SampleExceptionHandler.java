package keel;

import org.seasar.doma.jdbc.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// exception-handler-start
@ControllerAdvice
public class SampleExceptionHandler {
    
    private final Logger logger = LoggerFactory.getLogger(SampleExceptionHandler.class);
    
    // NoResultExceptionが発生した場合には、クライアントにはステータスコード400を返します。
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "対象データなし")
    @ExceptionHandler(NoResultException.class)
    public void noResultExceptionHandler(NoResultException e) {
        if (logger.isDebugEnabled()) {
            logger.debug("対象のデータが存在しません", e);
        }
    }
}
// exception-handler-end
