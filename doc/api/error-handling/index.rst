例外ハンドリング
====================================================================================================

本節では、RESTful Webサービスにおける例外ハンドリング方法とレスポンスの返却方法について説明します。

RESTful Webサービスでも、:doc:`Webの場合 </web/exclusive-control/doma2-optimistic-lock>` と同様に、発生した例外のログ出力とエラー応答を自動的に実施します。

参考情報
  `Spring Web MVCがデフォルトでハンドリングする例外 <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html>`_

上記以外の例外については基本的にステータスコード500が返却されます。
また、全ての例外において、レスポンスボディには例外メッセージ等が出力されます。

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
アプリケーション全体で例外に応じた処理が決まっている場合は、以下を満たすクラスを作成します。

#. `@RestControllerAdvice <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestControllerAdvice.html>`_ をクラスに設定
#. `ResponseEntityExceptionHandler <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html>`_ を継承

`ResponseEntityExceptionHandler <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html>`_ は
`Spring Web MVC <https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html>`_ 内で発生する例外をハンドリングするクラスです。
`ResponseEntityExceptionHandler <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html>`_ では、ハンドリングする例外に応じたステータスコードと空のレスポンスボディを返却します。
ハンドリングする例外は、`Spring Web MVCがデフォルトでハンドリングする例外 <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html>`_ と同様のものです。

なお、`ResponseEntityExceptionHandler <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html>`_ ではハンドリングしない例外については、

1. `@ExceptionHandler <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>`_ を設定したメソッドを定義
2. `@ResponseStatus <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>`_ を設定した例外クラスを送出

等の方法を使用して、例外をハンドリングします。

.. tip::

  例外クラスに設定した `ResponseStatus <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>`_ の ``reason`` 属性を指定した場合、
  レスポンスボディには、 ``reason`` 属性に指定した値がメッセージとして表示されます。
  ``reason`` 属性に指定した値は、`MessageSource <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/MessageSource.html>`_ を使用して解決されるため、
  ``messages.properties`` に定義することも可能です。

  ``Controller`` 等のメソッドに設定した `ResponseStatus <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>`_ の ``reason`` 属性を指定した場合も、
  レスポンスボディには、 ``reason`` 属性に指定した値がメッセージとして表示されます。
  ただし、 ``reason`` 属性に指定した値は、`MessageSource <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/MessageSource.html>`_ を使用して解決されません。
  そのため、 ``reason`` 属性に指定した値がそのまま出力されます。


実装例
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
* 入力値チェックでエラーが発生した場合の処理をカスタマイズしています。
* 排他制御エラーが発生した場合に送出される例外を、`@ExceptionHandler <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>`_ を使用してハンドリングしています。
* 独自に作成した例外( ``CustomValidationException`` )を、`@ExceptionHandler <https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>`_ を使用してハンドリングしています。
* 独自に作成した例外( ``UserNotFoundException`` )に `ResponseStatus <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>`_ を設定して、`Spring Web MVC <https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html>`_ に例外ハンドリングを委譲します。

GlobalExceptionHandler
  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/GlobalExceptionHandler.java
     :language: java
     :linenos:
     :start-after: api-error-handling-example-start
     :end-before: api-error-handling-example-end

UserNotFoundException
  .. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/exception/UserNotFoundException.java
     :language: java
     :linenos:
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`api-error-handling-sample <api/api-error-handling>` を参照してください。

個別機能( ``Controller`` )で例外をハンドリングする例
--------------------------------------------------------------------------------------
アプリケーション全体ではなく個別機能( ``Controller`` )で例外をハンドリングし、メッセージを返却したい場合があります。
:doc:`Webの場合 </web/error-handling/index>` と同様に、``Controller`` 内に例外ハンドリング用のメソッドを作成します。

.. literalinclude:: ../../../samples/api/api-error-handling/src/main/java/keel/apierrorhandling/controller/Users2Controller.java
   :language: java
   :linenos:
   :start-after: example-start
   :end-before: example-end

サンプル全体は :sample-app:`api-error-handling-sample <api/api-error-handling>` を参照してください。