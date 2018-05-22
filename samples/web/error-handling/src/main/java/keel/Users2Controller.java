package keel;

import org.seasar.doma.jdbc.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// controller-exception-handling-start
@Controller
public class Users2Controller {

    private final UserDao userDao;
    private final Logger logger = LoggerFactory.getLogger(Users2Controller.class);

    public Users2Controller(final UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/users2/{id:[0-9]+}")
    @ResponseBody
    public String getUser(@PathVariable Long id) {
        final String name = userDao.getUserName(id);
        return "name->" + name;
    }

    // NoResultExceptionを捕捉して、デフォルトの404ページではなく
    // Controller固有の404ページをクライアントに返します。
    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String noResultExceptionHandler(NoResultException e) {
        if (logger.isDebugEnabled()) {
            logger.debug("data not found.", e);
        }
        return "error/custom_404";
    }
}
// controller-exception-handling-end
