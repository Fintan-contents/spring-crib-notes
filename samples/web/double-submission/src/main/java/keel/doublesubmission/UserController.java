package keel.doublesubmission;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.terasoluna.gfw.web.token.transaction.InvalidTransactionTokenException;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

// example-start
@Controller
@RequestMapping("user")
// 生成するトークンのネームスペースを設定します。
@TransactionTokenCheck("user")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public UserForm userForm() {
        return new UserForm();
    }

    @GetMapping("/")
    public String input() {
        return "user/input";
    }

    // @TransactionTokenCheckのtype属性にTransactionTokenType.BEGINを設定して、トークンを生成します。
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    @PostMapping("/confirm")
    public String confirm(@Validated UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/input";
        }
        return "user/confirm";
    }

    // @TransactionTokenCheck設定して、リクエストで送信されるトークンとサーバで保持しているトークンが同一であるかをチェックします。
    // 同一でない場合は、InvalidTransactionTokenExceptionが送出されます。
    // @TransactionTokenCheckのtype属性のデフォルト値は、TransactionTokenType.INです。
    // また、登録完了後の画面でページの再読込みを実施した場合に登録処理が再実行されることを防止するため、
    // PRG(Post-Redirect-Get)パターンを適用しています。
    @TransactionTokenCheck
    @PostMapping("/create")
    public String create(@Validated UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/input";
        }
        userService.insert(new User(form.name, form.age));
        return "redirect:/user/complete";
    }

    @GetMapping("/complete")
    public String complete() {
        return "user/complete";
    }

    // @ExceptionHandlerを使用して、InvalidTransactionTokenExceptionが送出された場合のハンドリングをします。
    @ExceptionHandler(InvalidTransactionTokenException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String invalidTransactionTokenExceptionHandler(InvalidTransactionTokenException e) {
        if (logger.isDebugEnabled()) {
            logger.debug(e.getMessage());
        }
        return "error/token-error";
    }

    @Data
    public static class UserForm {
        @NotEmpty
        private String name;

        @Min(1)
        @Max(150)
        private Integer age;
    }
}
// example-end
