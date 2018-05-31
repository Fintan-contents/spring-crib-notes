.. _bean-validation:

Bean Validationを使った入力値のチェック
==================================================
画面から入力された値は、\ `Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_\ を使ってチェックできます。

Bean Validationは、下の流れでバリデーションが行われますが ``No1`` の型変換に失敗した場合には、ユーザに入力値が不正であったことを通知する必要があります。
もし、アプリケーション側で適切な実装（メッセージの定義）をしなかった場合、ユーザには意味の分からないメッセージが表示されるため注意しましょう。

1. 入力値をBeanに変換  

   プロパティの型がString以外の場合は、型変換が行われます。
2. Bean Validationの実行
3. アプリケーションは検証済みのBeanを使って処理を行う

実装例
--------------------------------------------------
型変換失敗時に適切なメッセージを表示するための実装例となります。

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
    詳細な仕様は、 `DefaultMessageCodesResolver <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/DefaultMessageCodesResolver.html>`_ を参照してください。

    .. code-block:: properties

      typeMismatch.age=数値で入力してください。

実行結果
  型変換時には定義したメッセージが表示されます。

  .. image:: images/type-converter-error-message.png

  .. tip::

    メッセージ定義を行わなかった場合は、下のようにユーザには理解できないメッセージが表示されます。

    .. image:: images/type-converter-error-default-message.png

サンプル全体は :sample-app:`validation-sample <web/validation>` を参照してください。
