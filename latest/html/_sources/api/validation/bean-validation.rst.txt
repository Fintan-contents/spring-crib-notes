入力値の型変換やJakarta Bean Validationで発生する例外
=====================================================
このページでは、クライアントから送信されたリクエストボディやクエリパラメータの型変換、および\ `Jakarta Bean Validation <https://spring.io/guides/gs/validating-form-input/>`_\ で発生する例外について説明します。

発生した例外に応じたレスポンスのカスタマイズ方法については :doc:`例外ハンドリング </api/error-handling/index>` を参照してください。

処理フロー
  1. リクエストボディやクエリパラメータを解析してBeanに変換（String型以外の場合は型変換が行われる）
  2. Jakarta Bean Validationの実行
  3. アプリケーションは検証済みのBeanを使って処理を行う

処理フロー1のBean変換で失敗した場合に送出される例外
  .. csv-table::
    :header: 変換元, 例外
    :widths: 10, 10

    リクエストボディ, :spring-framework-javadoc:`HttpMessageNotReadableException <javadoc-api/org/springframework/http/converter/HttpMessageNotReadableException.html>`
    クエリパラメータ, :spring-framework-javadoc:`BindException <javadoc-api/org/springframework/validation/BindException.html>`

処理フロー2のJakarta Bean Validationでエラーがあった場合に送出される例外
  .. csv-table::
    :header: チェック対象, 例外
    :widths: 10, 10

    リクエストボディから変換したBean, :spring-framework-javadoc:`MethodArgumentNotValidException <javadoc-api/org/springframework/web/bind/MethodArgumentNotValidException.html>`
    クエリパラメータから変換したBean, :spring-framework-javadoc:`BindException <javadoc-api/org/springframework/validation/BindException.html>`
