.. _database-validation:

データベースを使用して入力値をチェックする
==================================================
データベースの状態を用いた入力値のチェックをしたい場合があります。
データベースの状態チェックはトランザクション配下で行ったほうが良いケースがあるため、 :ref:`bean-validation` を使用するのではなく ``Service`` などでバリデーションを行うことをおすすめします。

.. tip::

  トランザクションの外側でデータベースに対するバリデーションを行った場合、正しく行えない場合があります。
  例えば、重複登録のチェックをトランザクションの外側でselectを実行して行っても、同時に登録処理が行われた場合チェック処理の意味がありません。

実装例
--------------------------------------------------
``Service`` で送出した例外を ``Controller`` で捕捉し、画面にエラーメッセージを表示します。

Controller
  .. literalinclude:: ../../../samples/web/validation/src/main/java/keel/validation/controller/AddUserController.java
     :language: java
     :linenos:
     :start-after: example-start
     :end-before: example-end

Service
  .. literalinclude:: ../../../samples/web/validation/src/main/java/keel/validation/service/UserService.java
    :language: java
    :linenos:
    :start-after: example-start
    :end-before: example-end
