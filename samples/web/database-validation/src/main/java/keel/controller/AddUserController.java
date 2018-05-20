package keel.controller;

import javax.validation.constraints.NotEmpty;

import keel.entity.Users;
import keel.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddUserController {

    private final UserService userService;

    public AddUserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Form form) {
        return "index";
    }

    // example-start
    @PostMapping("/")
    public ModelAndView add(@Validated @ModelAttribute Form form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return createValidationErrorResponse();
        }
        try {
            userService.insert(new Users(form.name, form.mailAddress, form.role));
        } catch (UserService.RoleNotFoundException e) {
            // ロールがデータベースのロールテーブル上に存在しない例外を捕捉して
            // 画面にメッセージを表示します。
            bindingResult.addError(new FieldError(
                    "form",
                    "role",
                    "ロールにはadmin以外登録出来ません。"));
            return createValidationErrorResponse();
        } catch (DuplicateKeyException e) {
            // 同じメールアドレスがデータベースに登録済みの場合には
            // 重複エラーが送出されるのでそれを元に画面にメッセージを表示します。
            bindingResult.addError(new FieldError(
                    "form",
                    "mailAddress",
                    "入力されたメールアドレスは登録済みです。"));
            return createValidationErrorResponse();
        }
        return new ModelAndView("redirect:/");
    }
    
    private ModelAndView createValidationErrorResponse() {
        return new ModelAndView("index", HttpStatus.BAD_REQUEST);
    }
    // example-end
    

    public static class Form {

        @NotEmpty
        private String name;

        @NotEmpty
        private String mailAddress;

        @NotEmpty
        public String role;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getMailAddress() {
            return mailAddress;
        }

        public void setMailAddress(final String mailAddress) {
            this.mailAddress = mailAddress;
        }

        public String getRole() {
            return role;
        }

        public void setRole(final String role) {
            this.role = role;
        }
    }
}
