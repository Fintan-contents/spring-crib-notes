例外ハンドリング
====================================================================================================
Webアプリケーションでの例外ハンドリング方法とレスポンスの返却方法について説明します。

:spring-framework-doc:`Spring Web MVC <reference/html/web.html#spring-web>` では、発生した例外を自動的にログ出力し、クライアントにエラーを返却します。

:spring-framework-doc:`Spring Web MVCがデフォルトでハンドリングする例外 <javadoc-api/org/springframework/web/servlet/mvc/support/DefaultHandlerExceptionResolver.html>`
であれば、アプリケーション側で設定や実装を行わなくても、レスポンスに適切なステータスコードが設定されます。記載のない例外については、デフォルトではステータスコードとして500が設定されます。

エラーが返却された場合、 デフォルトで :spring-boot-doc:`Sping Bootが提供しているエラー画面が表示されます <reference/htmlsingle/#web.servlet.spring-mvc.error-handling>` 。エラー画面の動作については ``server.error`` プロパティでカスマイズすることができます。プロパティの詳細については :spring-boot-doc:`Server Properties <reference/htmlsingle/#appendix.application-properties.server>` を参照してください。

また、デフォルトのエラー画面ではなく独自に作成したエラー画面を表示したい場合には、以下の方法があります。

* ステータスコードに対応するページを templates/error/<status-code>.html として作成する
* 例外をハンドリング後、任意のページ名を返す

例えば、ステータスコードが 404 NotFound の画面をカスタマイズしたい場合は templates/error/404.html を作成します。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

アプリケーション全体の例外ハンドリングをカスタマイズする例
-----------------------------------------------------------------------------
アプリケーション全体で例外に応じた処理が決まっている場合は、\ :spring-framework-doc:`@ControllerAdvice <javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html>`
アノテーションを設定したクラスで例外ハンドリングを行います。

どの例外を処理するかは、メソッドに設定された\ :spring-framework-doc:`@ExceptionHandler <javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html>`\ アノテーションの情報により決まります。
また、返却するステータスコードは :spring-framework-doc:`@ResponseStatus <javadoc-api/org/springframework/web/bind/annotation/ResponseStatus.html>` アノテーションに設定します。

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
