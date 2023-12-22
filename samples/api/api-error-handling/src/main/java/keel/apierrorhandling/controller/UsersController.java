package keel.apierrorhandling.controller;

import keel.apierrorhandling.entity.User;
import keel.apierrorhandling.exception.CustomValidationException;
import keel.apierrorhandling.exception.UserNotFoundException;
import keel.apierrorhandling.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    // database-validation-start
    private static final String ROLE_NOT_FOUND_MESSAGE = "keel.api-error-handling.role-not-found";

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserForm form, UriComponentsBuilder builder) {
        try {
            User created = userService.insert(new User(form.getName(), form.getRole(), form.getAge())).getEntity();
            return ResponseEntity.created(builder.pathSegment("users", "{id}").buildAndExpand(created.id).toUri()).body(created);
        } catch (UserService.RoleNotFoundException e) {
            // ロールがデータベースのロールテーブル上に存在しないという例外を捕捉し、入力値と紐づけて例外を送出します。
            BindingResult bindingResult = new BeanPropertyBindingResult(form, "");
            bindingResult.rejectValue("role", ROLE_NOT_FOUND_MESSAGE, new Object[]{form.getRole()}, ROLE_NOT_FOUND_MESSAGE);
            throw new CustomValidationException(bindingResult);
        }
    }
    // database-validation-end

    @GetMapping("/{id:[\\d]+}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id).orElseThrow(UserNotFoundException::new);
    }

    // 楽観ロックのサンプルとして利用するため、ユーザやロールが存在しない場合の例外はハンドリングしていません。
    //optimistic-lock-example-start
    @PostMapping("/{id:[\\d]+}")
    public User updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateForm form) {
        // リクエストボディのバージョン番号を、@Versionのついたプロパティに設定して更新します。
        // リクエストボディのバージョン番号が、データベースのバージョン番号と一致しない場合は楽観ロックエラーとなり、OptimisticLockingFailureExceptionが発生します。
        // 更新に成功した場合は、新しいバージョン番号を持ったエンティティを返します。
        return userService.update(new User(id, form.getName(), form.getRole(), form.getAge(), form.getVersionNo())).getEntity();
    }
    //optimistic-lock-example-end
}
