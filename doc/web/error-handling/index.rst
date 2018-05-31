例外ハンドリング
====================================================================================================
Webアプリケーションでの例外ハンドリング方法とレスポンスの返却方法について説明します。

`Spring Web MVC <https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html>`_ では、発生した例外のログ出力とエラー応答(基本的にステータスコードが500のレスポンス)を自動的に返却します。
このため、アプリケーション側で何かを作り込む必要はありませんが、例外に応じてクライアントに返すステータスコードやページを変更したい場合には例外ハンドリングを実装する必要があります。

例外に応じてクライアントに返すステータスコードやページをカスタマイズする
-----------------------------------------------------------------------------
アプリケーション全体で例外に応じた処理が決まっている場合は、\ `@ControllerAdvice <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html>`_
アノテーションを設定したクラスで例外ハンドリングを行います。
どの例外を処理するかは、メソッドに設定された\ `@ExceptionHandler <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>`_\ アノテーションの情報により決まります。

この例では、NoResultExceptionが発生した場合に対象データが存在しないことを示すステータスコード404を返します。
クライアントには、404に対応したテンプレート(Thymeleafを使用した場合のデフォルト設定ではtemplates/error/404.html)ページが返されます。

.. literalinclude:: ../../../samples/web/error-handling/src/main/java/keel/SampleExceptionHandler.java
   :language: java
   :start-after: exception-handler-start
   :end-before: exception-handler-end

アプリケーション全体ではなく個別機能(Controller)で例外をハンドリングし、エラーページを返したい場合があります。
この場合は、Controller内に例外ハンドリング用のメソッドを作成します。

.. literalinclude:: ../../../samples/web/error-handling/src/main/java/keel/Users2Controller.java
  :language: java
  :start-after: controller-exception-handling-start
  :end-before: controller-exception-handling-end

.. tip::

  `ResponseStatus <https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>`_ 
  のreason属性を指定しなかった場合は、下のように明示的にクライアントに返すテンプレートのパスを指定する必要があります。

  .. code-block:: java

    @ExceptionHandler(SampleException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String sampleExceptionHandler(SampleException e) {
        logger.debug("クライアントからの要求が不正です", e);
        // クライアントに返すテンプレートへのパスを戻します。
        return "error/custom_400";
    }

  テンプレートのパスを指定しなかった場合は、クライアントにはレスポンスヘッダーのみが返されます。

単純に例外毎にクライアントに返すステータスコードを決めたい場合には、下の例のように例外クラスに
ResponseStatusアノテーションを設定することで対応できます。
ただし、ログなどが一切出力されないため上で説明した@ControllerAdviceの使用を推奨します。

.. code-block:: java

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public class SampleException2 extends RuntimeException {
  }

サンプル全体は :sample-app:`error-handling-sample <web/error-handling>` を参照してください。


Serviceなどで送出した例外を業務エラーとして扱い画面にエラーメッセージを表示する
----------------------------------------------------------------------------------------
:ref:`web-database-validation` の例を参照してください。

