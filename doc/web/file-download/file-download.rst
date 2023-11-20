ファイルをダウンロードする
==================================================
このページでは、ファイルをダウンロードする方法について説明します。

ファイルのダウンロード処理は、:spring-framework-doc:`AbstractView <javadoc-api/org/springframework/web/servlet/view/AbstractView.html>` を継承したカスタムViewクラスで実施します。
AbstractViewを継承したクラスをSpringのDIコンテナに登録しておくと、 :spring-framework-doc:`BeanNameViewResolver <javadoc-api/org/springframework/web/servlet/view/BeanNameViewResolver.html>` が
Controllerから返却されたBean名と一致する場合に、Viewとして選択してくれます。

.. tip::

  Spring Web MVCやThymeleafでは、Controllerから返却された値に対応するViewを解決するため、いくつかのViewResolverが提供されています。Spring Bootを使用する場合、これらのViewResolverは自動で構成され、優先度の高いViewResolverから使用されます。Spring Web MVCが提供しているものについては :spring-framework-doc:`View Resolution <reference/html/web.html#mvc-viewresolver>` を、Thymeleafが提供しているものについては :thymeleaf-tutorials-doc:`Views and View Resolvers <thymeleafspring.html#views-and-view-resolvers>` を参照してください。

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

処理フロー
--------------------------------------------------
1. Controllerはファイルのダウンロード処理を実施するカスタムViewクラスのBean名を返却します。(このサンプルではテキストファイルのダウンロード処理を実施するBean名を返却します。)
2. BeanNameViewResolverは、Controllerから返却されたBean名に一致するカスタムViewクラスを選択します。
3. カスタムViewクラスは、レスポンスヘッダを設定してダウンロードファイルをレスポンスボディに書込みます。

実装例
--------------------------------------------------
Controller
  .. literalinclude:: ../../../samples/web/file-download/src/main/java/keel/filedownload/UserController.java
     :language: java
     :start-after: example-start
     :end-before: example-end
     :dedent: 4

TextFileDownloadView
  .. literalinclude:: ../../../samples/web/file-download/src/main/java/keel/filedownload/TextFileDownloadView.java
     :language: java
     :start-after: example-start
     :end-before: example-end

サンプル全体は :sample-app:`file-download <web/file-download>` を参照してください。
