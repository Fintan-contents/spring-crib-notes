Bean Validationを使った値のチェック
==================================================
クライアントから送信されるリクエストボディや、クエリパラメータは、 `Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_ を使ってチェックすることができます。
`Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_ の使用に関する基本的なフローは、:doc:`Webの場合 </web/validation/bean-validation>` と同様です。

処理フロー
  1. リクエストボディや、クエリパラメータを解析してBeanに変換

     プロパティの型がString以外の場合は、型変換が行われます。
  2. `Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_ の実行
  3. アプリケーションは検証済みのBeanを使って処理を行う


RESTful Webサービスの場合は、``No1`` の変換に失敗した場合に、以下の例外が送出されます。

.. csv-table::
  :header: 変換元, 例外
  :widths: 10, 10

  リクエストボディ, `HttpMessageNotReadableException <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/converter/HttpMessageNotReadableException.html>`_
  クエリパラメータ, `BindException <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindException.html>`_

なお、``No2`` の `Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_ の実行でエラーとなった場合は、以下の例外が送出されます。

.. csv-table::
  :header: チェック対象, 例外
  :widths: 10, 10

  リクエストボディから変換したBean, `MethodArgumentNotValidException <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/MethodArgumentNotValidException.html>`_
  クエリパラメータから変換したBean, `BindException <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindException.html>`_


上記例外発生時にデフォルトで返却されるレスポンスの内容や、そのレスポンスの内容をカスタマイズする方法については、:doc:`例外ハンドリング </api/error-handling/index>` を参照してください。
