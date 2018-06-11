.. _web-database-validation:

データベースを使用して入力値をチェックする
==================================================
データベースの状態を用いた入力値のチェックをしたい場合があります。
データベースの状態チェックはトランザクション配下で行ったほうが良いケースがあるため、`Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_\ を使用するのではなくServiceなどでバリデーションを行うことをおすすめします。

.. tip::

  トランザクションの外側でデータベースに対するバリデーションを行った場合、正しく行えない場合があります。
  例えば、重複登録のチェックをトランザクションの外側でselectを実行して行っても、同時に登録処理が行われた場合チェック処理の意味がありません。

実装例
--------------------------------------------------
Serviceで送出した例外をControllerで捕捉し、画面にエラーメッセージを表示します。

Controller
  .. literalinclude:: ../../../samples/web/validation/src/main/java/keel/validation/controller/AddUserController.java
     :language: java
     :start-after: example-start
     :end-before: example-end

Service
  .. literalinclude:: ../../../samples/web/validation/src/main/java/keel/validation/service/UserService.java
    :language: java
    :start-after: example-start
    :end-before: example-end
    
メッセージ定義(messages.properties)
  .. literalinclude:: ../../../samples/web/validation/src/main/resources/messages.properties
    :language: properties
    
サンプル全体は :sample-app:`validation-sample <web/validation>` を参照してください。
    
