例外ハンドリング
====================================================================================================
Webアプリケーションでの例外ハンドリング方法とレスポンスの返却方法について説明します。

:spring-doc:`Spring Web MVC <spring-framework-reference/web.html>` では、発生した例外を自動的にログ出力し、クライアントにエラーを返却します。

特に、 :spring-framework-doc:`Spring Web MVCがデフォルトでハンドリングする例外 <javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html>`
は、アプリケーション側で設定や実装を行わなくても、適切なレスポンスステータスコードに変換されます。記載のない例外についてはデフォルトではステータスコード500に変換されます。

なお、エラー画面はSpring Bootがデフォルトで用意した画面になります。カスタマイズする場合は、templates/error/<status-code>.htmlを作成してください。例えば、404 NotFoundの画面をカスタマイズしたい場合は、templates/error/404.htmlとして作成します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

アプリケーション全体の例外ハンドリングをカスタマイズする例
-----------------------------------------------------------------------------
アプリケーション全体で例外に応じた処理が決まっている場合は、\ :spring-doc:`@ControllerAdvice <javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html>`
アノテーションを設定したクラスで例外ハンドリングを行います。

どの例外を処理するかは、メソッドに設定された\ :spring-doc:`@ExceptionHandler <javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>`\ アノテーションの情報により決まります。
また、返却するステータスコードは :spring-doc:`@ResponseStatus <javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>` アノテーションに設定します。

この例では、NoResultExceptionが発生した場合に対象データが存在しないことを示すステータスコード404を返します。
クライアントには、404に対応したテンプレート(Thymeleafを使用した場合のデフォルト設定ではtemplates/error/404.html)ページが返されます。

.. literalinclude:: ../../../samples/web/error-handling/src/main/java/keel/SampleExceptionHandler.java
   :language: java
   :start-after: exception-handler-start
   :end-before: exception-handler-end

注意点として、@ResponseStatusアノテーションのreason属性を指定しなかった場合は、クライアントに返却したいテンプレートのパスをメソッドの戻り値として明示的に指定する必要があります。

サンプル全体は :sample-app:`error-handling-sample <web/error-handling>` を参照してください。

個別機能(Controller)で例外をハンドリングする例
-----------------------------------------------------------------------------

アプリケーション全体ではなく個別機能(Controller)で例外をハンドリングし、エラーページを返したい場合があります。
この場合は、Controller内に例外ハンドリング用のメソッドを作成します。

.. literalinclude:: ../../../samples/web/error-handling/src/main/java/keel/Users2Controller.java
  :language: java
  :start-after: controller-exception-handling-start
  :end-before: controller-exception-handling-end

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
:ref:`web-database-validation` の実装例を参照してください。
