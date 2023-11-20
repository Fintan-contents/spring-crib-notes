package keel.apierrorhandling.controller;

import keel.apierrorhandling.dao.UserDao;
import keel.apierrorhandling.entity.User;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users2")
public class Users2Controller {

    private final MessageSource messageSource;
    private final UserDao userDao;

    public Users2Controller(MessageSource messageSource, UserDao userDao) {
        this.messageSource = messageSource;
        this.userDao = userDao;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserForm form, UriComponentsBuilder builder) {
        User created = userDao.insert(new User(form.getName(), form.getRole(), form.getAge())).getEntity();
        return ResponseEntity.created(builder.pathSegment("users", "{id}").buildAndExpand(created.id).toUri()).body(created);
    }

    // example-start

    /**
     * {@link MethodArgumentNotValidException}を補足して、当画面固有のメッセージをリクエストボディに出力します。
     * ステータスコードは、400を返却します。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        messageSource.getMessage(
                                "keel.api-error-handling.MethodArgumentNotValidException",
                                new Object[0],
                                LocaleContextHolder.getLocale()
                        )
                );
    }
    // example-end
}
