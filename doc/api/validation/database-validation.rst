データベースを使用して入力値をチェックする
==================================================
データベースを使用した入力値のチェックの概要については、 :ref:`画面のデータベースを使用して入力値をチェックする <web-database-validation>` を参照してください。

本章では、RESTful APIでデータベースを使用した入力値チェックを行った場合の例外ハンドリングとクライントへのレスポンス返却方法について説明します。

実装例
--------------------------------------------------
Controller
  ``Controller``\ では\ ``Service``\ で送出された例外を捕捉します。
  捕捉した例外を元に、クライアントに返すための情報を持つ例外を生成し送出します。

  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/controller/UsersController.java
     :language: java
     :start-after: database-validation-start
     :end-before: database-validation-end
     :dedent: 4

メッセージ定義(messages.properties)
  クライアントに返すメッセージを定義します。

  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/resources/messages.properties
     :language: properties

例外クラス(CustomValidationException)
  バリデーションエラーの情報を保持する例外クラスです。
  例外クラスがバリデーションエラーの情報を持つことで、例外からクライアントに返す情報を構築できます。

  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/exception/CustomValidationException.java
     :language: java

例外ハンドラクラス(GlobalExceptionHandler)
  :ref:`api-error-handling` を使用して、例外に応じた処理を行います。
  この例では、捕捉した例外を元にクライアントに返却するレスポンスを構築します。

  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/GlobalExceptionHandler.java
     :language: java
     :start-after: database-validation-start
     :end-before: database-validation-end
     :dedent: 4
  
レスポンス内容
  上記の実装例の場合、バリデーションエラー発生時のレスポンスボディの内容は以下のようになります。
  
  .. code-block:: json

    [
      {
        "field": "role",
        "message": "ロール[存在しないロール]が見つかりませんでした。"
      }
    ]

サンプル全体は :sample-app:`api-error-handling-sample <api/api-error-handling>` を参照してください。
