入力値の型変換エラー時に入力画面に適切なメッセージを表示する
===================================================================
画面から入力された値は、\ `Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_\ により下記の流れで処理されますが、入力値をBeanに変換する際に型変換エラーが発生する場合があります。

Bean Validationの処理の流れ
  1. 入力値をBeanに変換  

     プロパティの型がString以外の場合は、型変換が行われます。
  2. Bean Validationの実行
  3. アプリケーションは検証済みのBeanを使って処理を行う

デフォルトでは、型変換時に入力画面に表示するメッセージが定義されていないため、入力画面には以下のように例外の内容がそのまま表示されてしまいます。

.. image:: images/type-converter-error-default-message.png
  :scale: 70
 
このページでは、入力値の型変換でエラーが発生した場合に、入力画面に適切なメッセージを表示する方法について説明します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

メッセージの定義例
--------------------------------------------------
型変換が失敗した際に表示するメッセージを定義することで、入力画面に入力値に誤りがあることを表示できます。

入力値を受け取るBean(Form)
  入力パラメータを保持するBeanのプロパティを適切な型で宣言します。

  .. literalinclude:: ../../../samples/web/validation/src/main/java/keel/validation/controller/AddUserController.java
    :language: java
    :start-after: type-converter-error-start
    :end-before: type-converter-error-end

メッセージ
  メッセージを ``typeMismatch.<変換対象の型の完全修飾名>`` で定義します。
  変換対象の型がjava.lang.Integer の場合には、メッセージのキーは ``typeMismatch.java.lang.Integer`` となります。
  
  .. literalinclude:: ../../../samples/web/validation/src/main/resources/messages.properties
    :language: properties

  .. tip::

    特定の画面のみ型変換時に表示するメッセージを変更したい場合は、下のように ``typeMismatch.<項目名>`` とすることで対応できます。
    詳細な仕様は、 :spring-framework-doc:`DefaultMessageCodesResolver <javadoc-api/org/springframework/validation/DefaultMessageCodesResolver.html>` を参照してください。

    .. code-block:: properties

      typeMismatch.age=数値で入力してください。

実行結果
  型変換時には定義したメッセージが表示されます。

  .. image:: images/type-converter-error-message.png

サンプル全体は :sample-app:`validation-sample <web/validation>` を参照してください。
