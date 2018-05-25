package keel.validation.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import keel.validation.entity.Users;
import keel.validation.service.UserService;
import keel.validation.value.MailAddress;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    
    @ModelAttribute
    public Form form() {
        return new Form();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // example-start
    @PostMapping("/")
    public ModelAndView add(@Valid @ModelAttribute Form form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return createValidationErrorResponse();
        }
        try {
            userService.insert(new Users(form.name, form.mailAddress.getValue(), form.role));
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

        // type-converter-error-start
        @NotEmpty
        private String name;

        private MailAddress mailAddress;
        
        @NotNull
        private Integer age;

        @NotEmpty
        private String role;
        // type-converter-error-end

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public MailAddress getMailAddress() {
            return mailAddress;
        }

        public void setMailAddress(final MailAddress mailAddress) {
            this.mailAddress = mailAddress;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(final Integer age) {
            this.age = age;
        }

        public String getRole() {
            return role;
        }

        public void setRole(final String role) {
            this.role = role;
        }
    }
}
