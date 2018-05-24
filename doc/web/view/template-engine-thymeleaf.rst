Thymeleafを使用した共通レイアウトの作成方法
====================================================================================================

本節では、ドキュメント等の情報量が多く、作成したhtmlをそのまま利用できる `Thymeleaf <https://www.thymeleaf.org/documentation.html>`_ を使用して、共通レイアウトを作成する方法について記載します。

なお、`Thymeleaf <https://www.thymeleaf.org/documentation.html>`_ の基本的な使用方法については、以下のドキュメントを参照してください。

参考情報
  * `Tutorial: Using Thymeleaf <https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html>`_
  * `Tutorial: Thymeleaf + Spring <https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html>`_
  * `Tutorial: Extending Thymeleaf <https://www.thymeleaf.org/doc/tutorials/3.0/extendingthymeleaf.html>`_

.. tip::
  Spring Bootでは、画面の作成にJSPを使用する事は非推奨となっています。

  詳細は、`Spring Boot Reference Guide - 27.4.5 JSP Limitations <https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-jsp-limitations>`_ を参照してください。


実装例
-----------------------------------------------

共通レイアウトは、`ThymeleafのFragments <https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#fragments>`_ を使用して実現します。

例えば、

* ``<head>`` 内の定義
* ヘッダーのレイアウト
* フッターのレイアウト

を共通化したい場合は以下のような実装になります。

``<head>`` を定義したフラグメント
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/common/head.html
    :language: html

ヘッダーのフラグメント
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/common/header.html
    :language: html

フッターのフラグメント
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/common/footer.html
    :language: html

ユーザ画面
  .. literalInclude:: ../../../samples/web/template-engine-thymeleaf/src/main/resources/templates/user.html
    :language: html

.. tip::
  Fragmentsを読込む方法として、以下の方法があります。

    * ``th:insert``
    * ``th:replace``
    * ``th:include``

  上記の使い分けについては、 `Tutorial: Using Thymeleaf -  Including template fragments <https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#including-template-fragments>`_ を参照してください。

サンプル全体は :sample-app:`template-engine-thymeleaf-sample <web/template-engine-thymeleaf>` を参照してください。

