.. _api-error-handling:

例外ハンドリング
====================================================================================================

RESTful Webサービスでの例外ハンドリング方法とレスポンスの返却方法について説明します。
RESTful Webサービスでも、:doc:`Webの場合 </web/error-handling/index>` と同様に、発生した例外のログ出力とエラー応答を自動的に実施します。

:spring-framework-doc:`Spring Web MVCがデフォルトでハンドリングする例外 <javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html>`
は、アプリケーション側で設定や実装を行わなくても、適切なレスポンスがクライアントに返却されます。記載のない例外についてはデフォルトではステータスコード500が返却されます。また、全ての例外において、レスポンスボディには例外メッセージ等が出力されます。

レスポンスの例
  .. code-block:: text

    HTTP/1.1 500
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Mon, 28 May 2018 07:20:57 GMT
    Connection: close

    {
      "timestamp": "2018-05-28T07:20:57.132+0000",
      "status": 500,
      "error": "Internal Server Error",
      "message": "file not found",
      "path": "/users/1"
    }

このため、特定の例外メッセージをクライアントに返却したくない場合や、例外に応じてクライアントに返却するステータスコードやレスポンスボディを変更したい場合には例外ハンドリングを実装する必要があります。

アプリケーション全体の例外ハンドリングをカスタマイズする例
--------------------------------------------------------------------------------------
アプリケーション全体で例外に応じた処理が決まっている場合は、:spring-doc:`@RestControllerAdvice <javadoc-api/org/springframework/web/bind/annotation/RestControllerAdvice.html>`
アノテーションを設定したクラスで例外ハンドリングを行います。

どの例外を処理するかは、メソッドに設定された\ :spring-doc:`@ExceptionHandler <javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>`\ アノテーションの情報により決まります。
返却するステータスコードは :spring-doc:`@ResponseStatus <javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>` アノテーションに設定します。

.. tip::

  例外クラスに設定した\ :spring-doc:`ResponseStatus <javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>`\ のreason属性を指定した場合、
  レスポンスボディには、reason属性に指定した値がメッセージとして表示されます。
  reason属性に指定した値は、\ :spring-doc:`MessageSource <javadoc-api/org/springframework/context/MessageSource.html>`\ を使用して解決されるため、
  messages.propertiesに定義することも可能です。

  Controller等のメソッドに設定したResponseStatusのreason属性を指定した場合も、
  レスポンスボディには、reason属性に指定した値がメッセージとして表示されます。
  ただし、reason属性に指定した値は、MessageSourceを使用して解決されません。そのため、reason属性に指定した値がそのまま出力されます。

ResponseEntityExceptionHandlerを継承して例外ハンドリングクラスを作成する
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
:spring-framework-doc:`ResponseEntityExceptionHandler <javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html>` は、Spring Web MVC内で発生する例外をハンドリングするクラスです。
ResponseEntityExceptionHandlerを継承したクラスを作成すると、ハンドリングする例外に応じたステータスコードと空のレスポンスボディが返却されます。
ハンドリングする例外は、:spring-framework-doc:`Spring Web MVCがデフォルトでハンドリングする例外 <javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html>` と同様です。

全ての例外を@ExceptionHandlerアノテーションで一つずつハンドリングしていくのは大変なので、基本的な例外ハンドリングはResponseEntityExceptionHandlerに委譲して、それ以外の例外を@ExceptionHandlerアノテーションでハンドリングする使用方法をお薦めします。

なお、ResponseEntityExceptionHandlerを継承した場合も、@RestControllerAdviceアノテーションを設定する必要があります。


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
* 入力値チェックでエラーが発生した場合の処理をカスタマイズしています。
* 排他制御エラーが発生した場合に送出される例外を、:spring-framework-doc:`@ExceptionHandler <javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>` を使用してハンドリングしています。
* 独自に作成した例外(CustomValidationException)を、:spring-framework-doc:`@ExceptionHandler <javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>` を使用してハンドリングしています。
* 独自に作成した例外(UserNotFoundException)に :spring-doc:`ResponseStatus <javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>` を設定して、:spring-doc:`Spring Web MVC <spring-framework-reference/web.html>` に例外ハンドリングを委譲します。

GlobalExceptionHandler
  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/GlobalExceptionHandler.java
     :language: java
     :start-after: api-error-handling-example-start
     :end-before: api-error-handling-example-end

UserNotFoundException
  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/exception/UserNotFoundException.java
     :language: java
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`api-error-handling-sample <api/api-error-handling>` を参照してください。

個別機能(Controller)で例外をハンドリングする例
--------------------------------------------------------------------------------------
アプリケーション全体ではなく個別機能(Controller)で例外をハンドリングし、メッセージを返却したい場合があります。
:doc:`Webの場合 </web/error-handling/index>` と同様に、Controller内に例外ハンドリング用のメソッドを作成します。

.. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/controller/Users2Controller.java
   :language: java
   :start-after: example-start
   :end-before: example-end

サンプル全体は :sample-app:`api-error-handling-sample <api/api-error-handling>` を参照してください。
