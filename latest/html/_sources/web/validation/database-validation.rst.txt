.. _web-database-validation:

データベースを使用して入力値をチェックする
==================================================
データベースの状態を用いた入力値のチェックをしたい場合、バリデーションを実装する必要があります。

.. tip::

  例えばバリデーションで重複登録のチェックを行ったとしても、同時に複数の登録処理が行われてしまうとバリデーションのタイミングでエラーにならないというようなこともありえます。バリデーションだけでデータの不整合を確実に防げるわけではありませんので、データベースの制約を使用する等、データの不整合が発生しないよう十分に注意してください。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

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
