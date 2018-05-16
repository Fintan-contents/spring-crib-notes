Spring Boot + Doma2を使用した楽観ロックの検証
====================================================================================================

Doma2の `@Version` を使用した楽観ロック方式で実現する。

参考情報
    * `Doma2 - 更新 <http://doma.readthedocs.io/ja/stable/query/update/>`_
    * `Doma2 - バッチ更新 <http://doma.readthedocs.io/ja/stable/query/batch-update/>`_

処理フロー
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
* 初期表示

  * 画面表示するデータをDBから取得
  * 取得したデータをセッションに格納

* 更新

  * 画面の入力値を精査
  * 精査後の入力値とセッションに格納したバージョン番号を使用して、DBのデータを更新


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
Controller
    .. code-block:: java

        @Controller
        @RequestMapping("admin/user")
        @SessionAttributes(names= "form") // Formクラスをセッションに格納
        public class UserUpdateController {

            private final UserService userService;

            public UserUpdateController(UserService userService) {
                this.userService= userService;
            }

            @ModelAttribute(name= "form")
            public UserUpdateForm setup() {
                return new UserUpdateForm();
            }

            @GetMapping("/edit")
            public String edit(@AuthenticationPrincipal Account account, @ModelAttribute(name= "form") UserUpdateForm form) {
                // 画面に表示するユーザ情報を取得
                User user= userService.findOne(account.getUsername());
                // バージョン番号をFormに設定
                form.setVersionNo(user.versionNo);
                return "user/edit";
            }

            @PostMapping("update")
            public String update(@AuthenticationPrincipal Account account,
                    @ModelAttribute(name= "form") @Validated UserUpdateForm form, BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    return "user/edit";
                }
                // プリンシパル情報、画面入力されたパスワード、及びセッションに格納したバージョン番号を使用してデータを更新
                userService.update(new PasswordUpdateDto(account.getUsername(), form.getPassword(), form.getVersionNo()));
                // データ更新に成功した場合は、初期表示にリダイレクト
                return "redirect:/admin/user/edit?success";
            }
        }


Dao
    .. code-block:: java

        @ConfigAutowireable
        @Dao
        public interface UserDao {

            @Update(excludeNull= true)
            Result<User> update(User user);
        }


Entity
    .. code-block:: java

        @Entity(immutable= true)
        @Table(name= "users")
        public class User {

            public User(final String mailAddress, String password, String userName, Boolean enabled) {
                this(mailAddress, password, userName, enabled, null);
            }

            public User(final String mailAddress, String password, String userName, Boolean enabled, Long versionNo) {

                // 　バージョン番号以外の項目は省略

                this.versionNo= versionNo;
            }


            // 　バージョン番号以外の項目は省略


            @Version
            public final Long versionNo;

        }


エラーハンドリング
    楽観ロックエラー時は、

    * `org.seasar.doma.jdbc.OptimisticLockException <http://static.javadoc.io/org.seasar.doma/doma/2.0.1/org/seasar/doma/jdbc/OptimisticLockException.html>`_
    * `org.seasar.doma.jdbc.BatchOptimisticLockException <http://static.javadoc.io/org.seasar.doma/doma/2.0.1/org/seasar/doma/jdbc/BatchOptimisticLockException.html>`_

    がDoma2からthrowされる。
    throwされた例外は、以下を参考にハンドリングする。

    TODO リンクを記載