Bean Validationを使った入力値のチェック
==================================================
クライアントから送信されるリクエストボディやクエリパラメータは、\ `Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_\ を使ってチェックできます。

このページでは、クライアントから送信された値をチェックする際に発生する例外(``No1``\ 及び\ ``No2``\ で発生する例外)について説明します。
発生した例外に応じたレスポンスのカスタマイズ方法は、:doc:`例外ハンドリング </api/error-handling/index>` を参照してください。

処理フロー
  1. リクエストボディや、クエリパラメータを解析してBeanに変換

     プロパティの型がString以外の場合は、型変換が行われます。
  2. Bean Validationの実行
  3. アプリケーションは検証済みのBeanを使って処理を行う

No1の型変換失敗時に送出される例外
  .. csv-table::
    :header: 変換元, 例外
    :widths: 10, 10

    リクエストボディ, :spring-framework-doc:`HttpMessageNotReadableException <javadoc-api/org/springframework/http/converter/HttpMessageNotReadableException.html>`
    クエリパラメータ, :spring-framework-doc:`BindException <javadoc-api/org/springframework/validation/BindException.html>`

No2のBean Validationでエラーがあった場合に送出される例外
  .. csv-table::
    :header: チェック対象, 例外
    :widths: 10, 10

    リクエストボディから変換したBean, :spring-framework-doc:`MethodArgumentNotValidException <javadoc-api/org/springframework/web/bind/MethodArgumentNotValidException.html>`
    クエリパラメータから変換したBean, :spring-framework-doc:`BindException <javadoc-api/org/springframework/validation/BindException.html>`

