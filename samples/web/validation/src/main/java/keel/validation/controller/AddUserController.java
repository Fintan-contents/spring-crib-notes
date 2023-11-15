package keel.validation.controller;

import jakarta.validation.Valid;

import keel.validation.entity.User;
import keel.validation.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AddUserController {

    private final UserService userService;

    // message-source-injection-start
    private final MessageSource messageSource;

    public AddUserController(final UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }
    // // message-source-injection-end

    @GetMapping("/")
    public String index(Form form) {
        return "index";
    }

    // example-start
    @PostMapping("/")
    public ModelAndView add(@Valid @ModelAttribute Form form, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return createValidationErrorResponse();
        }
        try {
            userService.insert(new User(form.getName(), form.getMailAddress().getValue(), form.getRole()));
        } catch (UserService.RoleNotFoundException e) {
            // ロールがデータベースのロールテーブル上に存在しない例外を捕捉して
            // 画面にメッセージを表示します。
            bindingResult.rejectValue("role", "role.notFound");
            return createValidationErrorResponse();
        } catch (DuplicateKeyException e) {
            // 同じメールアドレスがデータベースに登録済みの場合には
            // 重複エラーが送出されるのでそれを元に画面にメッセージを表示します。
            bindingResult.rejectValue("mailAddress", "mailAddress.duplicated");
            return createValidationErrorResponse();
        }

        // message-source-start
        // MessageSourceから完了メッセージを取得して、Flashスコープに設定します。
        // プレースホルダに、「ユーザ」を設定します。
        redirectAttributes.addFlashAttribute("successMessage",
                messageSource.getMessage("add.success", new String[]{"ユーザ"}, LocaleContextHolder.getLocale()));
        // message-source-end

        return new ModelAndView("redirect:/");
    }
    
    private ModelAndView createValidationErrorResponse() {
        return new ModelAndView("index", HttpStatus.BAD_REQUEST);
    }
    // example-end

}
