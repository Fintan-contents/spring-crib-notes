package keel.controller;


import keel.controller.form.UserUpdateForm;
import keel.domain.service.UserService;
import keel.domain.service.dto.UserDto;
import keel.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// example-start
@Controller
@RequestMapping("user")
@SessionAttributes(names = "form") // Formクラスをセッションに格納します
public class UserUpdateController {

    private final UserService userService;

    public UserUpdateController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "form")
    public UserUpdateForm setup() {
        return new UserUpdateForm();
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute(name = "form") UserUpdateForm form) {
        User user = userService.find();
        // セッションに格納されているFormクラスに取得した情報を設定します
        form.setUserId(user.userId);
        form.setUserName(user.userName);
        form.setVersionNo(user.versionNo);
        return "user/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute(name = "form") @Validated UserUpdateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        // 画面の入力値と、セッションに格納されている情報を使用してデータを更新します
        userService.update(new UserDto(form.getUserId(), form.getUserName(), form.getVersionNo()));
        // データ更新に成功した場合は、初期表示にリダイレクトします
        return "redirect:/user/edit?success";
    }
}
// example-end