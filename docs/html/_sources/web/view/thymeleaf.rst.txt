ViewのテンプレートエンジンにThymeleafを使用する
==================================================
Viewのテンプレートエンジンとして\ :thymeleaf-tutorials-doc:`Thymeleaf <usingthymeleaf_ja.html>`\ を使用する方法を説明します。

.. tip::

  Thymeleafには、以下のメリットがあります。

  * ドキュメントが豊富で容易に利用できます。(一部日本語化されたドキュメントも提供されています)
  * プロダクション環境で利用するテンプレート(html)をそのままブラウザで表示して確認できます。

.. tip::

  以下のリンク先にあるとおり、Spring Bootでは、特別な理由がない限りテンプレートエンジンにJSPを使用するのは避けるべきとされています。

  * :spring-boot-doc:`Spring Boot Reference Guide - 27.1.10 Template Engines <reference/htmlsingle/#boot-features-spring-mvc-template-engines>`

以下のサンプルコードの動作確認環境については、 :ref:`test-environment-and-dependencies` を参照してください。

テンプレートエンジンとしてThymeleafを使用できるようにする
-------------------------------------------------------------------
pom.xml
  依存ライブラリにspring-boot-starter-thymeleafを追加することで、Thymeleafが使用できるようになります。

  .. literalinclude:: ../../../samples/web/template-engine-thymeleaf/pom.xml
     :language: xml
     :start-after: example-start
     :end-before: example-end
     :dedent: 4

テンプレートの配置とControllerでの指定
--------------------------------------------------
テンプレートは、下の例のようにsrc/main/resources/templates配下に配置します。

.. code-block:: bat

  src\main\resources\templates
    │  user.html
    │
    ├─common
    │      footer.html
    │      head.html
    │      header.html
    │
    └─default
            completion.html
            input.html

Controllerで指定するテンプレートのファイル名には、templatesより下のパスを指定します。拡張子(.html)の指定は省略可能です。
templates/default/input.htmlを使用したい場合には、Controllerでは下の例のようにdefault/inputと指定します。

.. literalinclude:: ../../../samples/web/template-engine-thymeleaf/src/main/java/keel/thymeleaf/DefaultValueSampleController.java
   :language: java
   :start-after: example-start
   :end-before: example-end
   :dedent: 4
   
サンプル全体は :sample-app:`template-engine-thymeleaf-sample <web/template-engine-thymeleaf>` を参照してください。
